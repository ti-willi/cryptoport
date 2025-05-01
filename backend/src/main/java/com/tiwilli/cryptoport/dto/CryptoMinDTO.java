package com.tiwilli.cryptoport.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tiwilli.cryptoport.util.TwoDecimalSerializer;

public class CryptoMinDTO {

    private Long cryptoId;
    private String name;
    private String symbol;
    private String logoUrl;
    private Double quantity;

    @JsonSerialize(using = TwoDecimalSerializer.class)
    private Double amountInvested;

    @JsonSerialize(using = TwoDecimalSerializer.class)
    private Double currentBalance;

    @JsonSerialize(using = TwoDecimalSerializer.class)
    private Double averagePrice;

    @JsonSerialize(using = TwoDecimalSerializer.class)
    private Double quote;

    @JsonSerialize(using = TwoDecimalSerializer.class)
    private Double profit;

    @JsonSerialize(using = TwoDecimalSerializer.class)
    private Double profitPercentage;

    public CryptoMinDTO() {
    }

    public CryptoMinDTO(Long cryptoId, String name, String symbol, String logoUrl, Double quantity, Double amountInvested, Double currentBalance, Double averagePrice, Double quote, Double profit, Double profitPercentage) {
        this.cryptoId = cryptoId;
        this.name = name;
        this.symbol = symbol;
        this.logoUrl = logoUrl;
        this.quantity = quantity;
        this.amountInvested = amountInvested;
        this.currentBalance = currentBalance;
        this.averagePrice = averagePrice;
        this.quote = quote;
        this.profit = profit;
        this.profitPercentage = profitPercentage;
    }

    public Long getCryptoId() {
        return cryptoId;
    }

    public void setCryptoId(Long cryptoId) {
        this.cryptoId = cryptoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
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

    public Double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public Double getQuote() {
        return quote;
    }

    public void setQuote(Double quote) {
        this.quote = quote;
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
