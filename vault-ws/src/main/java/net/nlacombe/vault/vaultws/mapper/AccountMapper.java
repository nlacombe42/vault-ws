package net.nlacombe.vault.vaultws.mapper;

import net.nlacombe.vault.vaultws.api.dto.Account;
import net.nlacombe.vault.vaultws.entity.AccountEntity;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper extends BeanMapper<Account, AccountEntity>
{
	public AccountMapper()
	{
		super(Account.class, AccountEntity.class);
	}
}
