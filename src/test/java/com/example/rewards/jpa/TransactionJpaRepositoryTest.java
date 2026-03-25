
package com.example.rewards.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TransactionJpaRepositoryTest {

    @Autowired private TransactionJpaRepository txnRepo;
    @Autowired private CustomerJpaRepository custRepo;

    @Test
    void savesAndFindsByCustomerId_WithManyToOne() {
        CustomerEntity c = new CustomerEntity(99L, "Dev", "dev@example.com", null);
        custRepo.save(c);
        txnRepo.save(new TransactionEntity(1001L, c, LocalDate.of(2025, 2, 10), 120.0));

        assertThat(txnRepo.findByCustomer_Id(99L))
            .extracting(TransactionEntity::getId)
            .contains(1001L);
    }
}
