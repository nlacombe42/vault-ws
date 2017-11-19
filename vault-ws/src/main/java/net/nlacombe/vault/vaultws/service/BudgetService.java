package net.nlacombe.vault.vaultws.service;

import net.nlacombe.vault.vaultws.api.dto.Budget;
import net.nlacombe.vault.vaultws.api.dto.BudgetWithTransactions;
import net.nlacombe.vault.vaultws.api.dto.Category;
import net.nlacombe.vault.vaultws.api.dto.MonthBudgetCreationRequest;
import net.nlacombe.vault.vaultws.api.dto.MonthBudgetsInfo;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.stream.Stream;

public interface BudgetService
{
	Budget createMonthBudget(int userId, MonthBudgetCreationRequest monthBudgetCreationRequest);

	MonthBudgetsInfo getMonthBudgetsInfo(int userId, YearMonth month);

	BudgetWithTransactions getBudgetWithTransactions(int userId, int budgetId);

	Stream<Category> getUnbudgetedCategories(int userId, YearMonth month);

	void updateBudgetPlannedMaxAmount(int userId, int budgetId, BigDecimal plannedMaxAmount);
}
