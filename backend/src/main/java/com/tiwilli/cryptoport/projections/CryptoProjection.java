package com.tiwilli.cryptoport.projections;

public interface CryptoProjection {

    Long getCryptoId();
    String getName();
    Double getCryptoValue();
    Double getQuantity();
    Double getAmountInvested();
    Double getCurrentBalance();
    Double getAveragePrice();
    Double getProfit();
    Double getProfitPercentage();
}
