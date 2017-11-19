package net.nlacombe.vault.vaultws.api.dto;

import java.util.List;

public class BudgetWithTransactions extends Budget
{
	private List<Transaction> transactions;

	public List<Transaction> getTransactions()
	{
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions)
	{
		this.transactions = transactions;
	}
}
