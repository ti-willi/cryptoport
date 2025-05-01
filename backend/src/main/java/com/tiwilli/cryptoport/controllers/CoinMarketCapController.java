package com.tiwilli.cryptoport.controllers;

import com.tiwilli.cryptoport.dto.CoinMarketCapDTO;
import com.tiwilli.cryptoport.dto.CoinMarketCapMinDTO;
import com.tiwilli.cryptoport.services.CoinMarketCapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/coinmarketcap")
public class CoinMarketCapController {

    @Autowired
    private CoinMarketCapService service;

    @GetMapping("/search")
    public ResponseEntity<List<CoinMarketCapMinDTO>> getData(String name) {
        return ResponseEntity.ok(service.searchCryptosByName(name));
    }

}
