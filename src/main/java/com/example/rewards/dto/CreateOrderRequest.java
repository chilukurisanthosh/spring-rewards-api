
package com.example.rewards.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class CreateOrderRequest {
    @NotNull(message = "id is required")
    private Long id;
    @NotNull(message = "customerId is required")
    private Long customerId;
    @NotNull(message = "date is required")
    private LocalDate date;
    @DecimalMin(value = "0.01", message = "amount must be greater than 0")
    private Double amount;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
}
