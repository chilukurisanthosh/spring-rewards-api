
package com.example.rewards.controller;

import com.example.rewards.dto.CreateCustomerRequest;
import com.example.rewards.jpa.CustomerEntity;
import com.example.rewards.model.RewardSummary;
import com.example.rewards.service.RewardsService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final RewardsService rewardsService;
    public CustomerController(RewardsService rewardsService) { this.rewardsService = rewardsService; }

    @PostMapping
    public CustomerEntity createCustomer(@Valid @RequestBody CreateCustomerRequest req) {
        CustomerEntity c = new CustomerEntity(req.getId(), req.getName(), req.getEmail(), req.getPhone());
        return rewardsService.saveCustomer(c);
    }

    @GetMapping
    public List<CustomerEntity> getAllCustomers() { return rewardsService.getCustomers(); }

}
