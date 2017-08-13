package net.nlacombe.vault.vaultws.webservice.impl;

import net.maatvirtue.authlib.spring.AuthUtil;
import net.nlacombe.vault.vaultws.api.dto.Account;
import net.nlacombe.vault.vaultws.api.webservice.AccountWebService;
import net.nlacombe.vault.vaultws.service.AccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class AccountWebServiceImpl implements AccountWebService
{
	@Inject
	private AccountService accountService;

	@PreAuthorize("isAuthenticated()")
	@Override
	public Account createAccount(Account account)
	{
		int userId = AuthUtil.getAuthenticatedUser().getUserId();

		account.setUserId(userId);

		return accountService.createAccount(account);
	}

	@PreAuthorize("isAuthenticated()")
	@Override
	public Account getAccountByName(String name)
	{
		int userId = AuthUtil.getAuthenticatedUser().getUserId();

		return accountService.getAccountByName(userId, name);
	}
}
