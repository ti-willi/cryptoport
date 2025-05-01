package com.tiwilli.cryptoport.controllers;

import com.tiwilli.cryptoport.dto.CoinMarketCapDTO;
import com.tiwilli.cryptoport.services.CoinMarketCapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coinmarketcap")
public class CoinMarketCapController {

    @Autowired
    private CoinMarketCapService service;

    @GetMapping
    public ResponseEntity<CoinMarketCapDTO> getData() {
        return ResponseEntity.ok(service.getData());
    }
}
