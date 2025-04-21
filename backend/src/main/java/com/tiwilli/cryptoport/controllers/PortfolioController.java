package com.tiwilli.cryptoport.controllers;

import com.tiwilli.cryptoport.dto.CryptoDTO;
import com.tiwilli.cryptoport.dto.CryptoMinDTO;
import com.tiwilli.cryptoport.dto.PortfolioDTO;
import com.tiwilli.cryptoport.services.CryptoService;
import com.tiwilli.cryptoport.services.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/portfolios")
public class PortfolioController {

    @Autowired
    private PortfolioService service;

    @Autowired
    private CryptoService cryptoService;

    @GetMapping
    public ResponseEntity<Page<PortfolioDTO>> findAll(Pageable pageable) {
        Page<PortfolioDTO> list = service.findAll(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PortfolioDTO> findById(@PathVariable Long id) {
        PortfolioDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/{id}/cryptos")
    public ResponseEntity<List<CryptoMinDTO>> findByGropedCryptos(@PathVariable Long id) {
        List<CryptoMinDTO> list = cryptoService.getGroupedCryptoBalances(id);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<PortfolioDTO> insert(@RequestBody PortfolioDTO dto) {
        dto = service.insert(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PortfolioDTO> update(@PathVariable Long id, @RequestBody PortfolioDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
