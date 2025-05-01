package com.tiwilli.cryptoport.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tiwilli.cryptoport.entities.Crypto;
import com.tiwilli.cryptoport.entities.enums.TransactionType;
import com.tiwilli.cryptoport.util.TwoDecimalSerializer;

import java.time.LocalDate;

public class CryptoDTO {

    private Long id;
    private Long cryptoId;
    private String name;
    private String symbol;
    private String logoUrl;
    private LocalDate date;
    private Double depositOrWithdraw;

    @JsonSerialize(using = TwoDecimalSerializer.class)
    private Double cryptoValue;
    private Double quantity;
    private Double brokerageFee;

    @JsonSerialize(using = TwoDecimalSerializer.class)
    private Double currentBalance;
    private TransactionType type;

    @JsonSerialize(using = TwoDecimalSerializer.class)
    private Double quote;

    @JsonSerialize(using = TwoDecimalSerializer.class)
    private Double profit;

    @JsonSerialize(using = TwoDecimalSerializer.class)
    private Double profitPercentage;
    private Long portfolioId;

    public CryptoDTO() {
    }

    public CryptoDTO(Long id, Long cryptoId, String name, String symbol, String logoUrl, LocalDate date, Double depositOrWithdraw, Double cryptoValue, Double quantity, Double brokerageFee, Double currentBalance, TransactionType type, Double quote, Double profit, Double profitPercentage, Long portfolioId) {
        this.id = id;
        this.cryptoId = cryptoId;
        this.name = name;
        this.symbol = symbol;
        this.logoUrl = logoUrl;
        this.date = date;
        this.depositOrWithdraw = depositOrWithdraw;
        this.cryptoValue = cryptoValue;
        this.quantity = quantity;
        this.brokerageFee = brokerageFee;
        this.currentBalance = currentBalance;
        this.type = type;
        this.quote = quote;
        this.profit = profit;
        this.profitPercentage = profitPercentage;
        this.portfolioId = portfolioId;
    }

    public CryptoDTO(Crypto entity) {
        id = entity.getId();
        cryptoId = entity.getCryptoId();
        name = entity.getName();
        symbol = entity.getSymbol();
        logoUrl = entity.getLogoUrl();
        date = entity.getDate();
        depositOrWithdraw = entity.getDepositOrWithdraw();
        cryptoValue = entity.getCryptoValue();
        quantity = entity.getQuantity();
        brokerageFee = entity.getBrokerageFee();
        type = entity.getType();
        portfolioId = entity.getPortfolio().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getDepositOrWithdraw() {
        return depositOrWithdraw;
    }

    public void setDepositOrWithdraw(Double depositOrWithdraw) {
        this.depositOrWithdraw = depositOrWithdraw;
    }

    public Double getCryptoValue() {
        return cryptoValue;
    }

    public void setCryptoValue(Double cryptoValue) {
        this.cryptoValue = cryptoValue;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getBrokerageFee() {
        return brokerageFee;
    }

    public void setBrokerageFee(Double brokerageFee) {
        this.brokerageFee = brokerageFee;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
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

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Long portfolioId) {
        this.portfolioId = portfolioId;
    }
}

