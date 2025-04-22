package com.tiwilli.cryptoport.services;

import com.tiwilli.cryptoport.dto.CryptoDTO;
import com.tiwilli.cryptoport.dto.PortfolioDTO;
import com.tiwilli.cryptoport.entities.Crypto;
import com.tiwilli.cryptoport.entities.Portfolio;
import com.tiwilli.cryptoport.entities.enums.TransactionType;
import com.tiwilli.cryptoport.repositories.CryptoRepository;
import com.tiwilli.cryptoport.repositories.PortfolioRepository;
import com.tiwilli.cryptoport.services.exceptions.DatabaseException;
import com.tiwilli.cryptoport.services.exceptions.ResourceNotFoundException;
import com.tiwilli.cryptoport.util.Utils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepository repository;

    @Autowired
    private CryptoRepository cryptoRepository;

    @Transactional(readOnly = true)
    public PortfolioDTO findById(Long id) {
        Portfolio portfolio = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Entity not found"));
        return new PortfolioDTO(portfolio);
    }

    @Transactional(readOnly = true)
    public Page<PortfolioDTO> findAll(Pageable pageable) {
        Page<Portfolio> result = repository.findAll(pageable);
        return result.map(PortfolioDTO::new);
    }

    @Transactional
    public PortfolioDTO insert(PortfolioDTO dto) {
        Portfolio entity = new Portfolio();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new PortfolioDTO(entity);
    }

    @Transactional
    public PortfolioDTO update(Long id, PortfolioDTO dto) {
        try {
            Portfolio entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new PortfolioDTO(entity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Entity not found");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    @Transactional
    private void copyDtoToEntity(PortfolioDTO dto, Portfolio entity) {
        entity.setName(dto.getName());
    }

    public void updatePortfolio(Portfolio portfolio) {
        double amountInvested = calculateAmountInvested(portfolio);
        double currentBalance = calculateCurrentBalance(portfolio);
        double profit = calculateProfit(portfolio);
        double profitPercentage = Utils.decimalFormat(calculateProfitPercentage(portfolio));

        portfolio.setAmountInvested(amountInvested);
        portfolio.setCurrentBalance(currentBalance);
        portfolio.setProfit(profit);
        portfolio.setProfitPercentage(profitPercentage);

        repository.save(portfolio);
    }

    private double calculateAmountInvested(Portfolio portfolio) {
        return portfolio.getCryptos().stream()
                .mapToDouble(c -> c.getType() == TransactionType.DEPOSIT
                ? c.getDepositOrWithdraw()
                        : -c.getDepositOrWithdraw())
                .sum();
    }

    private double calculateCurrentBalance(Portfolio portfolio) {
        return portfolio.getCryptos().stream()
                .mapToDouble(Crypto::getCurrentBalance)
                .sum();
    }

    private double calculateProfit(Portfolio portfolio) {
        return portfolio.getCryptos().stream()
                .mapToDouble(Crypto::getProfit)
                .sum();
    }

    private double calculateProfitPercentage(Portfolio portfolio) {
        double amountInvested = Optional.ofNullable(portfolio.getAmountInvested())
                .orElse(0.0);
        if (amountInvested == 0) {
            return 0.0;
        }
        return portfolio.getProfit() / portfolio.getAmountInvested() * 100;
    }

 }
