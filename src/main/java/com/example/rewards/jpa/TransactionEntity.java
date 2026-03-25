
package com.example.rewards.jpa;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @Column(name = "txn_date", nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private double amount;

    public TransactionEntity() {}

    public TransactionEntity(Long id, CustomerEntity customer, LocalDate date, double amount) {
        this.id = id; this.customer = customer; this.date = date; this.amount = amount;
    }

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public CustomerEntity getCustomer() { return customer; }
    public void setCustomer(CustomerEntity customer) { this.customer = customer; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
