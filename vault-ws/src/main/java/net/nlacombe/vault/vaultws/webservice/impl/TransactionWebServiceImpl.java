package net.nlacombe.vault.vaultws.webservice.impl;

import net.nlacombe.authlib.spring.AuthUtil;
import net.nlacombe.vault.vaultws.api.dto.Account;
import net.nlacombe.vault.vaultws.api.dto.CategorizeRequest;
import net.nlacombe.vault.vaultws.api.dto.PaginationResponse;
import net.nlacombe.vault.vaultws.api.dto.SearchTransactionsRequest;
import net.nlacombe.vault.vaultws.api.dto.SplitTransactionRequest;
import net.nlacombe.vault.vaultws.api.dto.Transaction;
import net.nlacombe.vault.vaultws.api.webservice.TransactionWebService;
import net.nlacombe.vault.vaultws.service.AccountService;
import net.nlacombe.vault.vaultws.service.TransactionService;
import net.nlacombe.wsutils.restexception.exception.NotFoundRestException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class TransactionWebServiceImpl implements TransactionWebService
{
	private final TransactionService transactionService;
	private final AccountService accountService;

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
	public int countTransactions(int accountId, String datetimeText, String description, BigDecimal amount, Boolean includeParentTransactionsCanBeNull)
	{
		var includeParentTransactions = includeParentTransactionsCanBeNull != null && includeParentTransactionsCanBeNull;
		var datetime = Instant.parse(datetimeText);
		var userId = AuthUtil.getAuthenticatedUser().getUserId();

		validateUserHasAccount(accountId, userId);

		return transactionService.countTransactions(accountId, datetime, description, amount, includeParentTransactions);
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
	public void categorizeTransaction(int transactionId, CategorizeRequest categorizeRequest)
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

	@PreAuthorize("isAuthenticated()")
	@Override
	public Transaction getTransaction(int transactionId)
	{
		int userId = AuthUtil.getAuthenticatedUser().getUserId();

		return transactionService.getTransaction(userId, transactionId);
	}

	@PreAuthorize("isAuthenticated()")
	@Override
	public void deleteTransaction(int transactionId)
	{
		int userId = AuthUtil.getAuthenticatedUser().getUserId();

		transactionService.deleteTransaction(userId, transactionId);
	}

	@PreAuthorize("isAuthenticated()")
	@Override
	public void deleteTemporaryTransactions() {
		int userId = AuthUtil.getAuthenticatedUser().getUserId();

		transactionService.deleteTemporaryTransactions(userId);
	}

	@PreAuthorize("isAuthenticated()")
	@Override
	public void splitTransaction(SplitTransactionRequest splitTransactionRequest) {
		int userId = AuthUtil.getAuthenticatedUser().getUserId();

		transactionService.splitTransaction(userId, splitTransactionRequest);
	}

	private void validateUserHasAccount(int accountId, int userId)
	{
		Account account = accountService.getAccount(accountId);

		if (account == null || account.getUserId() != userId)
			throw new NotFoundRestException("Account ID " + accountId + " not found for user ID " + userId);
	}
}
