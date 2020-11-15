package net.nlacombe.vault.vaultws.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "transaction")
public class TransactionEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int transactionId;

	@ManyToOne
	@JoinColumn(name = "accountId")
	private AccountEntity account;

	@ManyToOne
	@JoinColumn(name = "categoryId")
	private CategoryEntity category;

	private Instant datetime;

	private String description;

	private BigDecimal amount;

	private boolean temporary;

	public boolean isTemporary() {
		return temporary;
	}

	public void setTemporary(boolean temporary) {
		this.temporary = temporary;
	}

	public int getTransactionId()
	{
		return transactionId;
	}

	public void setTransactionId(int transactionId)
	{
		this.transactionId = transactionId;
	}

	public AccountEntity getAccount()
	{
		return account;
	}

	public void setAccount(AccountEntity account)
	{
		this.account = account;
	}

	public CategoryEntity getCategory()
	{
		return category;
	}

	public void setCategory(CategoryEntity category)
	{
		this.category = category;
	}

	public Instant getDatetime()
	{
		return datetime;
	}

	public void setDatetime(Instant datetime)
	{
		this.datetime = datetime;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}
}
