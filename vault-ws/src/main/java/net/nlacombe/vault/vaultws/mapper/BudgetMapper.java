package net.nlacombe.vault.vaultws.mapper;

import net.nlacombe.vault.vaultws.api.dto.Budget;
import net.nlacombe.vault.vaultws.api.dto.BudgetType;
import net.nlacombe.vault.vaultws.api.dto.BudgetWithTransactions;
import net.nlacombe.vault.vaultws.api.dto.Transaction;
import net.nlacombe.vault.vaultws.entity.BudgetEntity;
import net.nlacombe.vault.vaultws.entity.CategoryEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class BudgetMapper
{
	private BeanMapper<Budget, BudgetEntity> beanMapper;

	public BudgetMapper()
	{
		beanMapper = new BeanMapper<>(Budget.class, BudgetEntity.class);
	}

	public Budget mapToDto(BudgetEntity budgetEntity, BigDecimal currentAmount)
	{
		Budget budgetDto = beanMapper.mapToDto(budgetEntity);
		budgetDto.setCurrentAmount(currentAmount);
		budgetDto.setBudgetType(BudgetType.fromCode(budgetEntity.getBudgetTypeCode()));

		CategoryEntity category = budgetEntity.getCategory();

		if (category != null)
			budgetDto.setCategoryId(category.getCategoryId());

		return budgetDto;
	}

	public BudgetWithTransactions mapToBudgetWithTransactions(BudgetEntity budgetEntity, BigDecimal currentAmount, List<Transaction> transactions)
	{
		Budget budget = mapToDto(budgetEntity, currentAmount);

		BudgetWithTransactions budgetWithTransactions = new BudgetWithTransactions();
		BeanUtils.copyProperties(budget, budgetWithTransactions);
		budgetWithTransactions.setTransactions(transactions);

		return budgetWithTransactions;
	}
}
