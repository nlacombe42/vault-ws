package net.nlacombe.vault.vaultws.webservice.impl;

import net.nlacombe.authlib.spring.AuthUtil;
import net.nlacombe.vault.vaultws.api.dto.Budget;
import net.nlacombe.vault.vaultws.api.dto.MonthBudgetCreationRequest;
import net.nlacombe.vault.vaultws.api.dto.MonthBudgetsInfo;
import net.nlacombe.vault.vaultws.api.dto.Transaction;
import net.nlacombe.vault.vaultws.api.webservice.BudgetWebService;
import net.nlacombe.vault.vaultws.service.BudgetService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.YearMonth;
import java.util.List;

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
	public Budget createMonthBudget(MonthBudgetCreationRequest monthBudgetCreationRequest)
	{
		int userId = AuthUtil.getAuthenticatedUser().getUserId();

		return budgetService.createMonthBudget(userId, monthBudgetCreationRequest);
	}

	@PreAuthorize("isAuthenticated()")
	@Override
	public MonthBudgetsInfo getMonthBudgetsInfo(String monthIsoString)
	{
		int userId = AuthUtil.getAuthenticatedUser().getUserId();
		YearMonth month = YearMonth.parse(monthIsoString);

		return budgetService.getMonthBudgetsInfo(userId, month);
	}

	@PreAuthorize("isAuthenticated()")
	@Override
	public List<Transaction> getBudgetTransactions(int budgetId)
	{
		int userId = AuthUtil.getAuthenticatedUser().getUserId();

		return budgetService.getBudgetTransactions(userId, budgetId);
	}
}
