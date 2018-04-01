package net.nlacombe.vault.vaultws.mapper;

import net.nlacombe.vault.vaultws.api.dto.Category;
import net.nlacombe.vault.vaultws.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper extends BeanMapper<Category, CategoryEntity>
{
	public CategoryMapper()
	{
		super(Category.class, CategoryEntity.class);
	}

	public Category mapToDto(CategoryEntity categoryEntity, int numberOfUses)
	{
		Category category = super.mapToDto(categoryEntity);
		category.setNumberOfUses(numberOfUses);

		return category;
	}
}
