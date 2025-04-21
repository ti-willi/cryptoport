package com.tiwilli.cryptoport.repositories;

import com.tiwilli.cryptoport.entities.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CryptoRepository extends JpaRepository<Crypto, Long> {

    List<Crypto> findByPortfolioId(Long portfolioId);
}
