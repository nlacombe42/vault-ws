package net.nlacombe.vault.vaultws.repositorty;

import net.nlacombe.vault.vaultws.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer>
{
}
