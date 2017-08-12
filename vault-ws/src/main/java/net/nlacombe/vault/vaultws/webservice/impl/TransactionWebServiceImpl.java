package net.nlacombe.vault.vaultws.webservice.impl;

import net.maatvirtue.authlib.spring.AuthUtil;
import net.nlacombe.vault.vaultws.api.dto.Transaction;
import net.nlacombe.vault.vaultws.api.webservice.TransactionWebService;
import net.nlacombe.vault.vaultws.service.TransactionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.Instant;

@Service
public class TransactionWebServiceImpl implements TransactionWebService
{
	@Inject
	private TransactionService transactionService;

	@PreAuthorize("isAuthenticated()")
	@Override
	public Transaction createTransaction(Transaction transaction)
	{
		int userId = AuthUtil.getAuthenticatedUser().getUserId();

		return transactionService.createTransaction(userId, transaction);
	}

	@PreAuthorize("isAuthenticated()")
	@Override
	public boolean transactionExists(String datetimeText, String description, BigDecimal amount)
	{
		int userId = AuthUtil.getAuthenticatedUser().getUserId();
		Instant datetime = Instant.parse(datetimeText);

		return transactionService.transactionExists(userId, datetime, description, amount);
	}
}
