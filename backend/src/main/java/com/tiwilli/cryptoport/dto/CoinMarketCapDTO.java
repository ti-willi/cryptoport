package com.tiwilli.cryptoport.dto;

import java.util.List;

public record CoinMarketCapDTO(
        List<CryptoData> data
) {
    public record CryptoData(
            Long id,
            String name,
            String symbol,
            Quote quote
    ) {}

    public record Quote(
            Usd USD
    ) {}

    public record Usd(
            Double price
    ) {}

}
