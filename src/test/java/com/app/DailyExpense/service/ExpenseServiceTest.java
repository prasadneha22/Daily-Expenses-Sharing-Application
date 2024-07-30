package com.app.DailyExpense.service;

import com.app.DailyExpense.Entity.Expense;
import com.app.DailyExpense.Repository.ExpenseRepository;
import com.app.DailyExpense.Service.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExpenseServiceTest {

    @InjectMocks
    private ExpenseService expenseService;

    @Mock
    private ExpenseRepository expenseRepository;

    private Expense expense;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        expense = new Expense();
        expense.setId(1L);
        expense.setDescription("Dinner at restaurant");
        expense.setTotalAmount(BigDecimal.valueOf(3000));
    }


    @Test
    void testCalculateExactAmounts() {
        Map<Long, BigDecimal> exactAmounts = new HashMap<>();
        exactAmounts.put(1L, BigDecimal.valueOf(1000));
        exactAmounts.put(2L, BigDecimal.valueOf(2000));
        expense.setExactAmounts(exactAmounts);

        Map<Long, BigDecimal> result = expenseService.calculateExactAmounts(expense);
        assertEquals(2, result.size());
        assertEquals(BigDecimal.valueOf(1000), result.get(1L));
        assertEquals(BigDecimal.valueOf(2000), result.get(2L));
    }

    @Test
    void testCalculatePercentageAmounts() {
        Map<Long, Integer> percentages = new HashMap<>();
        percentages.put(1L, 50);
        percentages.put(2L, 50);
        expense.setPercentages(percentages);

        Map<Long, BigDecimal> result = expenseService.calculatePercentageAmounts(expense);
        assertEquals(2, result.size());
        assertEquals(BigDecimal.valueOf(1500), result.get(1L));
        assertEquals(BigDecimal.valueOf(1500), result.get(2L));
    }

    @Test
    void testCalculateTotalExpenses() {
        when(expenseRepository.findAll()).thenReturn(List.of(expense));
        BigDecimal total = expenseService.calculateTotalExpenses();
        assertEquals(BigDecimal.valueOf(3000), total);
    }
}
