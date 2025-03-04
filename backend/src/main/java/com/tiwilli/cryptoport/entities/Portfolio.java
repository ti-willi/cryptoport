package com.tiwilli.cryptoport.entities;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "tb_portfolio")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cryptoName;
    private Double balance;
    private Double currentPrice;
    private Double totalCoins;
    private Double funds;
    private Double averagePrice;
    private Double profit;
    private Double profitPercentage;

    @OneToMany(mappedBy = "portfolio")
    private Set<Crypto> cryptos = new HashSet<>();

    public Portfolio() {
    }

    public Portfolio(Long id, String cryptoName, Double balance, Double currentPrice, Double totalCoins, Double funds, Double averagePrice, Double profit, Double profitPercentage) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCryptoName() {
        return cryptoName;
    }

    public void setCryptoName(String cryptoName) {
        this.cryptoName = cryptoName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Double getTotalCoins() {
        return totalCoins;
    }

    public void setTotalCoins(Double totalCoins) {
        this.totalCoins = totalCoins;
    }

    public Double getFunds() {
        return funds;
    }

    public void setFunds(Double funds) {
        this.funds = funds;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Double getProfitPercentage() {
        return profitPercentage;
    }

    public void setProfitPercentage(Double profitPercentage) {
        this.profitPercentage = profitPercentage;
    }

    public Set<Crypto> getCryptos() {
        return cryptos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Portfolio portfolio = (Portfolio) o;

        return Objects.equals(id, portfolio.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
