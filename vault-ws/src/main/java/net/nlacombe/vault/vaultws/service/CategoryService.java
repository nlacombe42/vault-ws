package net.nlacombe.vault.vaultws.service;

import net.nlacombe.vault.vaultws.api.dto.Category;

import java.util.List;

public interface CategoryService
{
	List<Category> getCategories(int userId);
}
