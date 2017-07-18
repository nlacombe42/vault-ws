package net.nlacombe.vault.vaultws.webservice.impl;

import net.maatvirtue.authlib.spring.AuthUtil;
import net.nlacombe.vault.vaultws.api.dto.Account;
import net.nlacombe.vault.vaultws.api.webservice.AccountWebService;
import net.nlacombe.vault.vaultws.service.AccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
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
}
