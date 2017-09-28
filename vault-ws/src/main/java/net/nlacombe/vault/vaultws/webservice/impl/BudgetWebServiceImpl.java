package net.nlacombe.vault.vaultws.webservice.impl;

import net.nlacombe.authlib.spring.AuthUtil;
import net.nlacombe.vault.vaultws.api.dto.Budget;
import net.nlacombe.vault.vaultws.api.webservice.BudgetWebService;
import net.nlacombe.vault.vaultws.service.BudgetService;
import net.nlacombe.vault.vaultws.service.CategoryAccessService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.YearMonth;

@Service
@Transactional
public class BudgetWebServiceImpl implements BudgetWebService
{
	private BudgetService budgetService;

	@Inject
	public BudgetWebServiceImpl(BudgetService budgetService)
	{
		this.budgetService = budgetService;
	}

	@PreAuthorize("isAuthenticated()")
	@Override
	public Budget createBudget(Budget budget)
	{
		int userId = AuthUtil.getAuthenticatedUser().getUserId();
		budget.setBudgetId(0);

		return budgetService.createBudget(userId, budget);
	}
}
