package com.tiwilli.cryptoport.dto;

import com.tiwilli.cryptoport.entities.Portfolio;

public class PortfolioDTO {

    private Long id;
    private String cryptoName;
    private Double balance;
    private Double currentPrice;
    private Double totalCoins;
    private Double funds;
    private Double averagePrice;
    private Double profit;
    private Double profitPercentage;

    public PortfolioDTO() {
    }

    public PortfolioDTO(Long id, String cryptoName, Double balance, Double currentPrice, Double totalCoins, Double funds, Double averagePrice, Double profit, Double profitPercentage) {
        this.id = id;
        this.cryptoName = cryptoName;
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
        cryptoName = entity.getCryptoName();
        balance = entity.getBalance();
        currentPrice = entity.getCurrentPrice();
        totalCoins = entity.getTotalCoins();
        funds = entity.getFunds();
        averagePrice = entity.getAveragePrice();
        profit = entity.getProfit();
        profitPercentage = entity.getProfitPercentage();
    }

    public Long getId() {
        return id;
    }

    public String getCryptoName() {
        return cryptoName;
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
}


