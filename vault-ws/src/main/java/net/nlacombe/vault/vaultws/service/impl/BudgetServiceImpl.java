package net.nlacombe.vault.vaultws.service.impl;

import net.nlacombe.vault.vaultws.api.dto.Budget;
import net.nlacombe.vault.vaultws.entity.BudgetEntity;
import net.nlacombe.vault.vaultws.entity.CategoryEntity;
import net.nlacombe.vault.vaultws.mapper.BudgetMapper;
import net.nlacombe.vault.vaultws.repositorty.BudgetRepository;
import net.nlacombe.vault.vaultws.service.BudgetService;
import net.nlacombe.vault.vaultws.service.CategoryAccessService;
import net.nlacombe.vault.vaultws.service.TransactionService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BudgetServiceImpl implements BudgetService
{
	private BudgetRepository budgetRepository;
	private CategoryAccessService categoryAccessService;
	private BudgetMapper budgetMapper;
	private TransactionService transactionService;

	@Inject
	public BudgetServiceImpl(BudgetRepository budgetRepository, CategoryAccessService categoryAccessService, BudgetMapper budgetMapper, TransactionService transactionService)
	{
		this.budgetRepository = budgetRepository;
		this.categoryAccessService = categoryAccessService;
		this.budgetMapper = budgetMapper;
		this.transactionService = transactionService;
	}

	@Override
	public Budget createBudget(int userId, Budget budget)
	{
		budget.setBudgetId(0);

		CategoryEntity categoryEntity = categoryAccessService.getCategoryEntity(userId, budget.getCategoryId());

		BudgetEntity budgetEntity = budgetMapper.mapToEntity(budget);
		budgetEntity.setCategory(categoryEntity);
		budgetEntity.setUserId(userId);

		budgetEntity = budgetRepository.save(budgetEntity);

		BigDecimal budgetCurrentAmount = getBudgetCurrentAmount(userId, budgetEntity);

		return budgetMapper.mapToDto(budgetEntity, budgetCurrentAmount);
	}

	@Override
	public List<Budget> getBudgets(int userId, Instant startDate, Instant endDate)
	{
		return budgetRepository.findByRange(userId, startDate, endDate)
				.map(budgetEntity ->
						budgetMapper.mapToDto(budgetEntity, getBudgetCurrentAmount(userId, budgetEntity)))
				.collect(Collectors.toList());
	}

	private BigDecimal getBudgetCurrentAmount(int userId, BudgetEntity budgetEntity)
	{
		return transactionService.getCategoryTotal(userId, budgetEntity.getCategory().getCategoryId(),
				budgetEntity.getStartDate(), budgetEntity.getEndDate());
	}
}
