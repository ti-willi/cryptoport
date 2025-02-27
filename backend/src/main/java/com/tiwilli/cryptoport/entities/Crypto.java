package com.tiwilli.cryptoport.entities;

import java.time.LocalDate;
import java.util.Objects;

public class Crypto {

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

    public Crypto() {
    }

    public Crypto(Long id, String name, LocalDate date, Double depositOrWithdrawValue, Double currentValue, Double quantity, Double profit, Double profitPercentage, Double totalValue, Double bankingFee) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getDepositOrWithdrawValue() {
        return depositOrWithdrawValue;
    }

    public void setDepositOrWithdrawValue(Double depositOrWithdrawValue) {
        this.depositOrWithdrawValue = depositOrWithdrawValue;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
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

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public Double getBankingFee() {
        return bankingFee;
    }

    public void setBankingFee(Double bankingFee) {
        this.bankingFee = bankingFee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Crypto crypto = (Crypto) o;

        return Objects.equals(id, crypto.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
