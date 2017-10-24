package net.nlacombe.vault.vaultws.service.impl;

import net.nlacombe.commonlib.util.DateUtil;
import net.nlacombe.vault.vaultws.api.dto.Budget;
import net.nlacombe.vault.vaultws.api.dto.BudgetType;
import net.nlacombe.vault.vaultws.api.dto.MonthBudgetCreationRequest;
import net.nlacombe.vault.vaultws.entity.BudgetEntity;
import net.nlacombe.vault.vaultws.entity.CategoryEntity;
import net.nlacombe.vault.vaultws.mapper.BudgetMapper;
import net.nlacombe.vault.vaultws.repositorty.BudgetRepository;
import net.nlacombe.vault.vaultws.service.BudgetService;
import net.nlacombe.vault.vaultws.service.CategoryAccessService;
import net.nlacombe.vault.vaultws.service.TransactionService;
import net.nlacombe.wsutils.restexception.exception.InvalidInputRestException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.YearMonth;
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
	public Budget createMonthBudget(int userId, MonthBudgetCreationRequest monthBudgetCreationRequest)
	{
		validateNoDuplicateCategoryForMonth(userId, monthBudgetCreationRequest.getMonth(), monthBudgetCreationRequest.getCategoryId());

		CategoryEntity categoryEntity = categoryAccessService.getCategoryEntity(userId, monthBudgetCreationRequest.getCategoryId());

		BudgetEntity budgetEntity = new BudgetEntity();
		budgetEntity.setCategory(categoryEntity);
		budgetEntity.setUserId(userId);
		budgetEntity.setBudgetTypeCode(BudgetType.MONTH.getCode());
		budgetEntity.setPlannedMaxAmount(monthBudgetCreationRequest.getPlannedMaxAmount());
		budgetEntity.setStartDate(DateUtil.getStartOfMonthUtc(monthBudgetCreationRequest.getMonth()));
		budgetEntity.setEndDate(DateUtil.getLastSecondBeforeNextMonthUtc(monthBudgetCreationRequest.getMonth()));

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

	private void validateNoDuplicateCategoryForMonth(int userId, YearMonth month, int categoryId)
	{
		Instant startDate = DateUtil.getStartOfMonthUtc(month);
		Instant endDate = DateUtil.getLastSecondBeforeNextMonthUtc(month);

		if (budgetRepository.existsByRangeAndCategoryId(userId, startDate, endDate, categoryId))
			throw new InvalidInputRestException("Month already contains budget with category ID: " + categoryId);
	}
}
