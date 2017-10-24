package net.nlacombe.vault.vaultws.api.dto;

import java.math.BigDecimal;
import java.time.YearMonth;

public class MonthBudgetCreationRequest
{
	private int categoryId;
	private YearMonth month;
	private BigDecimal plannedMaxAmount;

	public int getCategoryId()
	{
		return categoryId;
	}

	public void setCategoryId(int categoryId)
	{
		this.categoryId = categoryId;
	}

	public YearMonth getMonth()
	{
		return month;
	}

	public void setMonth(YearMonth month)
	{
		this.month = month;
	}

	public BigDecimal getPlannedMaxAmount()
	{
		return plannedMaxAmount;
	}

	public void setPlannedMaxAmount(BigDecimal plannedMaxAmount)
	{
		this.plannedMaxAmount = plannedMaxAmount;
	}
}
