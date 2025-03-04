package com.tiwilli.cryptoport.dto;

import com.tiwilli.cryptoport.entities.Crypto;

import java.time.LocalDate;

public class CryptoDTO {

    private Long id;
    private String name;
    private LocalDate date;
    private Double depositOrWithdrawValue;
    private Double currentValue;
    private Double quantity;
    private Double profit;
    private Double profitPercentage;
    private Double totalValue;
    private Double bankingFee;

    public CryptoDTO() {
    }

    public CryptoDTO(Long id, String name, LocalDate date, Double depositOrWithdrawValue, Double currentValue, Double quantity, Double profit, Double profitPercentage, Double totalValue, Double bankingFee) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.depositOrWithdrawValue = depositOrWithdrawValue;
        this.currentValue = currentValue;
        this.quantity = quantity;
        this.profit = profit;
        this.profitPercentage = profitPercentage;
        this.totalValue = totalValue;
        this.bankingFee = bankingFee;
    }

    public CryptoDTO(Crypto entity) {
        id = entity.getId();
        name = entity.getName();
        date = entity.getDate();
        depositOrWithdrawValue = entity.getDepositOrWithdrawValue();
        currentValue = entity.getCurrentValue();
        quantity = entity.getQuantity();
        profit = entity.getProfit();
        profitPercentage = entity.getProfitPercentage();
        totalValue = entity.getTotalValue();
        bankingFee = entity.getBankingFee();
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

    public Double getDepositOrWithdrawValue() {
        return depositOrWithdrawValue;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Double getProfit() {
        return profit;
    }

    public Double getProfitPercentage() {
        return profitPercentage;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public Double getBankingFee() {
        return bankingFee;
    }
}
