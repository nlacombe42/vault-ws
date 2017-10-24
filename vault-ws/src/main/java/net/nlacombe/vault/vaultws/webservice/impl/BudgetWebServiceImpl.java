package net.nlacombe.vault.vaultws.webservice.impl;

import net.nlacombe.authlib.spring.AuthUtil;
import net.nlacombe.vault.vaultws.api.dto.Budget;
import net.nlacombe.vault.vaultws.api.dto.MonthBudgetCreationRequest;
import net.nlacombe.vault.vaultws.api.webservice.BudgetWebService;
import net.nlacombe.vault.vaultws.service.BudgetService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.Instant;
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
	public List<Budget> getBudgets(String startDateText, String endDateText)
	{
		int userId = AuthUtil.getAuthenticatedUser().getUserId();
		Instant startDate = Instant.parse(startDateText);
		Instant endDate = Instant.parse(endDateText);

		return budgetService.getBudgets(userId, startDate, endDate);
	}
}
