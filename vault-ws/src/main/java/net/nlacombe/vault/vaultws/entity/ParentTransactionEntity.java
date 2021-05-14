package net.nlacombe.vault.vaultws.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "parent_transaction")
public class ParentTransactionEntity {

    @Id
    private int parentTransactionId;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private AccountEntity account;

    private Instant datetime;

    private String description;

    private BigDecimal amount;

    public int getParentTransactionId() {
        return parentTransactionId;
    }

    public void setParentTransactionId(int transactionId) {
        this.parentTransactionId = transactionId;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public Instant getDatetime() {
        return datetime;
    }

    public void setDatetime(Instant datetime) {
        this.datetime = datetime;
    }

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
