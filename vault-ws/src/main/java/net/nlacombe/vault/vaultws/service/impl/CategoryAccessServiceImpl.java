package net.nlacombe.vault.vaultws.service.impl;

import net.nlacombe.vault.vaultws.entity.CategoryEntity;
import net.nlacombe.vault.vaultws.repositorty.CategoryRepository;
import net.nlacombe.vault.vaultws.service.CategoryAccessService;
import net.nlacombe.wsutils.restexception.exception.NotFoundRestException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class CategoryAccessServiceImpl implements CategoryAccessService
{
	private CategoryRepository categoryRepository;

	@Inject
	public CategoryAccessServiceImpl(CategoryRepository categoryRepository)
	{
		this.categoryRepository = categoryRepository;
	}

	@Override
	public CategoryEntity getCategoryEntity(int userId, Integer categoryId)
	{
		if (categoryId == null)
			return null;

		CategoryEntity categoryEntity = categoryRepository.getOne(categoryId);

		if (categoryEntity == null || categoryEntity.getUserId() != userId)
			throw new NotFoundRestException("Category ID " + categoryId + " not found for user ID " + userId);

		return categoryEntity;
	}
}
