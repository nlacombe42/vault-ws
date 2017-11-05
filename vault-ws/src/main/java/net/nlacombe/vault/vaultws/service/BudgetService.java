package net.nlacombe.vault.vaultws.service;

import net.nlacombe.vault.vaultws.api.dto.Budget;
import net.nlacombe.vault.vaultws.api.dto.MonthBudgetCreationRequest;
import net.nlacombe.vault.vaultws.api.dto.MonthBudgetsInfo;

import java.time.YearMonth;

public interface BudgetService
{
	Budget createMonthBudget(int userId, MonthBudgetCreationRequest monthBudgetCreationRequest);

	MonthBudgetsInfo getMonthBudgetsInfo(int userId, YearMonth month);
}
