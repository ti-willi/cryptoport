package com.tiwilli.cryptoport.services;

import com.tiwilli.cryptoport.dto.CryptoDTO;
import com.tiwilli.cryptoport.entities.Crypto;
import com.tiwilli.cryptoport.entities.Portfolio;
import com.tiwilli.cryptoport.repositories.CryptoRepository;
import com.tiwilli.cryptoport.repositories.PortfolioRepository;
import com.tiwilli.cryptoport.services.exceptions.DatabaseException;
import com.tiwilli.cryptoport.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class CryptoService {

    @Autowired
    private CryptoRepository repository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Transactional(readOnly = true)
    public CryptoDTO findById(Long id) {
        Crypto crypto = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Entity not found"));
        return new CryptoDTO(crypto);
    }

    @Transactional(readOnly = true)
    public Page<CryptoDTO> findAll(Pageable pageable) {
        Page<Crypto> result = repository.findAll(pageable);
        return result.map(CryptoDTO::new);
    }

    @Transactional
    public CryptoDTO insert(CryptoDTO dto) {
        Crypto entity = new Crypto();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        updatePortfolio(dto.getPortfolioId());
        return new CryptoDTO(entity);
    }

    @Transactional
    public CryptoDTO update(Long id, CryptoDTO dto) {
        try {
            Crypto entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new CryptoDTO(entity);
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
    private void copyDtoToEntity(CryptoDTO dto, Crypto entity) {
        entity.setName(dto.getName());
        entity.setAmountInvested(dto.getAmountInvested());
        entity.setCurrentBalance(dto.getCurrentBalance());
        entity.setQuantity(dto.getQuantity());
        entity.setAveragePrice(dto.getAveragePrice());
        entity.setProfit(dto.getProfit());
        entity.setProfitPercentage(dto.getProfitPercentage());

        Portfolio portfolio = portfolioRepository.getReferenceById(dto.getPortfolioId());
        entity.setPortfolio(portfolio);
    }

    @Transactional
    public void updatePortfolio(Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio não encontrado"));

        double amountInvested = calculateAmountInvested(portfolio);
        double profit = calculateProfit(portfolio);

        portfolio.setAmountInvested(amountInvested);
        portfolio.setProfit(profit);

        portfolioRepository.save(portfolio);
    }

    private double calculateAmountInvested(Portfolio portfolio) {
        return portfolio.getCryptos().stream()
                .mapToDouble(Crypto::getAmountInvested)
                .sum();
    }

    private double calculateProfit(Portfolio portfolio) {
        return portfolio.getCryptos().stream()
                .mapToDouble(Crypto::getProfit)
                .sum();
    }





}
