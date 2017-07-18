package net.nlacombe.vault.vaultws.mapper;

import org.springframework.beans.BeanUtils;

public class BeanMapper<DtoType, EntityType> implements Mapper<DtoType, EntityType>
{
	private Class<DtoType> dtoClass;
	private Class<EntityType> entityClass;

	public BeanMapper(Class<DtoType> dtoClass, Class<EntityType> entityClass)
	{
		this.dtoClass = dtoClass;
		this.entityClass = entityClass;
	}

	@Override
	public EntityType mapToEntity(DtoType dto)
	{
		EntityType entity = BeanUtils.instantiate(entityClass);

		BeanUtils.copyProperties(dto, entity);

		return entity;
	}

	@Override
	public DtoType mapToDto(EntityType entity)
	{
		DtoType dto = BeanUtils.instantiate(dtoClass);

		BeanUtils.copyProperties(entity, dto);

		return dto;
	}
}
