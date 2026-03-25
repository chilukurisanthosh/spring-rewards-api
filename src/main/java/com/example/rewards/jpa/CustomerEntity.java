
package com.example.rewards.jpa;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class CustomerEntity {
    @Id
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    private String email;
    private String phone;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionEntity> transactions = new ArrayList<>();

    public CustomerEntity() {}

    public CustomerEntity(Long id, String name, String email, String phone) {
        this.id = id; this.name = name; this.email = email; this.phone = phone;
    }

    // helpers
    public void addTransaction(TransactionEntity t) {
        transactions.add(t);
        t.setCustomer(this);
    }
    public void removeTransaction(TransactionEntity t) {
        transactions.remove(t);
        t.setCustomer(null);
    }

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public List<TransactionEntity> getTransactions() { return transactions; }
}
