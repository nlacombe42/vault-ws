package net.nlacombe.vault.vaultws.mapper;

import net.nlacombe.vault.vaultws.domain.UserConfig;
import net.nlacombe.vault.vaultws.entity.UserConfigEntity;
import org.springframework.stereotype.Component;

@Component
public class UserConfigMapper extends BeanMapper<UserConfig, UserConfigEntity>
{
	public UserConfigMapper()
	{
		super(UserConfig.class, UserConfigEntity.class);
	}
}
