package net.nlacombe.vault.vaultws.webservice.impl;

import net.maatvirtue.authlib.spring.AuthUtil;
import net.nlacombe.vault.vaultws.api.dto.Account;
import net.nlacombe.vault.vaultws.api.dto.CategorizeRequest;
import net.nlacombe.vault.vaultws.api.dto.PaginationResponse;
import net.nlacombe.vault.vaultws.api.dto.SearchTransactionsRequest;
import net.nlacombe.vault.vaultws.api.dto.Transaction;
import net.nlacombe.vault.vaultws.api.webservice.TransactionWebService;
import net.nlacombe.vault.vaultws.service.AccountService;
import net.nlacombe.vault.vaultws.service.TransactionService;
import net.nlacombe.wsutils.restexception.exception.NotFoundRestException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class TransactionWebServiceImpl implements TransactionWebService
{
	private TransactionService transactionService;
	private AccountService accountService;

	@Inject
	public TransactionWebServiceImpl(TransactionService transactionService, AccountService accountService)
	{
		this.transactionService = transactionService;
		this.accountService = accountService;
	}

	@PreAuthorize("isAuthenticated()")
	@Override
	public Transaction createTransaction(Transaction transaction)
	{
		int userId = AuthUtil.getAuthenticatedUser().getUserId();

		return transactionService.createTransaction(userId, transaction);
	}

	@PreAuthorize("isAuthenticated()")
	@Override
	public int countTransactions(int accountId, String datetimeText, String description, BigDecimal amount)
	{
		Instant datetime = Instant.parse(datetimeText);
		int userId = AuthUtil.getAuthenticatedUser().getUserId();

		validateUserHasAccount(accountId, userId);

		return transactionService.countTransactions(accountId, datetime, description, amount);
	}

	@PreAuthorize("isAuthenticated()")
	@Override
	public List<Transaction> getUncategorizedTransactions()
	{
		int userId = AuthUtil.getAuthenticatedUser().getUserId();

		return transactionService.getUncategorizedTransactions(userId);
	}

	@PreAuthorize("isAuthenticated()")
	@Override
	public void categorizeTransaction(Integer transactionId, CategorizeRequest categorizeRequest)
	{
		int userId = AuthUtil.getAuthenticatedUser().getUserId();

		transactionService.categorizeTransaction(userId, transactionId, categorizeRequest.getCategoryId());
	}

	@PreAuthorize("isAuthenticated()")
	@Override
	public PaginationResponse<Transaction> searchTransactions(SearchTransactionsRequest searchTransactionsRequest)
	{
		int userId = AuthUtil.getAuthenticatedUser().getUserId();

		return transactionService.searchTransactions(userId, searchTransactionsRequest);
	}

	private void validateUserHasAccount(int accountId, int userId)
	{
		Account account = accountService.getAccount(accountId);

		if (account == null || account.getUserId() != userId)
			throw new NotFoundRestException("Account ID " + accountId + " not found for user ID " + userId);
	}
}
