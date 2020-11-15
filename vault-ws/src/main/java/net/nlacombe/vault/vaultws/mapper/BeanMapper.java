package net.nlacombe.vault.vaultws.mapper;

import net.nlacombe.commonlib.mapping.AbstractBeanMapper;
import org.springframework.beans.BeanUtils;

public class BeanMapper<DtoType, DomainType> extends AbstractBeanMapper<DtoType, DomainType>
{
	private final Class<DtoType> dtoClass;
	private final Class<DomainType> domainClass;

	public BeanMapper(Class<DtoType> dtoClass, Class<DomainType> domainClass)
	{
		this.dtoClass = dtoClass;
		this.domainClass = domainClass;
	}

	@Override
	public DomainType mapToDomainObject(DtoType dto)
	{
		if (dto == null)
			return null;

		DomainType domainObject = BeanUtils.instantiateClass(domainClass);

		BeanUtils.copyProperties(dto, domainObject);

		return domainObject;
	}

	@Override
	public DtoType mapToDto(DomainType domainObject)
	{
		if (domainObject == null)
			return null;

		DtoType dto = BeanUtils.instantiateClass(dtoClass);

		BeanUtils.copyProperties(domainObject, dto);

		return dto;
	}
}
