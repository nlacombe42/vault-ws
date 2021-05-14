package net.nlacombe.vault.vaultws.repositorty;

import net.nlacombe.vault.vaultws.entity.ParentTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentTransactionRepository extends JpaRepository<ParentTransactionEntity, Integer> {
}
