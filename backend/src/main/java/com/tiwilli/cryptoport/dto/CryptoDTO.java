package com.tiwilli.cryptoport.dto;

import com.tiwilli.cryptoport.entities.Crypto;
import com.tiwilli.cryptoport.entities.enums.TransactionType;

import java.time.LocalDate;

public class CryptoDTO {

    private Long id;
    private String name;
    private LocalDate date;
    private Double depositOrWithdraw;
    private Double cryptoValue;
    private Double currentBalance;
    private Double quantity;
    private Double brokerageFee;
    private TransactionType type;
    private Double profit;
    private Double profitPercentage;
    private Long portfolioId;

    public CryptoDTO() {
    }

    public CryptoDTO(Long id, String name, LocalDate date, Double depositOrWithdraw, Double cryptoValue, Double currentBalance, Double quantity, Double brokerageFee, TransactionType type, Double profit, Double profitPercentage, Long portfolioId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.depositOrWithdraw = depositOrWithdraw;
        this.cryptoValue = cryptoValue;
        this.currentBalance = currentBalance;
        this.quantity = quantity;
        this.brokerageFee = brokerageFee;
        this.type = type;
        this.profit = profit;
        this.profitPercentage = profitPercentage;
        this.portfolioId = portfolioId;
    }

    public CryptoDTO(Crypto entity) {
        id = entity.getId();
        name = entity.getName();
        date = entity.getDate();
        depositOrWithdraw = entity.getDepositOrWithdraw();
        cryptoValue = entity.getCryptoValue();
        currentBalance = entity.getCurrentBalance();
        quantity = entity.getQuantity();
        brokerageFee = entity.getBrokerageFee();
        type = entity.getType();
        profit = entity.getProfit();
        profitPercentage = entity.getProfitPercentage();
        portfolioId = entity.getPortfolio().getId();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getDepositOrWithdraw() {
        return depositOrWithdraw;
    }

    public Double getCryptoValue() {
        return cryptoValue;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Double getBrokerageFee() {
        return brokerageFee;
    }

    public TransactionType getType() {
        return type;
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
}
