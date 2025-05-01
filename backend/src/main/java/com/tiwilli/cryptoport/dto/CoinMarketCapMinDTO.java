package com.tiwilli.cryptoport.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tiwilli.cryptoport.util.TwoDecimalSerializer;

public record CoinMarketCapMinDTO(
        Long id,
        String name,
        String symbol,

        @JsonSerialize(using = TwoDecimalSerializer.class)
        Double price
) {}
