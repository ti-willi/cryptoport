package com.tiwilli.cryptoport.dto;

import com.tiwilli.cryptoport.entities.Crypto;
import com.tiwilli.cryptoport.projections.CryptoProjection;

public class CryptoMinDTO {

    private String name;
    private Double cryptoValue;
    private Double amountInvested;
    private Double currentBalance;
    private Double quantity;
    private Double averagePrice;
    private Double profit;
    private Double profitPercentage;

    public CryptoMinDTO() {
    }

    public CryptoMinDTO(String name, Double cryptoValue, Double amountInvested, Double currentBalance, Double quantity, Double averagePrice, Double profit, Double profitPercentage) {
        this.name = name;
        this.cryptoValue = cryptoValue;
        this.amountInvested = amountInvested;
        this.currentBalance = currentBalance;
        this.quantity = quantity;
        this.averagePrice = averagePrice;
        this.profit = profit;
        this.profitPercentage = profitPercentage;
    }

    public CryptoMinDTO(Crypto entity) {
        name = entity.getName();
        currentBalance = entity.getCurrentBalance();
        quantity = entity.getQuantity();
        profit = entity.getProfit();
        profitPercentage = entity.getProfitPercentage();
    }

    public CryptoMinDTO(CryptoProjection projection) {
        this.name = projection.getName();
        this.cryptoValue = projection.getCryptoValue();
        this.quantity = projection.getQuantity();
        this.amountInvested = projection.getAmountInvested();
        this.currentBalance = projection.getCurrentBalance();
        this.averagePrice = projection.getAveragePrice();
        this.profit = projection.getProfit();
        this.profitPercentage = projection.getProfitPercentage();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCryptoValue() {
        return cryptoValue;
    }

    public void setCryptoValue(Double cryptoValue) {
        this.cryptoValue = cryptoValue;
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

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
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

}
