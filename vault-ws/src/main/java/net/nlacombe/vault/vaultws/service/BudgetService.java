package net.nlacombe.vault.vaultws.service;

import net.nlacombe.vault.vaultws.api.dto.Budget;
import net.nlacombe.vault.vaultws.api.dto.Category;
import net.nlacombe.vault.vaultws.api.dto.MonthBudgetCreationRequest;
import net.nlacombe.vault.vaultws.api.dto.MonthBudgetsInfo;
import net.nlacombe.vault.vaultws.api.dto.Transaction;

import java.time.YearMonth;
import java.util.stream.Stream;

public interface BudgetService
{
	Budget createMonthBudget(int userId, MonthBudgetCreationRequest monthBudgetCreationRequest);

	MonthBudgetsInfo getMonthBudgetsInfo(int userId, YearMonth month);

	Stream<Transaction> getBudgetTransactions(int userId, int budgetId);

	Stream<Category> getUnbudgetedCategories(int userId, YearMonth month);
}
