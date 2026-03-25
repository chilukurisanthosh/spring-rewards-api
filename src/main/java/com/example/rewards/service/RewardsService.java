
package com.example.rewards.service;

import com.example.rewards.jpa.CustomerEntity;
import com.example.rewards.jpa.CustomerJpaRepository;
import com.example.rewards.jpa.TransactionEntity;
import com.example.rewards.jpa.TransactionJpaRepository;
import com.example.rewards.model.MonthlyPoints;
import com.example.rewards.model.RewardSummary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RewardsService {
    private final TransactionJpaRepository txnRepo;
    private final CustomerJpaRepository customerRepo;

    public RewardsService(TransactionJpaRepository txnRepo, CustomerJpaRepository customerRepo) {
        this.txnRepo = txnRepo;
        this.customerRepo = customerRepo;
    }

    public CustomerEntity saveCustomer(CustomerEntity c) { return customerRepo.save(c); }
    public List<CustomerEntity> getCustomers() { return customerRepo.findAll(); }
    public TransactionEntity saveTransaction(TransactionEntity t) { return txnRepo.save(t); }

    public int calculatePoints(double amount) {
        int dollars = (int) Math.floor(amount);

        if (dollars <= 50) {
            return 0;
        }
        else if (dollars <= 100) {
            return dollars - 50;
        }
        else {
            int over100 = dollars - 100;
            return 50 + over100 * 2; // 50 points from $50-$100 + 2x points after 100
        }
    }

    public List<RewardSummary> getRewardSummaries(LocalDate start, LocalDate end) {
        List<TransactionEntity> txns = txnRepo.findByDateBetween(start, end);
        Map<Long, Map<YearMonth, Integer>> pointsByCustomerMonth = new HashMap<>();
        for (TransactionEntity t : txns) {
            YearMonth ym = YearMonth.from(t.getDate());
            int pts = calculatePoints(t.getAmount());
            pointsByCustomerMonth.computeIfAbsent(t.getCustomer().getId(), k -> new HashMap<>()).merge(ym, pts, Integer::sum);
        }
        Set<Long> custIds = pointsByCustomerMonth.keySet();
        Map<Long, String> names = customerRepo.findAllById(custIds).stream()
                .collect(Collectors.toMap(CustomerEntity::getId, CustomerEntity::getName));
        List<RewardSummary> result = new ArrayList<>();
        for (Map.Entry<Long, Map<YearMonth, Integer>> e : pointsByCustomerMonth.entrySet()) {
            Long id = e.getKey(); String name = names.getOrDefault(id, "Customer-" + id);
            List<YearMonth> months = e.getValue().keySet().stream().sorted().toList();
            List<MonthlyPoints> monthly = new ArrayList<>(); int total = 0;
            for (YearMonth ym : months) { int pts = e.getValue().get(ym); monthly.add(new MonthlyPoints(ym.toString(), pts)); total += pts; }
            result.add(new RewardSummary(id, name, monthly, total));
        }
        result.sort(Comparator.comparing(RewardSummary::getCustomerName));
        return result;
    }
    public RewardSummary getCustomerRewardSummary1(Long customerId, LocalDate start, LocalDate end) {
        var txns = txnRepo.findByCustomer_IdAndDateBetween(customerId, start, end);

        int total = 0;
        Map<YearMonth, Integer> monthMap = new TreeMap<>();
        for (var t : txns) {
            var ym = YearMonth.from(t.getDate());
            int pts = calculatePoints(t.getAmount());
            monthMap.merge(ym, pts, Integer::sum);
        }

        var monthly = new ArrayList<MonthlyPoints>();
        for (var e : monthMap.entrySet()) {
            monthly.add(new MonthlyPoints(e.getKey().toString(), e.getValue()));
            total += e.getValue();
        }

        String name = customerRepo.findById(customerId)
                .map(CustomerEntity::getName)
                .orElse("Customer-" + customerId);

        return new RewardSummary(customerId, name, monthly, total);
    }
    public RewardSummary getCustomerRewardSummary(Long customerId, LocalDate start, LocalDate end) {
        Objects.requireNonNull(customerId, "customerId must not be null");
        Objects.requireNonNull(start, "start must not be null");
        Objects.requireNonNull(end, "end must not be null");

        if (end.isBefore(start)) {
            throw new IllegalArgumentException("end date must be on or after start date");
        }

        // 1) Fetch transactions
        var txns = txnRepo.findByCustomer_IdAndDateBetween(customerId, start, end);

        // 2) Aggregate AMOUNTS per month (Option B)
        Map<YearMonth, Double> monthAmount = new TreeMap<>();
        for (var t : txns) {
            if (t == null || t.getDate() == null) continue;
            YearMonth ym = YearMonth.from(t.getDate());
            monthAmount.merge(ym, t.getAmount(), Double::sum);
        }

        // 3) Compute points once per month on the aggregated amount
        List<MonthlyPoints> monthly = new ArrayList<>(monthAmount.size());
        int totalPoints = 0;
        for (var e : monthAmount.entrySet()) {
            String month = e.getKey().toString(); // "YYYY-MM"
            double monthTotalAmount = e.getValue();
            int monthPoints = calculatePoints(monthTotalAmount);
            monthly.add(new MonthlyPoints(month, monthPoints));
            totalPoints += monthPoints;
        }

        // 4) Resolve customer name
        String name = customerRepo.findById(customerId)
                .map(CustomerEntity::getName)
                .orElse("Customer-" + customerId);

        return new RewardSummary(customerId, name, monthly, totalPoints);
    }
}
