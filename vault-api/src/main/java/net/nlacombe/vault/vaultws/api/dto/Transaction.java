package net.nlacombe.vault.vaultws.api.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class Transaction
{
	private int transactionId;

	private Account account;

	private Category category;

	private Instant datetime;

	private String description;

	private BigDecimal amount;

	public int getTransactionId()
	{
		return transactionId;
	}

	public void setTransactionId(int transactionId)
	{
		this.transactionId = transactionId;
	}

	public Account getAccount()
	{
		return account;
	}

	public void setAccount(Account account)
	{
		this.account = account;
	}

	public Category getCategory()
	{
		return category;
	}

	public void setCategory(Category category)
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
