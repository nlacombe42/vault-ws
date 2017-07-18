package net.nlacombe.vault.vaultws.mapper;

public interface Mapper<DtoType, EntityType>
{
	EntityType mapToEntity(DtoType dto);

	DtoType mapToDto(EntityType entity);
}
