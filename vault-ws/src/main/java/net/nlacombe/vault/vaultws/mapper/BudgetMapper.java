package net.nlacombe.vault.vaultws.mapper;

import net.nlacombe.vault.vaultws.api.dto.Budget;
import net.nlacombe.vault.vaultws.api.dto.Transaction;
import net.nlacombe.vault.vaultws.entity.BudgetEntity;
import net.nlacombe.vault.vaultws.entity.CategoryEntity;
import net.nlacombe.vault.vaultws.entity.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class BudgetMapper extends BeanMapper<Budget, BudgetEntity>
{
	public BudgetMapper()
	{
		super(Budget.class, BudgetEntity.class);
	}

	@Override
	public Budget mapToDto(BudgetEntity budgetEntity)
	{
		Budget budgetDto = super.mapToDto(budgetEntity);

		CategoryEntity category = budgetEntity.getCategory();

		if (category != null)
			budgetDto.setCategoryId(category.getCategoryId());

		return budgetDto;
	}
}
