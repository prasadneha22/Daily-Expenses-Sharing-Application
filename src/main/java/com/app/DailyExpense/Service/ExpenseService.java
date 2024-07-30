package com.app.DailyExpense.Service;

import com.app.DailyExpense.Entity.Expense;
import com.app.DailyExpense.Entity.User;
import com.app.DailyExpense.Repository.ExpenseRepository;
import com.app.DailyExpense.Repository.UserRepository;
import com.app.DailyExpense.dto.ExpenseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;


    public Expense addExpense(ExpenseDTO expenseDTO) {
        Expense expense = new Expense();
        expense.setDescription(expenseDTO.getDescription());
        expense.setTotalAmount(expenseDTO.getTotalAmount());

        // Fetch and set participants
        List<User> participants = new ArrayList<>();
        for (Long userId : expenseDTO.getParticipantIds()) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            participants.add(user);
        }
        expense.setParticipants(participants);

        // Set exact amounts and percentages
        expense.setExactAmounts(expenseDTO.getExactAmounts());
        expense.setPercentages(expenseDTO.getPercentages());

        return expenseRepository.save(expense);
    }

    public Expense getExpense(Long id) {
        return expenseRepository.findById(id).orElse(null);
    }

    public Map<Long, BigDecimal> calculateExactAmounts(Expense expense) {
        Map<Long, BigDecimal> exactAmounts = expense.getExactAmounts();
        BigDecimal totalAmount = expense.getTotalAmount();

        if (exactAmounts == null || exactAmounts.isEmpty()) {
            throw new IllegalArgumentException("Exact amounts are not specified.");
        }

        // Calculate the total of all exact amounts
        BigDecimal totalExactAmounts = exactAmounts.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (!totalExactAmounts.equals(totalAmount)) {
            throw new IllegalArgumentException("The sum of exact amounts does not match the total expense amount.");
        }

        return new HashMap<>(exactAmounts);
    }

    public Map<Long, BigDecimal> calculatePercentageAmounts(Expense expense) {
    Map<Long, Integer> percentages = expense.getPercentages();
    BigDecimal totalAmount = expense.getTotalAmount();

    if (percentages == null || percentages.isEmpty()) {
        throw new IllegalArgumentException("Percentages are not specified.");
    }

    // Calculate the total percentage
    int totalPercentage = percentages.values().stream()
            .mapToInt(Integer::intValue)
            .sum();

    if (totalPercentage != 100) {
        throw new IllegalArgumentException("The sum of percentages must equal 100.");
    }

    // Calculate the amount each participant owes
    Map<Long, BigDecimal> amounts = new HashMap<>();
    for (Map.Entry<Long, Integer> entry : percentages.entrySet()) {
        Long userId = entry.getKey();
        Integer percentage = entry.getValue();
        BigDecimal amountOwed = totalAmount.multiply(BigDecimal.valueOf(percentage))
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
        amounts.put(userId, amountOwed);
        }
    return amounts;
    }

    public BigDecimal calculateTotalExpenses() {
    List<Expense> expenses = expenseRepository.findAll();

    BigDecimal total = expenses.stream()
            .map(Expense::getTotalAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    return total;
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }
}
