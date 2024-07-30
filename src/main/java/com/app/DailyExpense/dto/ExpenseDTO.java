package com.app.DailyExpense.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExpenseDTO {
    private String description;
    private BigDecimal totalAmount;
    private List<Long> participantIds;
    private Map<Long, BigDecimal> exactAmounts;
    private Map<Long, Integer> percentages;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<Long> getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(List<Long> participantIds) {
        this.participantIds = participantIds;
    }

    public Map<Long, BigDecimal> getExactAmounts() {
        return exactAmounts;
    }

    public void setExactAmounts(Map<Long, BigDecimal> exactAmounts) {
        this.exactAmounts = exactAmounts;
    }

    public Map<Long, Integer> getPercentages() {
        return percentages;
    }

    public void setPercentages(Map<Long, Integer> percentages) {
        this.percentages = percentages;
    }

    public void setId(long l) {
    }
}
