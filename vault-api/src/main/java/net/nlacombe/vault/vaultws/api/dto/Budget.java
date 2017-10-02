package net.nlacombe.vault.vaultws.api.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class Budget
{
	private int budgetId;
	private int categoryId;
	private Instant startDate;
	private Instant endDate;
	private BigDecimal plannedMaxAmount;
	private BigDecimal currentAmount;

	public int getBudgetId()
	{
		return budgetId;
	}

	public void setBudgetId(int budgetId)
	{
		this.budgetId = budgetId;
	}

	public int getCategoryId()
	{
		return categoryId;
	}

	public void setCategoryId(int categoryId)
	{
		this.categoryId = categoryId;
	}

	public Instant getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Instant startDate)
	{
		this.startDate = startDate;
	}

	public Instant getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Instant endDate)
	{
		this.endDate = endDate;
	}

	public BigDecimal getPlannedMaxAmount()
	{
		return plannedMaxAmount;
	}

	public void setPlannedMaxAmount(BigDecimal plannedMaxAmount)
	{
		this.plannedMaxAmount = plannedMaxAmount;
	}

	public BigDecimal getCurrentAmount()
	{
		return currentAmount;
	}

	public void setCurrentAmount(BigDecimal currentAmount)
	{
		this.currentAmount = currentAmount;
	}
}
