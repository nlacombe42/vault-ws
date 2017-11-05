package net.nlacombe.vault.vaultws.service.impl;

import net.nlacombe.commonlib.util.DateUtil;
import net.nlacombe.vault.vaultws.api.dto.Budget;
import net.nlacombe.vault.vaultws.api.dto.BudgetType;
import net.nlacombe.vault.vaultws.api.dto.Category;
import net.nlacombe.vault.vaultws.api.dto.MonthBudgetCreationRequest;
import net.nlacombe.vault.vaultws.api.dto.MonthBudgetsInfo;
import net.nlacombe.vault.vaultws.api.dto.MonthStats;
import net.nlacombe.vault.vaultws.domain.UserConfig;
import net.nlacombe.vault.vaultws.entity.BudgetEntity;
import net.nlacombe.vault.vaultws.entity.CategoryEntity;
import net.nlacombe.vault.vaultws.mapper.BudgetMapper;
import net.nlacombe.vault.vaultws.repositorty.BudgetRepository;
import net.nlacombe.vault.vaultws.service.BudgetService;
import net.nlacombe.vault.vaultws.service.CategoryAccessService;
import net.nlacombe.vault.vaultws.service.CategoryService;
import net.nlacombe.vault.vaultws.service.TransactionService;
import net.nlacombe.vault.vaultws.service.UserConfigService;
import net.nlacombe.wsutils.restexception.exception.InvalidInputRestException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.YearMonth;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
public class BudgetServiceImpl implements BudgetService
{
	private BudgetRepository budgetRepository;
	private CategoryAccessService categoryAccessService;
	private BudgetMapper budgetMapper;
	private TransactionService transactionService;
	private CategoryService categoryService;
	private UserConfigService userConfigService;

	@Inject
	public BudgetServiceImpl(BudgetRepository budgetRepository, CategoryAccessService categoryAccessService, BudgetMapper budgetMapper, TransactionService transactionService, CategoryService categoryService, UserConfigService userConfigService)
	{
		this.budgetRepository = budgetRepository;
		this.categoryAccessService = categoryAccessService;
		this.budgetMapper = budgetMapper;
		this.transactionService = transactionService;
		this.categoryService = categoryService;
		this.userConfigService = userConfigService;
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
		budgetEntity.setStartDate(getStartOfMonth(userId, monthBudgetCreationRequest.getMonth()));
		budgetEntity.setEndDate(getLastSecondBeforeNextMonth(userId, monthBudgetCreationRequest.getMonth()));
		budgetEntity.setIncome(monthBudgetCreationRequest.isIncome());

		budgetEntity = budgetRepository.save(budgetEntity);

		BigDecimal budgetCurrentAmount = getBudgetCurrentAmount(userId, budgetEntity);

		return budgetMapper.mapToDto(budgetEntity, budgetCurrentAmount);
	}

	@Override
	public MonthBudgetsInfo getMonthBudgetsInfo(int userId, YearMonth month)
	{
		MonthStats monthStats = getMonthStats(userId, month);
		List<Budget> incomeBudgets = getBudgets(userId, month, true);
		List<Budget> spendingBudgets = getBudgets(userId, month, false);
		Budget unbudgeted = getMonthUnbudgeted(userId, month);

		return new MonthBudgetsInfo(monthStats, incomeBudgets, spendingBudgets, unbudgeted);
	}

	private List<Budget> getBudgets(int userId, YearMonth month, boolean income)
	{
		Instant startDate = getStartOfMonth(userId, month);
		Instant endDate = getLastSecondBeforeNextMonth(userId, month);

		return budgetRepository.findByRangeAndHasCategory(userId, startDate, endDate, income)
				.map(budgetEntity ->
						budgetMapper.mapToDto(budgetEntity, getBudgetCurrentAmount(userId, budgetEntity)))
				.collect(Collectors.toList());
	}

