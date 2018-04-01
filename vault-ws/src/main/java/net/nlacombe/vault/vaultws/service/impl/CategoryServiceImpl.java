package net.nlacombe.vault.vaultws.service.impl;

import net.nlacombe.vault.vaultws.api.dto.Category;
import net.nlacombe.vault.vaultws.entity.CategoryEntity;
import net.nlacombe.vault.vaultws.mapper.CategoryMapper;
import net.nlacombe.vault.vaultws.repositorty.CategoryRepository;
import net.nlacombe.vault.vaultws.repositorty.TransactionRepository;
import net.nlacombe.vault.vaultws.service.CategoryService;
import net.nlacombe.wsutils.restexception.exception.DuplicateRestException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;
import java.util.stream.Stream;

@Service
public class CategoryServiceImpl implements CategoryService
{
	private CategoryRepository categoryRepository;
	private TransactionRepository transactionRepository;
	private CategoryMapper categoryMapper;

	@Inject
	public CategoryServiceImpl(CategoryRepository categoryRepository, TransactionRepository transactionRepository, CategoryMapper categoryMapper)
	{
		this.categoryRepository = categoryRepository;
		this.transactionRepository = transactionRepository;
		this.categoryMapper = categoryMapper;
	}

	@Override
	public Stream<Category> getCategories(int userId)
	{
		return categoryRepository.findByUserId(userId)
				.map(this::mapToDto);
	}

	@Override
	public Stream<Category> getCategories(Collection<Integer> categoryIds)
	{
		return categoryRepository.findByCategoryIdIn(categoryIds)
				.map(this::mapToDto);
	}

	@Override
	public Category createCategory(int userId, String name)
	{
		if (categoryRepository.existsByUserIdAndName(userId, name))
			throw new DuplicateRestException("Category with name \"" + name + "\" already exists.");

		CategoryEntity categoryEntity = categoryRepository.save(new CategoryEntity(userId, name));

		return mapToDto(categoryEntity);
	}

	private Category mapToDto(CategoryEntity categoryEntity)
	{
		return categoryMapper.mapToDto(categoryEntity, transactionRepository.countByCategory(categoryEntity));
	}
}
