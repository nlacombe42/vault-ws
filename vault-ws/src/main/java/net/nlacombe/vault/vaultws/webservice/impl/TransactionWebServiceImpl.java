package net.nlacombe.vault.vaultws.webservice.impl;

import net.maatvirtue.authlib.spring.AuthUtil;
import net.nlacombe.vault.vaultws.api.dto.Account;
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

@Service
@Transactional
public class TransactionWebServiceImpl implements TransactionWebService
{
	@Inject
	private TransactionService transactionService;

	@Inject
	private AccountService accountService;

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

	private void validateUserHasAccount(int accountId, int userId)
	{
		Account account = accountService.getAccount(accountId);

		if (account == null || account.getUserId() != userId)
			throw new NotFoundRestException("Account ID " + accountId + " not found for user ID " + userId);
	}
}
