package com.tiwilli.cryptoport.repositories;

import com.tiwilli.cryptoport.entities.Crypto;
import com.tiwilli.cryptoport.entities.Investment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {
}