	private Budget getMonthUnbudgeted(int userId, YearMonth month)
	{
		Instant startDate = getStartOfMonth(userId, month);
		Instant endDate = getLastSecondBeforeNextMonth(userId, month);
		BigDecimal budgetCurrentAmount = getUnbudgetedCurrentAmount(userId, month);

		BudgetEntity unbudgetedEntity = budgetRepository.findSpendingByRangeAndDoesNotHaveCategory(userId, startDate, endDate);

		if (unbudgetedEntity == null)
			unbudgetedEntity = createUnbudgetedBudget(userId, startDate, endDate);

		return budgetMapper.mapToDto(unbudgetedEntity, budgetCurrentAmount);
	}

	private MonthStats getMonthStats(int userId, YearMonth month)
	{
		Instant startDate = getStartOfMonth(userId, month);
		Instant endDate = getLastSecondBeforeNextMonth(userId, month);

		BigDecimal totalPlannedMaxAmount = budgetRepository.getTotalPlannedMaxAmount(userId, startDate, endDate);
		totalPlannedMaxAmount = totalPlannedMaxAmount == null ? BigDecimal.ZERO : totalPlannedMaxAmount;

		BigDecimal currentAmount = transactionService.getTotalAmount(userId, startDate, endDate);

		return new MonthStats(totalPlannedMaxAmount, currentAmount);
	}

	private Instant getStartOfMonth(int userId, YearMonth yearMonth)
	{
		UserConfig userConfig = userConfigService.getUserConfig(userId);

		return DateUtil.getStartOfMonth(yearMonth, TimeZone.getTimeZone(userConfig.getTimezone()));
	}

	private Instant getLastSecondBeforeNextMonth(int userId, YearMonth yearMonth)
	{
		UserConfig userConfig = userConfigService.getUserConfig(userId);

		return DateUtil.getLastSecondBeforeNextMonth(yearMonth, TimeZone.getTimeZone(userConfig.getTimezone()));
	}

	private BudgetEntity createUnbudgetedBudget(int userId, Instant startDate, Instant endDate)
	{
		BudgetEntity everythingElseBudgetEntity = new BudgetEntity();
		everythingElseBudgetEntity.setCategory(null);
		everythingElseBudgetEntity.setUserId(userId);
		everythingElseBudgetEntity.setBudgetTypeCode(BudgetType.MONTH.getCode());
		everythingElseBudgetEntity.setPlannedMaxAmount(BigDecimal.ZERO);
		everythingElseBudgetEntity.setStartDate(startDate);
		everythingElseBudgetEntity.setEndDate(endDate);

		return budgetRepository.save(everythingElseBudgetEntity);
	}

	private BigDecimal getUnbudgetedCurrentAmount(int userId, YearMonth month)
	{
		Instant startDate = getStartOfMonth(userId, month);
		Instant endDate = getLastSecondBeforeNextMonth(userId, month);
		Set<Integer> unbudgetedCategoryIds = getUnbudgetedCategoryIds(userId, month);

		return transactionService.getCategoriesTotal(userId, unbudgetedCategoryIds, startDate, endDate);
	}

	private Set<Integer> getUnbudgetedCategoryIds(int userId, YearMonth month)
	{
		Set<Integer> allCategoryIds =
				categoryService.getCategories(userId).stream()
						.map(Category::getCategoryId)
						.collect(Collectors.toSet());

		Set<Integer> budgetedCategoyIds = getBudgets(userId, month, false).stream()
				.map(Budget::getCategoryId)
				.collect(Collectors.toSet());

		allCategoryIds.removeAll(budgetedCategoyIds);

		return allCategoryIds;
	}

	private BigDecimal getBudgetCurrentAmount(int userId, BudgetEntity budgetEntity)
	{
		return transactionService.getCategoryTotal(userId, budgetEntity.getCategory().getCategoryId(),
				budgetEntity.getStartDate(), budgetEntity.getEndDate());
	}

	private void validateNoDuplicateCategoryForMonth(int userId, YearMonth month, int categoryId)
	{
		Instant startDate = getStartOfMonth(userId, month);
		Instant endDate = getLastSecondBeforeNextMonth(userId, month);

		if (budgetRepository.existsByRangeAndCategoryId(userId, startDate, endDate, categoryId))
			throw new InvalidInputRestException("Month already contains budget with category ID: " + categoryId);
	}
}
