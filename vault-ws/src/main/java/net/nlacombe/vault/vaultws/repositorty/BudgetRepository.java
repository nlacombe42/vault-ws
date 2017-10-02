package net.nlacombe.vault.vaultws.repositorty;

import net.nlacombe.vault.vaultws.entity.BudgetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.stream.Stream;

@Repository
public interface BudgetRepository extends JpaRepository<BudgetEntity, Integer>
{
	@Query("select b from BudgetEntity b where b.userId = :userId and " +
			"((b.startDate >= :startDate and b.startDate <= :endDate) or " +
			"(b.endDate >= :startDate and b.endDate <= :endDate) or " +
			"(b.startDate <= :startDate and b.endDate >= :endDate))")
	Stream<BudgetEntity> findByRange(@Param("userId") int userId,
									 @Param("startDate") Instant startDate,
									 @Param("endDate") Instant endDate);
}
