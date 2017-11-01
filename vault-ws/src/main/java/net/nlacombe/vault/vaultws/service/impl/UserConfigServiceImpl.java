package net.nlacombe.vault.vaultws.service.impl;

import net.nlacombe.vault.vaultws.domain.UserConfig;
import net.nlacombe.vault.vaultws.entity.UserConfigEntity;
import net.nlacombe.vault.vaultws.mapper.UserConfigMapper;
import net.nlacombe.vault.vaultws.repositorty.UserConfigRepository;
import net.nlacombe.vault.vaultws.service.UserConfigService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class UserConfigServiceImpl implements UserConfigService
{
	private UserConfigRepository userConfigRepository;
	private UserConfigMapper userConfigMapper;

	@Inject
	public UserConfigServiceImpl(UserConfigRepository userConfigRepository, UserConfigMapper userConfigMapper)
	{
		this.userConfigRepository = userConfigRepository;
		this.userConfigMapper = userConfigMapper;
	}

	@Override
	public UserConfig getUserConfig(int userId)
	{
		UserConfigEntity userConfigEntity = userConfigRepository.findByUserId(userId);

		return userConfigMapper.mapToDto(userConfigEntity);
	}
}
