
package com.example.rewards.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findByCustomer_Id(Long customerId);
    List<TransactionEntity> findByDateBetween(LocalDate start, LocalDate end);
    List<TransactionEntity> findByCustomer_IdAndDateBetween(Long customerId,
                                                            LocalDate start,
                                                            LocalDate end);
}
