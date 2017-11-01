package net.nlacombe.vault.vaultws.api.dto;

import java.math.BigDecimal;

public class MonthStats
{
	private BigDecimal totalPlannedMaxAmount;
	private BigDecimal currentAmount;

	public MonthStats(BigDecimal totalPlannedMaxAmount, BigDecimal currentAmount)
	{
		this.totalPlannedMaxAmount = totalPlannedMaxAmount;
		this.currentAmount = currentAmount;
	}

	public BigDecimal getTotalPlannedMaxAmount()
	{
		return totalPlannedMaxAmount;
	}

	public void setTotalPlannedMaxAmount(BigDecimal totalPlannedMaxAmount)
	{
		this.totalPlannedMaxAmount = totalPlannedMaxAmount;
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
