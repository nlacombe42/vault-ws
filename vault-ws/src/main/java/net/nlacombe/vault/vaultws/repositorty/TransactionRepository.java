package net.nlacombe.vault.vaultws.repositorty;

import net.nlacombe.vault.vaultws.entity.CategoryEntity;
import net.nlacombe.vault.vaultws.entity.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface TransactionRepository extends org.springframework.data.repository.Repository<TransactionEntity, Integer>
{
	Optional<TransactionEntity> findOne(Integer transactionId);

	TransactionEntity save(TransactionEntity transactionEntity);

	int countByAccountAccountIdAndDatetimeAndDescriptionAndAmount(int accountId, Instant datetime, String description, BigDecimal amount);

	Stream<TransactionEntity> findByCategory(CategoryEntity categoryEntity);

	Page<TransactionEntity> findByAccountUserIdOrderByDatetimeDesc(int userId, Pageable pageable);
}
