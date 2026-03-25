
package com.example.rewards.controller;

import com.example.rewards.dto.CreateOrderRequest;
import com.example.rewards.jpa.CustomerEntity;
import com.example.rewards.jpa.CustomerJpaRepository;
import com.example.rewards.jpa.TransactionEntity;
import com.example.rewards.jpa.TransactionJpaRepository;
import com.example.rewards.service.RewardsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final RewardsService rewardsService;
    private final CustomerJpaRepository customerRepo;
    private final TransactionJpaRepository txnRepo;

    public OrderController(RewardsService rewardsService, CustomerJpaRepository customerRepo, TransactionJpaRepository txnRepo) {
        this.rewardsService = rewardsService; this.customerRepo = customerRepo; this.txnRepo = txnRepo;
    }

    @PostMapping
    public TransactionEntity createOrder(@Valid @RequestBody CreateOrderRequest req) {
        CustomerEntity customer = customerRepo.findById(req.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        TransactionEntity t = new TransactionEntity(req.getId(), customer, req.getDate(), req.getAmount());
        return rewardsService.saveTransaction(t);
    }

    @GetMapping("/customer/{customerId}")
    public List<TransactionEntity> getOrdersForCustomer(@PathVariable Long customerId) {
        return txnRepo.findByCustomer_Id(customerId);
    }
}
