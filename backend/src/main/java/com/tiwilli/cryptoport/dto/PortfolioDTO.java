package com.tiwilli.cryptoport.dto;

import com.tiwilli.cryptoport.entities.Portfolio;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class PortfolioDTO {

    private Long id;
    private String name;
    private Double balance;
    private Double currentPrice;
    private Double totalCoins;
    private Double funds;
    private Double averagePrice;
    private Double profit;
    private Double profitPercentage;

    private Set<CryptoDTO> cryptos = new HashSet<>();

    public PortfolioDTO() {
    }

    public PortfolioDTO(Long id, String name, Double balance, Double currentPrice, Double totalCoins, Double funds, Double averagePrice, Double profit, Double profitPercentage) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.currentPrice = currentPrice;
        this.totalCoins = totalCoins;
        this.funds = funds;
        this.averagePrice = averagePrice;
        this.profit = profit;
        this.profitPercentage = profitPercentage;
    }

    public PortfolioDTO(Portfolio entity) {
        id = entity.getId();
        name = entity.getName();
        balance = entity.getBalance();
        currentPrice = entity.getCurrentPrice();
        totalCoins = entity.getTotalCoins();
        funds = entity.getFunds();
        averagePrice = entity.getAveragePrice();
        profit = entity.getProfit();
        profitPercentage = entity.getProfitPercentage();
        this.cryptos = entity.getCryptos().stream()
                .map(CryptoDTO::new).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getBalance() {
        return balance;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public Double getTotalCoins() {
        return totalCoins;
    }

    public Double getFunds() {
        return funds;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    public Double getProfit() {
        return profit;
    }

    public Double getProfitPercentage() {
        return profitPercentage;
    }

    public Set<CryptoDTO> getCryptos() {
        return cryptos;
    }
}


