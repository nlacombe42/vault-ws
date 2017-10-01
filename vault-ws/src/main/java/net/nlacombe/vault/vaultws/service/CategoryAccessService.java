package net.nlacombe.vault.vaultws.service;

import net.nlacombe.vault.vaultws.entity.CategoryEntity;

public interface CategoryAccessService
{
	CategoryEntity getCategoryEntity(int userId, Integer categoryId);
}
