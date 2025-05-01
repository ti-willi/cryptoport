package com.tiwilli.cryptoport.services;

import com.tiwilli.cryptoport.dto.CoinMarketCapDTO;
import com.tiwilli.cryptoport.dto.CryptoDTO;
import com.tiwilli.cryptoport.dto.PortfolioDTO;
import com.tiwilli.cryptoport.entities.Portfolio;
import com.tiwilli.cryptoport.entities.enums.TransactionType;
import com.tiwilli.cryptoport.repositories.CryptoRepository;
import com.tiwilli.cryptoport.repositories.PortfolioRepository;
import com.tiwilli.cryptoport.services.exceptions.DatabaseException;
import com.tiwilli.cryptoport.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepository repository;

    @Autowired
    private CryptoRepository cryptoRepository;

    @Autowired
    private CoinMarketCapService coinMarketCapService;

    @Transactional(readOnly = true)
    public PortfolioDTO findById(Long id, CryptoService cryptoService) {
        Portfolio portfolio = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Entity not found"));

        CoinMarketCapDTO coinMarketCapDTO = coinMarketCapService.getData();
        PortfolioDTO dto = new PortfolioDTO(portfolio);

        List<CryptoDTO> cryptoDTOS = portfolio.getCryptos().stream()
                .map(c -> cryptoService.toDto(c, coinMarketCapDTO))
                .toList();
        setDTO(dto, cryptoDTOS);

        return dto;
    }

    @Transactional(readOnly = true)
    public List<PortfolioDTO> findAll(CryptoService cryptoService) {
        List<Portfolio> result = repository.findAll();
        CoinMarketCapDTO coinMarketCapDTO = coinMarketCapService.getData();

        return result.stream().map(
                portfolio -> {
                    PortfolioDTO dto = new PortfolioDTO(portfolio);

                    List<CryptoDTO> cryptoDTOS = portfolio.getCryptos().stream()
                            .map(crypto -> cryptoService.toDto(crypto, coinMarketCapDTO))
                            .toList();

                    setDTO(dto, cryptoDTOS);

                    return dto;
                })
                .collect(Collectors.toList());
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

    private void setDTO(PortfolioDTO dto, List<CryptoDTO> cryptoDTOS) {
        dto.getCryptos().clear();
        dto.getCryptos().addAll(cryptoDTOS);
        dto.setCurrentBalance(calculateCurrentBalance(dto));
        dto.setProfit(calculateProfit(dto));
        dto.setProfitPercentage(calculateProfitPercentage(dto));
    }

    public void updatePortfolio(Portfolio portfolio) {
        double amountInvested = calculateAmountInvested(portfolio);
        portfolio.setAmountInvested(amountInvested);
        repository.save(portfolio);
    }

    private double calculateAmountInvested(Portfolio portfolio) {
        return portfolio.getCryptos().stream()
                .mapToDouble(c -> c.getType() == TransactionType.DEPOSIT
                ? c.getDepositOrWithdraw()
                        : -c.getDepositOrWithdraw())
                .sum();
    }

    private double calculateCurrentBalance(PortfolioDTO dto) {
        return dto.getCryptos().stream()
                .mapToDouble(CryptoDTO::getCurrentBalance)
                .sum();
    }

    private double calculateProfit(PortfolioDTO dto) {
        return dto.getCryptos().stream()
                .mapToDouble(CryptoDTO::getProfit)
                .sum();
    }

    private double calculateProfitPercentage(PortfolioDTO dto) {
        double amountInvested = dto.getAmountInvested();
        if (amountInvested == 0) {
            return 0.0;
        }
        return dto.getProfit() / dto.getAmountInvested() * 100;
    }

 }
