package net.nlacombe.vault.vaultws.service;

import net.nlacombe.vault.vaultws.api.dto.Budget;

import java.time.Instant;
import java.util.List;

public interface BudgetService
{
	Budget createBudget(int userId, Budget budget);

	List<Budget> getBudgets(int userId, Instant startDate, Instant endDate);
}
