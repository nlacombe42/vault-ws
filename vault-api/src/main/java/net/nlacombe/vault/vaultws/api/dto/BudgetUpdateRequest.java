package net.nlacombe.vault.vaultws.api.dto;

import java.math.BigDecimal;

public class BudgetUpdateRequest {
    
    private BigDecimal plannedMaxAmount;
    private boolean income;
    private boolean investment;

    public BigDecimal getPlannedMaxAmount() {
        return plannedMaxAmount;
    }

    public void setPlannedMaxAmount(BigDecimal plannedMaxAmount) {
        this.plannedMaxAmount = plannedMaxAmount;
    }

    public boolean isIncome() {
        return income;
    }

    public void setIncome(boolean income) {
        this.income = income;
    }

    public boolean isInvestment() {
        return investment;
    }

    public void setInvestment(boolean investment) {
        this.investment = investment;
    }
}
