package net.nlacombe.vault.vaultws.service;

import net.nlacombe.vault.vaultws.api.dto.Account;

public interface AccountService
{
	Account createAccount(Account account);

	Account getAccountByName(int userId, String name);
}
