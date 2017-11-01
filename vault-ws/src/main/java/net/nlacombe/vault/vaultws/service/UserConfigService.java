package net.nlacombe.vault.vaultws.service;

import net.nlacombe.vault.vaultws.domain.UserConfig;

public interface UserConfigService
{
	UserConfig getUserConfig(int userId);
}
