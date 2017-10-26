package net.nlacombe.vault.vaultws.api.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class Budget
{
	private int budgetId;
	private Integer categoryId;
	private BudgetType budgetType;
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

	public Integer getCategoryId()
	{
		return categoryId;
	}

	public void setCategoryId(Integer categoryId)
	{
		this.categoryId = categoryId;
	}

	public BudgetType getBudgetType()
	{
		return budgetType;
	}

	public void setBudgetType(BudgetType budgetType)
	{
		this.budgetType = budgetType;
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
