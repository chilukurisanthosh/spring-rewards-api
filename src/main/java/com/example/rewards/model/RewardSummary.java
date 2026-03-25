
package com.example.rewards.model;

import java.util.List;

public class RewardSummary {
    private Long customerId;
    private String customerName;
    private List<MonthlyPoints> monthlyPoints;
    private int totalPoints;

    public RewardSummary() {}
    public RewardSummary(Long customerId, String customerName, List<MonthlyPoints> monthlyPoints, int totalPoints) {
        this.customerId = customerId; this.customerName = customerName; this.monthlyPoints = monthlyPoints; this.totalPoints = totalPoints;
    }

    public Long getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public List<MonthlyPoints> getMonthlyPoints() { return monthlyPoints; }
    public int getTotalPoints() { return totalPoints; }
}
