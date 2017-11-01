package net.nlacombe.vault.vaultws.repositorty;

import net.nlacombe.vault.vaultws.entity.UserConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserConfigRepository extends JpaRepository<UserConfigEntity, Integer>
{
	UserConfigEntity findByUserId(int userId);
}
