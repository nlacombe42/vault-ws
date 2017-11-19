package net.nlacombe.vault.vaultws.service.impl;

import net.nlacombe.vault.vaultws.api.dto.Category;
import net.nlacombe.vault.vaultws.mapper.CategoryMapper;
import net.nlacombe.vault.vaultws.repositorty.CategoryRepository;
import net.nlacombe.vault.vaultws.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.stream.Stream;

@Service
public class CategoryServiceImpl implements CategoryService
{
	private CategoryRepository categoryRepository;
	private CategoryMapper categoryMapper;

	@Inject
	public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper)
	{
		this.categoryRepository = categoryRepository;
		this.categoryMapper = categoryMapper;
	}

	@Override
	public Stream<Category> getCategories(int userId)
	{
		return categoryRepository.findByUserId(userId)
				.map(categoryMapper::mapToDto);
	}
}
