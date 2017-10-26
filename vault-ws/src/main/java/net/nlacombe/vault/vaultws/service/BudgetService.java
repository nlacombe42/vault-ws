package net.nlacombe.vault.vaultws.service;

import net.nlacombe.vault.vaultws.api.dto.Budget;
import net.nlacombe.vault.vaultws.api.dto.MonthBudgetCreationRequest;

import java.time.Instant;
import java.time.YearMonth;
import java.util.List;

public interface BudgetService
{
	Budget createMonthBudget(int userId, MonthBudgetCreationRequest monthBudgetCreationRequest);

	List<Budget> getBudgets(int userId, Instant startDate, Instant endDate);

	Budget getMonthEverythingElseBudget(int userId, YearMonth month);
}
