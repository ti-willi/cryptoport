package com.tiwilli.cryptoport.dto;

import com.tiwilli.cryptoport.entities.Portfolio;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class PortfolioDTO {

    private Long id;
    private String name;
    private Double amountInvested;
    private Double currentBalance;
    private Double profit;
    private Double profitPercentage;

    private Set<CryptoDTO> cryptos = new HashSet<>();

    public PortfolioDTO() {
    }

    public PortfolioDTO(Long id, String name, Double amountInvested, Double currentBalance, Double profit, Double profitPercentage) {
        this.id = id;
        this.name = name;
        this.amountInvested = amountInvested;
        this.currentBalance = currentBalance;
        this.profit = profit;
        this.profitPercentage = profitPercentage;
    }

    public PortfolioDTO(Portfolio entity) {
        id = entity.getId();
        name = entity.getName();
        amountInvested = entity.getAmountInvested();
        currentBalance = entity.getCurrentBalance();
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

    public Double getAmountInvested() {
        return amountInvested;
    }

    public Double getCurrentBalance() {
        return currentBalance;
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


