package com.tiwilli.cryptoport.services;

import com.tiwilli.cryptoport.dto.CryptoDTO;
import com.tiwilli.cryptoport.dto.PortfolioDTO;
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

    private double calculateProfit(Portfolio portfolio) {
        return portfolio.getCryptos().stream()
                .mapToDouble(Crypto::getProfit)
                .sum();
    }


 }
