
package com.example.rewards.config;

import com.example.rewards.jpa.CustomerEntity;
import com.example.rewards.jpa.CustomerJpaRepository;
import com.example.rewards.jpa.TransactionEntity;
import com.example.rewards.jpa.TransactionJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.List;

@Configuration
@Profile("!test")
public class DataLoader {
    @Bean
    CommandLineRunner seedData(CustomerJpaRepository customers, TransactionJpaRepository txns) {
        return args -> {
            CustomerEntity alice = new CustomerEntity(1L, "Alice", "alice@example.com", "+91-9000000001");
            CustomerEntity bob   = new CustomerEntity(2L, "Bob",   "bob@example.com",   "+91-9000000002");
            customers.saveAll(List.of(alice, bob));

            txns.saveAll(List.of(
                new TransactionEntity(1L, alice, LocalDate.of(2025, 1, 5), 100.00),
                new TransactionEntity(2L, alice, LocalDate.of(2025, 1, 12), 20.00),
                new TransactionEntity(3L, alice, LocalDate.of(2025, 2, 3), 200.00),
                new TransactionEntity(4L, bob,   LocalDate.of(2025, 1, 18), 150.00)
            ));
        };
    }
}
