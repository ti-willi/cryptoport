package com.tiwilli.cryptoport.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tiwilli.cryptoport.entities.Portfolio;
import com.tiwilli.cryptoport.util.TwoDecimalSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PortfolioDTO {

    private Long id;
    private String name;

    @JsonSerialize(using = TwoDecimalSerializer.class)
    private Double amountInvested;

    @JsonSerialize(using = TwoDecimalSerializer.class)
    private Double currentBalance;

    @JsonSerialize(using = TwoDecimalSerializer.class)
    private Double profit;

    @JsonSerialize(using = TwoDecimalSerializer.class)
    private Double profitPercentage;

    private List<CryptoDTO> cryptos = new ArrayList<>();

    public PortfolioDTO() {
    }

    public PortfolioDTO(Long id, String name, Double amountInvested, Double currentBalance, Double profit, Double profitPercentage) {
        this.id = id;
        this.name = name;
        this.amountInvested = amountInvested;
        this.currentBalance = currentBalance;
        this.profit = profit;
        this.profitPercentage = profitPercentage;
    }

    public PortfolioDTO(Portfolio entity) {
        id = entity.getId();
        name = entity.getName();
        amountInvested = entity.getAmountInvested();
        this.cryptos = entity.getCryptos().stream()
                .map(CryptoDTO::new).collect(Collectors.toList());
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

    public List<CryptoDTO> getCryptos() {
        return cryptos;
    }
}


