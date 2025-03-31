package com.tiwilli.cryptoport.dto;

import com.tiwilli.cryptoport.entities.Crypto;
import com.tiwilli.cryptoport.entities.Investment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CryptoDTO {

    private Long id;
    private String name;
    private Double amountInvested;
    private Double currentBalance;
    private Double quantity;
    private Double averagePrice;
    private Double profit;
    private Double profitPercentage;

    private Long portfolioId;

    private List<InvestmentDTO> investments = new ArrayList<>();

    public CryptoDTO() {
    }

    public CryptoDTO(Long id, String name, Double amountInvested, Double currentBalance, Double quantity, Double averagePrice, Double profit, Double profitPercentage, Long portfolioId) {
        this.id = id;
        this.name = name;
        this.amountInvested = amountInvested;
        this.currentBalance = currentBalance;
        this.quantity = quantity;
        this.averagePrice = averagePrice;
        this.profit = profit;
        this.profitPercentage = profitPercentage;
        this.portfolioId = portfolioId;
    }

    public CryptoDTO(Crypto entity) {
        id = entity.getId();
        name = entity.getName();
        amountInvested = entity.getAmountInvested();
        currentBalance = entity.getCurrentBalance();
        quantity = entity.getQuantity();
        averagePrice = entity.getAveragePrice();
        profit = entity.getProfit();
        profitPercentage = entity.getProfitPercentage();
        portfolioId = entity.getPortfolio().getId();
        this.investments = entity.getInvestments().stream()
                .map(InvestmentDTO::new).collect(Collectors.toList());
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

    public Double getQuantity() {
        return quantity;
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

    public Long getPortfolioId() {
        return portfolioId;
    }

    public List<InvestmentDTO> getInvestments() {
        return investments;
    }
}
