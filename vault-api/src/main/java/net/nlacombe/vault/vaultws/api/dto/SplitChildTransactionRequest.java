package net.nlacombe.vault.vaultws.api.dto;

import java.math.BigDecimal;

public class SplitChildTransactionRequest {

    private String description;
    private BigDecimal amount;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
