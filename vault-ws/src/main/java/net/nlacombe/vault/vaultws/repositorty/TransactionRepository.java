package net.nlacombe.vault.vaultws.repositorty;

import net.nlacombe.vault.vaultws.entity.CategoryEntity;
import net.nlacombe.vault.vaultws.entity.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface TransactionRepository extends org.springframework.data.repository.Repository<TransactionEntity, Integer>
{
	Optional<TransactionEntity> getOne(Integer transactionId);

	void delete(TransactionEntity transactionEntity);

	TransactionEntity save(TransactionEntity transactionEntity);

	int countByAccountAccountIdAndDatetimeAndDescriptionAndAmount(int accountId, Instant datetime, String description, BigDecimal amount);

	Stream<TransactionEntity> findByCategory(CategoryEntity categoryEntity);

	void deleteByAccount_UserIdAndTemporary(int userId, boolean temporary);

	int countByCategory(CategoryEntity categoryEntity);

	Page<TransactionEntity> findByAccountUserIdOrderByDatetimeDesc(int userId, Pageable pageable);

	Page<TransactionEntity> findByAccountUserIdAndCategoryNotNullOrderByDatetimeDesc(int userId, Pageable pageable);

	@Query("select sum(t.amount) from TransactionEntity t where t.account.userId = :userId and " +
			"t.category.categoryId in :categoryIds and t.datetime >= :startDate and t.datetime <= :endDate")
	BigDecimal getCategoriesTotal(@Param("userId") int userId, @Param("categoryIds") Collection<Integer> categoryIds,
								  @Param("startDate") Instant startDate, @Param("endDate") Instant endDate);

	@Query("select sum(t.amount) from TransactionEntity t " +
			"where t.account.userId = :userId and t.datetime >= :startDate and t.datetime <= :endDate and t.category is not null")
	BigDecimal getTotalAmount(@Param("userId") int userId, @Param("startDate") Instant startDate, @Param("endDate") Instant endDate);

	@Query("select t from TransactionEntity t where t.account.userId = :userId and " +
			"t.category.categoryId in :categoryIds and t.datetime >= :startDate and t.datetime <= :endDate")
	Stream<TransactionEntity> getTransactions(@Param("userId") int userId, @Param("categoryIds") Collection<Integer> categoryIds,
											  @Param("startDate") Instant startDate, @Param("endDate") Instant endDate);
}
