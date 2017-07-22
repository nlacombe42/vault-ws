package net.nlacombe.vault.vaultws.repositorty;

import net.nlacombe.vault.vaultws.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer>
{
	AccountEntity findByUserIdAndName(int userId, String name);
}
