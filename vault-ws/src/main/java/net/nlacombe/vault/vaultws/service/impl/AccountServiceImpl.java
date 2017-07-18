package net.nlacombe.vault.vaultws.service.impl;

import net.nlacombe.vault.vaultws.api.dto.Account;
import net.nlacombe.vault.vaultws.entity.AccountEntity;
import net.nlacombe.vault.vaultws.mapper.AccountMapper;
import net.nlacombe.vault.vaultws.repositorty.AccountRepository;
import net.nlacombe.vault.vaultws.service.AccountService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class AccountServiceImpl implements AccountService
{
	@Inject
	private AccountRepository accountRepository;

	@Inject
	private AccountMapper accountMapper;

	@Override
	public Account createAccount(Account account)
	{
		account.setAccountId(0);

		AccountEntity accountEntity = accountMapper.mapToEntity(account);
		accountEntity = accountRepository.save(accountEntity);

		return accountMapper.mapToDto(accountEntity);
	}
}
