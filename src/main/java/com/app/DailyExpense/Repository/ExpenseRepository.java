package com.app.DailyExpense.Repository;

import com.app.DailyExpense.Entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
        }
