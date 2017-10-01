package net.nlacombe.vault.vaultws.service;

import net.nlacombe.vault.vaultws.api.dto.Budget;

public interface BudgetService
{
	Budget createBudget(int userId, Budget budget);
}
