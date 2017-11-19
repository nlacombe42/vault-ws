package net.nlacombe.vault.vaultws.service.impl;

import net.nlacombe.commonlib.util.DateUtil;
import net.nlacombe.vault.vaultws.api.dto.Budget;
import net.nlacombe.vault.vaultws.api.dto.BudgetType;
import net.nlacombe.vault.vaultws.api.dto.BudgetWithTransactions;
import net.nlacombe.vault.vaultws.api.dto.Category;
import net.nlacombe.vault.vaultws.api.dto.MonthBudgetCreationRequest;
import net.nlacombe.vault.vaultws.api.dto.MonthBudgetsInfo;
import net.nlacombe.vault.vaultws.api.dto.MonthStats;
import net.nlacombe.vault.vaultws.api.dto.Transaction;
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
import net.nlacombe.wsutils.restexception.exception.NotFoundRestException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.YearMonth;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

		BigDecimal budgetCurrentAmount = getBudgetCurrentAmount(budgetEntity);

		return budgetMapper.mapToDto(budgetEntity, budgetCurrentAmount);
	}

	@Override
	public MonthBudgetsInfo getMonthBudgetsInfo(int userId, YearMonth month)
	{
		MonthStats monthStats = getMonthStats(userId, month);
		List<Budget> incomeBudgets = getBudgets(userId, month, true);
		List<Budget> spendingBudgets = getBudgets(userId, month, false);
		Budget unbudgeted = getMonthUnbudgetedBudget(userId, month);

		return new MonthBudgetsInfo(monthStats, incomeBudgets, spendingBudgets, unbudgeted);
	}

	@Override
	public BudgetWithTransactions getBudgetWithTransactions(int userId, int budgetId)
	{
		BudgetEntity budgetEntity = getBudget(userId, budgetId);
		BigDecimal budgetCurrentAmount = getBudgetCurrentAmount(budgetEntity);
		List<Transaction> transactions = getBudgetTransactions(budgetEntity).collect(Collectors.toList());

		return budgetMapper.mapToBudgetWithTransactions(budgetEntity, budgetCurrentAmount, transactions);
	}

	private Stream<Transaction> getBudgetTransactions(BudgetEntity budgetEntity)
	{
		if (isUnbudgetedBudget(budgetEntity))
			return getUnbudgetedTransactions(budgetEntity.getUserId(), budgetEntity.getStartDate(), budgetEntity.getEndDate());
		else
			return transactionService.getTransactions(budgetEntity.getUserId(), budgetEntity.getCategory().getCategoryId(), budgetEntity.getStartDate(), budgetEntity.getEndDate());
	}

	@Override
	public Stream<Category> getUnbudgetedCategories(int userId, YearMonth month)
	{
		Instant startDate = getStartOfMonth(userId, month);
		Instant endDate = getLastSecondBeforeNextMonth(userId, month);

		return categoryService.getCategories(getUnbudgetedCategoryIds(userId, startDate, endDate));
	}

	private boolean isUnbudgetedBudget(BudgetEntity budgetEntity)
	{
		return budgetEntity.getCategory() == null;
	}

	private BudgetEntity getBudget(int userId, int budgetId)
	{
		return budgetRepository.findByUserIdAndBudgetId(userId, budgetId)
				.orElseThrow(() -> new NotFoundRestException("Budget with user ID \"" + userId + "\" and budget ID \"" + budgetId + "\" not found."));
	}

	private List<Budget> getBudgets(int userId, YearMonth month, boolean income)
	{
		Instant startDate = getStartOfMonth(userId, month);
		Instant endDate = getLastSecondBeforeNextMonth(userId, month);

		return budgetRepository.findByRangeAndHasCategory(userId, startDate, endDate, income)
				.map(budgetEntity ->
						budgetMapper.mapToDto(budgetEntity, getBudgetCurrentAmount(budgetEntity)))
				.collect(Collectors.toList());
	}

	private Budget getMonthUnbudgetedBudget(int userId, YearMonth month)
	{
		Instant startDate = getStartOfMonth(userId, month);
		Instant endDate = getLastSecondBeforeNextMonth(userId, month);
		BigDecimal budgetCurrentAmount = getUnbudgetedCurrentAmount(userId, month);

		BudgetEntity unbudgetedEntity = budgetRepository.findUnbudgetedBudget(userId, startDate, endDate);

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
		return getUnbudgetedCurrentAmount(userId, startDate, endDate);
	}

	private BigDecimal getUnbudgetedCurrentAmount(int userId, Instant startDate, Instant endDate)
	{
		Set<Integer> unbudgetedCategoryIds = getUnbudgetedCategoryIds(userId, startDate, endDate);

		BigDecimal unbudgetedCurrentAmount = transactionService.getCategoriesTotal(userId, unbudgetedCategoryIds, startDate, endDate);

		return unbudgetedCurrentAmount.negate();
	}

	private Stream<Transaction> getUnbudgetedTransactions(int userId, Instant startDate, Instant endDate)
	{
		Set<Integer> unbudgetedCategoryIds = getUnbudgetedCategoryIds(userId, startDate, endDate);

		return transactionService.getTransactions(userId, unbudgetedCategoryIds, startDate, endDate);
	}

	private Set<Integer> getUnbudgetedCategoryIds(int userId, Instant startDate, Instant endDate)
	{
		Set<Integer> allCategoryIds =
				categoryService.getCategories(userId)
						.map(Category::getCategoryId)
						.collect(Collectors.toSet());

		Set<Integer> budgetedCategoryIds =
				budgetRepository.findBudgetedCategoryIds(userId, startDate, endDate).collect(Collectors.toSet());

		allCategoryIds.removeAll(budgetedCategoryIds);

		return allCategoryIds;
	}

	private BigDecimal getBudgetCurrentAmount(BudgetEntity budgetEntity)
	{
		if (isUnbudgetedBudget(budgetEntity))
			return getUnbudgetedCurrentAmount(budgetEntity.getUserId(), budgetEntity.getStartDate(), budgetEntity.getEndDate());

		BigDecimal categoryTotal = transactionService.getCategoryTotal(budgetEntity.getUserId(), budgetEntity.getCategory().getCategoryId(),
				budgetEntity.getStartDate(), budgetEntity.getEndDate());

		if (!budgetEntity.isIncome())
			categoryTotal = categoryTotal.negate();

		return categoryTotal;
	}

	private void validateNoDuplicateCategoryForMonth(int userId, YearMonth month, int categoryId)
	{
		Instant startDate = getStartOfMonth(userId, month);
		Instant endDate = getLastSecondBeforeNextMonth(userId, month);

		if (budgetRepository.existsByRangeAndCategoryId(userId, startDate, endDate, categoryId))
			throw new InvalidInputRestException("Month already contains budget with category ID: " + categoryId);
	}
}
