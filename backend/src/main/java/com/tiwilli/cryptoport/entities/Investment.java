package com.tiwilli.cryptoport.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate date;
    private Double depositOrWithdraw;
    private Double cryptoValue;
    private Double quantity;
    private Double profit;
    private Double profitPercentage;
    private Double brokerage;

    @ManyToOne
    @JoinColumn(name = "crypto_id")
    private Crypto crypto;

    public Investment() {
    }

    public Investment(Long id, String name, LocalDate date, Double depositOrWithdraw, Double cryptoValue, Double quantity, Double profit, Double profitPercentage, Double brokerage, Crypto crypto) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.depositOrWithdraw = depositOrWithdraw;
        this.cryptoValue = cryptoValue;
        this.quantity = quantity;
        this.profit = profit;
        this.profitPercentage = profitPercentage;
        this.brokerage = brokerage;
        this.crypto = crypto;
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

    public Double getBrokerage() {
        return brokerage;
    }

    public void setBrokerage(Double brokerage) {
        this.brokerage = brokerage;
    }

    public Crypto getCrypto() {
        return crypto;
    }

    public void setCrypto(Crypto crypto) {
        this.crypto = crypto;
    }
}
