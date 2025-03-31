package com.tiwilli.cryptoport.entities;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "tb_portfolio")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double amountInvested;
    private Double currentBalance;
    private Double profit;
    private Double profitPercentage;

    @OneToMany(mappedBy = "portfolio")
    private Set<Crypto> cryptos = new HashSet<>();

    public Portfolio() {
    }

    public Portfolio(Long id, String name, Double amountInvested, Double currentBalance, Double profit, Double profitPercentage) {
        this.id = id;
        this.name = name;
        this.amountInvested = amountInvested;
        this.currentBalance = currentBalance;
        this.profit = profit;
        this.profitPercentage = profitPercentage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmountInvested() {
        return amountInvested;
    }

    public void setAmountInvested(Double amountInvested) {
        this.amountInvested = amountInvested;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
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
