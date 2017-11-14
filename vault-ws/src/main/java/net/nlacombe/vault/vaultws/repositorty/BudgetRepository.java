package net.nlacombe.vault.vaultws.repositorty;

import net.nlacombe.vault.vaultws.entity.BudgetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface BudgetRepository extends JpaRepository<BudgetEntity, Integer>
{
	@Query("select b from BudgetEntity b where b.userId = :userId " +
			"and b.category is not null and b.income = :income and " +
			"((b.startDate >= :startDate and b.startDate <= :endDate) or " +
			"(b.endDate >= :startDate and b.endDate <= :endDate) or " +
			"(b.startDate <= :startDate and b.endDate >= :endDate))")
	Stream<BudgetEntity> findByRangeAndHasCategory(@Param("userId") int userId,
												   @Param("startDate") Instant startDate,
												   @Param("endDate") Instant endDate,
												   @Param("income") boolean income);

	@Query("select b from BudgetEntity b where b.userId = :userId " +
			"and b.category is null and b.income = false and " +
			"((b.startDate >= :startDate and b.startDate <= :endDate) or " +
			"(b.endDate >= :startDate and b.endDate <= :endDate) or " +
			"(b.startDate <= :startDate and b.endDate >= :endDate))")
	BudgetEntity findSpendingByRangeAndDoesNotHaveCategory(@Param("userId") int userId,
														   @Param("startDate") Instant startDate,
														   @Param("endDate") Instant endDate);

	@Query("select CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
			"from BudgetEntity b where b.userId = :userId and " +
			"((b.startDate >= :startDate and b.startDate <= :endDate) or " +
			"(b.endDate >= :startDate and b.endDate <= :endDate) or " +
			"(b.startDate <= :startDate and b.endDate >= :endDate)) " +
			"and b.category.categoryId = :categoryId")
	boolean existsByRangeAndCategoryId(@Param("userId") int userId,
									   @Param("startDate") Instant startDate,
									   @Param("endDate") Instant endDate,
									   @Param("categoryId") int categoryId);

	@Query("select sum(b.plannedMaxAmount) from BudgetEntity b where b.userId = :userId and " +
			"b.income = false and " +
			"((b.startDate >= :startDate and b.startDate <= :endDate) or " +
			"(b.endDate >= :startDate and b.endDate <= :endDate) or " +
			"(b.startDate <= :startDate and b.endDate >= :endDate))")
	BigDecimal getTotalPlannedMaxAmount(@Param("userId") int userId,
										@Param("startDate") Instant startDate,
										@Param("endDate") Instant endDate);

	Optional<BudgetEntity> findByUserIdAndBudgetId(int userId, int budgetId);
}
