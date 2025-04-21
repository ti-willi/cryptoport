package com.tiwilli.cryptoport.projections;

public interface CryptoProjection {

    String getName();
    Double getCryptoValue();
    Double getQuantity();
    Double getAmountInvested();
    Double getCurrentBalance();
    Double getAveragePrice();
    Double getProfit();
    Double getProfitPercentage();
}
