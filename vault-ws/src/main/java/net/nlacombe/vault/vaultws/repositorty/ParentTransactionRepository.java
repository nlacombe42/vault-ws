package net.nlacombe.vault.vaultws.repositorty;

import net.nlacombe.vault.vaultws.entity.ParentTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;

@Repository
public interface ParentTransactionRepository extends JpaRepository<ParentTransactionEntity, Integer> {

    int countByAccountAccountIdAndDatetimeAndDescriptionAndAmount(int accountId, Instant datetime, String description, BigDecimal amount);

}
