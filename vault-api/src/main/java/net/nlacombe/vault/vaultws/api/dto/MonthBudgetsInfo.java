package net.nlacombe.vault.vaultws.api.dto;

import java.util.List;

public class MonthBudgetsInfo
{
	private MonthStats monthStats;
	private List<Budget> incomeBudgets;
	private List<Budget> spendingBudgets;
	private Budget unbudgeted;

	public MonthBudgetsInfo(MonthStats monthStats, List<Budget> incomeBudgets, List<Budget> spendingBudgets, Budget unbudgeted)
	{
		this.monthStats = monthStats;
		this.incomeBudgets = incomeBudgets;
		this.spendingBudgets = spendingBudgets;
		this.unbudgeted = unbudgeted;
	}

	public MonthStats getMonthStats()
	{
		return monthStats;
	}

	public List<Budget> getIncomeBudgets()
	{
		return incomeBudgets;
	}

	public List<Budget> getSpendingBudgets()
	{
		return spendingBudgets;
	}

	public Budget getUnbudgeted()
	{
		return unbudgeted;
	}
}
