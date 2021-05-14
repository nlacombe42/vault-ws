package net.nlacombe.vault.vaultws.api.dto;

import java.util.List;

public class SplitTransactionRequest {

    private int transactionId;
    private List<SplitChildTransactionRequest> childTransactions;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public List<SplitChildTransactionRequest> getChildTransactions() {
        return childTransactions;
    }

    public void setChildTransactions(List<SplitChildTransactionRequest> childTransactions) {
        this.childTransactions = childTransactions;
    }
}
