package com.tiwilli.cryptoport.repositories;

import com.tiwilli.cryptoport.entities.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoRepository extends JpaRepository<Crypto, Long> {
}
