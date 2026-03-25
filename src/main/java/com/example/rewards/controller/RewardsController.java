
package com.example.rewards.controller;

import com.example.rewards.model.RewardSummary;
import com.example.rewards.service.RewardsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rewards")
public class RewardsController {
    private final RewardsService rewardsService;
    public RewardsController(RewardsService rewardsService) { this.rewardsService = rewardsService; }

    @GetMapping
    public List<RewardSummary> getRewards(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return rewardsService.getRewardSummaries(start, end);
    }
    @GetMapping("/customer/{customerId}")
    public RewardSummary getCustomerRewards(
            @PathVariable Long customerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return rewardsService.getCustomerRewardSummary(customerId, start, end);
    }
}
