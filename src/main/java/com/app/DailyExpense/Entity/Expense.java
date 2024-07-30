package com.app.DailyExpense.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Entity
@Data
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private BigDecimal totalAmount;

    @ElementCollection
    private Map<Long, BigDecimal> exactAmounts;

    @ElementCollection
    private Map<Long, Integer> percentages;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "expense_participants",
            joinColumns = @JoinColumn(name = "expense_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> participants;
}