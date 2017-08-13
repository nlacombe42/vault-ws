package net.nlacombe.vault.vaultws.repositorty;

import net.nlacombe.vault.vaultws.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer>
{
	int countByAccountAccountIdAndDatetimeAndDescriptionAndAmount(int accountId, Instant datetime, String description, BigDecimal amount);
}
