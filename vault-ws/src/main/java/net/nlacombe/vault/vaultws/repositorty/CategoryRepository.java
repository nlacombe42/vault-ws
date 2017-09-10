package net.nlacombe.vault.vaultws.repositorty;

import net.nlacombe.vault.vaultws.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer>
{
	Stream<CategoryEntity> findByUserId(int userId);
}