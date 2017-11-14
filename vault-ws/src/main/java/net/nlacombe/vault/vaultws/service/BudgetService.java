package net.nlacombe.vault.vaultws.service;

import net.nlacombe.vault.vaultws.api.dto.Budget;
import net.nlacombe.vault.vaultws.api.dto.MonthBudgetCreationRequest;
import net.nlacombe.vault.vaultws.api.dto.MonthBudgetsInfo;
import net.nlacombe.vault.vaultws.api.dto.Transaction;

import java.time.YearMonth;
import java.util.List;

public interface BudgetService
{
	Budget createMonthBudget(int userId, MonthBudgetCreationRequest monthBudgetCreationRequest);

	MonthBudgetsInfo getMonthBudgetsInfo(int userId, YearMonth month);

	List<Transaction> getBudgetTransactions(int userId, int budgetId);
}
