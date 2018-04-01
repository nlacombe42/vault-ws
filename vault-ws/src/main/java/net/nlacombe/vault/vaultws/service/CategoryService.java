package net.nlacombe.vault.vaultws.service;

import net.nlacombe.vault.vaultws.api.dto.Category;

import java.util.Collection;
import java.util.stream.Stream;

public interface CategoryService
{
	Stream<Category> getCategories(int userId);

	Stream<Category> getCategories(Collection<Integer> categoryIds);

	Category createCategory(int userId, String name);
}
