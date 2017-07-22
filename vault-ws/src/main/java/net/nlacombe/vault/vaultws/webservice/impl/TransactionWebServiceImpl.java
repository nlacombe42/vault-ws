package net.nlacombe.vault.vaultws.webservice.impl;

import net.maatvirtue.authlib.spring.AuthUtil;
import net.nlacombe.vault.vaultws.api.dto.Transaction;
import net.nlacombe.vault.vaultws.api.webservice.TransactionWebService;
import net.nlacombe.vault.vaultws.service.TransactionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

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
}
