package com.app.DailyExpense.Controller;

import com.app.DailyExpense.Entity.Expense;
import com.app.DailyExpense.Service.ExpenseService;
import com.app.DailyExpense.Service.UserService;
import com.app.DailyExpense.dto.ExpenseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;


    @Autowired
    private UserService userService;

    @PostMapping
    public Expense addExpense(@RequestBody ExpenseDTO expenseDTO) {
        return expenseService.addExpense(expenseDTO);
    }

    @GetMapping("/{id}")
    public Expense getExpense(@PathVariable Long id) {
        return expenseService.getExpense(id);
    }

    @GetMapping("/total")
    public Map<String, Object> getTotalExpenses() {
        BigDecimal total = expenseService.calculateTotalExpenses();
        Map<String, Object> response = new HashMap<>();
        response.put("totalExpenses", total);
        return response;
    }

    @GetMapping("/balance-sheet")
    public ResponseEntity<byte[]> downloadBalanceSheet() throws IOException {

        List<Expense> expenses = expenseService.getAllExpenses();

        // Calculate individual balances
        Map<Long, BigDecimal> userBalances = calculateUserBalances(expenses);

        String csvContent = generateCsvContent(userBalances);

        byte[] csvBytes = csvContent.getBytes();

        // Set response headers
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=balance-sheet.csv");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(csvBytes.length);

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }

    private Map<Long, BigDecimal> calculateUserBalances(List<Expense> expenses) {

        Map<Long, BigDecimal> userBalances = new HashMap<>();

        for (Expense expense : expenses) {
            BigDecimal totalAmount = expense.getTotalAmount();

            // Add amounts to users based on exact amounts
            if (expense.getExactAmounts() != null) {
                for (Map.Entry<Long, BigDecimal> entry : expense.getExactAmounts().entrySet()) {
                    Long userId = entry.getKey();
                    BigDecimal amount = entry.getValue();
                    userBalances.put(userId, userBalances.getOrDefault(userId, BigDecimal.ZERO).add(amount));
                }
            }

            // Add amounts to users based on percentage amounts
            if (expense.getPercentages() != null) {
                BigDecimal totalExpenseAmount = expense.getTotalAmount();
                for (Map.Entry<Long, Integer> entry : expense.getPercentages().entrySet()) {
                    Long userId = entry.getKey();
                    Integer percentage = entry.getValue();
                    BigDecimal amount = totalExpenseAmount.multiply(BigDecimal.valueOf(percentage))
                            .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
                    userBalances.put(userId, userBalances.getOrDefault(userId, BigDecimal.ZERO).add(amount));
                }
            }
        }

        return userBalances;
    }

    private String generateCsvContent(Map<Long, BigDecimal> userBalances) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outputStream);

        writer.println("User ID, Amount");

        for (Map.Entry<Long, BigDecimal> entry : userBalances.entrySet()) {
            writer.printf("%d, %s%n", entry.getKey(), entry.getValue().toString());
        }

        writer.flush();
        return new String(outputStream.toByteArray());
    }
}

