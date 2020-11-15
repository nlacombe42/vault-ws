package net.nlacombe.vault.vaultws.api.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class Transaction
{
	private int transactionId;

	private int accountId;

	private Integer categoryId;

	private Instant datetime;

	private String description;

	private BigDecimal amount;

	private Boolean temporary;

	public Boolean getTemporary() {
		return temporary;
	}

	public void setTemporary(Boolean temporary) {
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

	public int getAccountId()
	{
		return accountId;
	}

	public void setAccountId(int accountId)
	{
		this.accountId = accountId;
	}

	public Integer getCategoryId()
	{
		return categoryId;
	}

	public void setCategoryId(Integer categoryId)
	{
		this.categoryId = categoryId;
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
