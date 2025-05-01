package com.tiwilli.cryptoport.services;

import com.tiwilli.cryptoport.dto.CoinMarketCapDTO;
import com.tiwilli.cryptoport.util.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

@Service
public class CoinMarketCapService {

    @Value("${coinmarketcap.api.key}")
    private String apiKey;

    public CoinMarketCapDTO getData() {
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        HttpHeaders headers = Utils.BuildHeaders(
                "X-CMC_PRO_API_KEY", apiKey, MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<CoinMarketCapDTO> response = Utils.makeApiCall(
                url, entity, CoinMarketCapDTO.class
        );

        return response.getBody();
    }

    public double getQuote(CoinMarketCapDTO dto, Long cryptoId) {
        for (CoinMarketCapDTO.CryptoData cryptoData : dto.data()) {
            if (cryptoData.id().equals(cryptoId)) {
                return cryptoData.quote().USD().price();
            }
        }
        throw new IllegalArgumentException("Crypto with id " + cryptoId + " not found in data");
    }

}
