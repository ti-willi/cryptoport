package com.tiwilli.cryptoport.dto;

import com.tiwilli.cryptoport.entities.Crypto;

import java.time.LocalDate;

public class CryptoMinDTO {

    private Long id;
    private String name;
    private Double currentValue;
    private LocalDate date;
    private Double depositOrWithdrawValue;
    private Double bankingFee;
    private Double quantity;
    

    public CryptoMinDTO() {
    }

    public CryptoMinDTO(Long id, String name, Double currentValue, LocalDate date, Double depositOrWithdrawValue, Double bankingFee, Double quantity) {
        this.id = id;
        this.name = name;
        this.currentValue = currentValue;
        this.date = date;
        this.depositOrWithdrawValue = depositOrWithdrawValue;
        this.bankingFee = bankingFee;
        this.quantity = quantity;
    }

    public CryptoMinDTO(Crypto entity) {
        id = entity.getId();
        name = entity.getName();
        currentValue = entity.getCurrentValue();
        date = entity.getDate();
        depositOrWithdrawValue = entity.getDepositOrWithdrawValue();
        bankingFee = entity.getBankingFee();
        quantity = entity.getQuantity();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getDepositOrWithdrawValue() {
        return depositOrWithdrawValue;
    }

    public Double getBankingFee() {
        return bankingFee;
    }

    public Double getQuantity() {
        return quantity;
    }
}
