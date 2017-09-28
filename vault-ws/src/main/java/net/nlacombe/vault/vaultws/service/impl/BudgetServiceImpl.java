package net.nlacombe.vault.vaultws.service.impl;

import net.nlacombe.vault.vaultws.api.dto.Budget;
import net.nlacombe.vault.vaultws.entity.BudgetEntity;
import net.nlacombe.vault.vaultws.entity.CategoryEntity;
import net.nlacombe.vault.vaultws.mapper.BudgetMapper;
import net.nlacombe.vault.vaultws.repositorty.BudgetRepository;
import net.nlacombe.vault.vaultws.service.BudgetService;
import net.nlacombe.vault.vaultws.service.CategoryAccessService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class BudgetServiceImpl implements BudgetService
{
	private BudgetRepository budgetRepository;
	private CategoryAccessService categoryAccessService;
	private BudgetMapper budgetMapper;

	@Inject
	public BudgetServiceImpl(BudgetRepository budgetRepository, CategoryAccessService categoryAccessService, BudgetMapper budgetMapper)
	{
		this.budgetRepository = budgetRepository;
		this.categoryAccessService = categoryAccessService;
		this.budgetMapper = budgetMapper;
	}

	@Override
	public Budget createBudget(int userId, Budget budget)
	{
		CategoryEntity categoryEntity = categoryAccessService.getCategoryEntity(userId, budget.getCategoryId());

		BudgetEntity budgetEntity = budgetMapper.mapToEntity(budget);
		budgetEntity.setCategory(categoryEntity);

		budgetEntity = budgetRepository.save(budgetEntity);

		return budgetMapper.mapToDto(budgetEntity);
	}
}
