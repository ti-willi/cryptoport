package com.tiwilli.cryptoport.dto;

import com.tiwilli.cryptoport.entities.Investment;

import java.time.LocalDate;

public class InvestmentDTO {

    private Long id;
    private String name;
    private LocalDate date;
    private Double depositOrWithdraw;
    private Double cryptoValue;
    private Double quantity;
    private Double profit;
    private Double profitPercentage;
    private Double brokerage;

    private Long cryptoId;

    public InvestmentDTO() {
    }

    public InvestmentDTO(Long id, String name, LocalDate date, Double depositOrWithdraw, Double cryptoValue, Double quantity, Double profit, Double profitPercentage, Double brokerage, Long cryptoId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.depositOrWithdraw = depositOrWithdraw;
        this.cryptoValue = cryptoValue;
        this.quantity = quantity;
        this.profit = profit;
        this.profitPercentage = profitPercentage;
        this.brokerage = brokerage;
        this.cryptoId = cryptoId;
    }

    public InvestmentDTO(Investment entity) {
        id = entity.getId();
        name = entity.getName();
        date = entity.getDate();
        depositOrWithdraw = entity.getDepositOrWithdraw();
        cryptoValue = entity.getCryptoValue();
        quantity = entity.getQuantity();
        profit = entity.getProfit();
        profitPercentage = entity.getProfitPercentage();
        brokerage = entity.getBrokerage();
        cryptoId = entity.getCrypto().getId();
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

    public Double getQuantity() {
        return quantity;
    }

    public Double getProfit() {
        return profit;
    }

    public Double getProfitPercentage() {
        return profitPercentage;
    }

    public Double getBrokerage() {
        return brokerage;
    }

    public Long getCryptoId() {
        return cryptoId;
    }
}
