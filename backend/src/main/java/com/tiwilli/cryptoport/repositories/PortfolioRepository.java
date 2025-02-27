package com.tiwilli.cryptoport.repositories;

import com.tiwilli.cryptoport.entities.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}
