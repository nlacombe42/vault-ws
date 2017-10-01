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
@Table(name = "budget")
public class BudgetEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int budgetId;

	@ManyToOne
	@JoinColumn(name = "categoryId")
	private CategoryEntity category;

	private int userId;

	private Instant startDate;

	private Instant endDate;

	private BigDecimal amount;

	public int getBudgetId()
	{
		return budgetId;
	}

	public void setBudgetId(int budgetId)
	{
		this.budgetId = budgetId;
	}

	public CategoryEntity getCategory()
	{
		return category;
	}

	public void setCategory(CategoryEntity category)
	{
		this.category = category;
	}

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
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

	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}
}
