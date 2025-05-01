package com.tiwilli.cryptoport.services;

import com.tiwilli.cryptoport.dto.CoinMarketCapDTO;
import com.tiwilli.cryptoport.dto.CryptoDTO;
import com.tiwilli.cryptoport.dto.CryptoMinDTO;
import com.tiwilli.cryptoport.entities.Crypto;
import com.tiwilli.cryptoport.entities.Portfolio;
import com.tiwilli.cryptoport.entities.enums.TransactionType;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CryptoService {

    @Autowired
    private CryptoRepository repository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private CoinMarketCapService coinMarketCapService;


    @Transactional(readOnly = true)
    public CryptoDTO findById(Long id) {
        Crypto crypto = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Entity not found"));
        CoinMarketCapDTO coinMarketCapDTO = coinMarketCapService.getData();
        return toDto(crypto, coinMarketCapDTO);
    }

    @Transactional(readOnly = true)
    public Page<CryptoDTO> findAll(Pageable pageable) {
        Page<Crypto> result = repository.findAll(pageable);
        CoinMarketCapDTO coinMarketCapDTO = coinMarketCapService.getData();
        return result.map(c -> toDto(c, coinMarketCapDTO));
    }

    @Transactional
    public CryptoDTO insert(CryptoDTO dto) {
        Crypto entity = new Crypto();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        portfolioService.updatePortfolio(entity.getPortfolio());
        return new CryptoDTO(entity);
    }

    @Transactional
    public CryptoDTO update(Long id, CryptoDTO dto) {
        try {
            Crypto entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            portfolioService.updatePortfolio(entity.getPortfolio());
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
        entity.setCryptoId(dto.getCryptoId());
        entity.setName(dto.getName());
        entity.setSymbol(dto.getSymbol());
        entity.setDate(dto.getDate());
        entity.setDepositOrWithdraw(dto.getDepositOrWithdraw());
        entity.setCryptoValue(dto.getCryptoValue());
        entity.setQuantity(calculateQuantity(dto));
        entity.setType(dto.getType());
        entity.setBrokerageFee(dto.getBrokerageFee());

        Portfolio portfolio = portfolioRepository.getReferenceById(dto.getPortfolioId());
        entity.setPortfolio(portfolio);
    }

    public CryptoDTO toDto(Crypto entity, CoinMarketCapDTO  coinMarketCapDTO) {
        CryptoDTO dto = new CryptoDTO(entity);

        double quote = coinMarketCapService.getQuote(coinMarketCapDTO, entity.getCryptoId());
        double currentBalance = calculateCurrentBalance(dto, quote);
        double profit = calculateProfit(dto, quote);
        double profitPercentage = calculateProfitPercentage(dto, quote);

        dto.setQuote(quote);
        dto.setCurrentBalance(currentBalance);
        dto.setProfit(profit);
        dto.setProfitPercentage(profitPercentage);

        return dto;
    }

    private CryptoMinDTO toDto(List<Crypto> cryptos, Long cryptoId, CoinMarketCapDTO coinMarketCapDTO) {
        String name = String.valueOf(cryptos.getFirst().getName());
        String symbol = String.valueOf(cryptos.getFirst().getSymbol());
        String logoUrl = String.valueOf(cryptos.getFirst().getLogoUrl());
        double quote = coinMarketCapService.getQuote(coinMarketCapDTO, cryptoId);
        double amountInvested = calculateAmountInvested(cryptos);
        double quantity = calculateQuantity(cryptos);
        double averagePrice = calculateAveragePrice(cryptos);
        double currentBalance = calculateCurrentBalance(cryptos, coinMarketCapDTO);
        double profit = calculateProfit(cryptos, amountInvested, coinMarketCapDTO);
        double profitPercentage = calculateProfitPercentage(cryptos, amountInvested, coinMarketCapDTO);

        CryptoMinDTO dto = new CryptoMinDTO();
        dto.setCryptoId(cryptoId);
        dto.setName(name);
        dto.setSymbol(symbol);
        dto.setLogoUrl(logoUrl);
        dto.setQuote(quote);
        dto.setAmountInvested(amountInvested);
        dto.setQuantity(quantity);
        dto.setAveragePrice(averagePrice);
        dto.setCurrentBalance(currentBalance);
        dto.setProfit(profit);
        dto.setProfitPercentage(profitPercentage);

        return dto;
    }

    @Transactional(readOnly = true)
    public List<CryptoMinDTO> getGroupedCryptoBalances(Long portfolioId, CoinMarketCapDTO coinMarketCapDTO) {
        List<Crypto> allCryptos = repository.findByPortfolioId(portfolioId);

        Map<Long, List<Crypto>> grouped = allCryptos.stream()
                .collect(Collectors.groupingBy(Crypto::getCryptoId));

        List<CryptoMinDTO> result = new ArrayList<>();

        for (Map.Entry<Long, List<Crypto>> entry : grouped.entrySet()) {
            Long cryptoId = entry.getKey();
            List<Crypto> cryptos = entry.getValue();

            CryptoMinDTO dto = toDto(cryptos, cryptoId, coinMarketCapDTO);
            result.add(dto);
        }

        return result;
    }

    private double calculateAmountInvested(List<Crypto> cryptos) {
        return cryptos.stream()
                .mapToDouble(c -> c.getType() == TransactionType.DEPOSIT
                            ?c.getDepositOrWithdraw() : -c.getDepositOrWithdraw())
                        .sum();
    }

    private double calculateNetAmount(CryptoDTO dto) {
        return dto.getDepositOrWithdraw() - (dto.getBrokerageFee() != null ? dto.getBrokerageFee() : 0.0);
    }

    private double calculateQuantity(CryptoDTO dto) {
        double netAmount = calculateNetAmount(dto);
        double quantity = netAmount / dto.getCryptoValue();
        return dto.getType() == TransactionType.DEPOSIT ? quantity : -quantity;
    }

    private double calculateQuantity(List<Crypto> cryptos) {
        return cryptos.stream()
                .mapToDouble(c -> {
                    double netAmount = c.getDepositOrWithdraw() - (c.getBrokerageFee() != null ? c.getBrokerageFee() : 0.0);
                    double quantity = netAmount / c.getCryptoValue();
                    return c.getType() == TransactionType.DEPOSIT ? quantity : -quantity;
                })
                .sum();
    }

    private double calculateCurrentBalance(CryptoDTO dto, double quote) {
        double quantity = calculateQuantity(dto);
        return quantity * quote;
    }

    private double calculateCurrentBalance(List<Crypto> cryptos, CoinMarketCapDTO coinMarketCapDTO) {
        return cryptos.stream()
                .mapToDouble(c -> {
                    double quote = coinMarketCapService.getQuote(coinMarketCapDTO, c.getCryptoId());
                    double quantity = calculateQuantity(List.of(c));
                    return quantity * quote;
                })
                .sum();
    }

    private double calculateProfit(CryptoDTO dto, double quote) {
        double quantity = calculateQuantity(dto);
        return quote * quantity - dto.getDepositOrWithdraw();
    }

    private double calculateProfit(List<Crypto> cryptos, double amountInvested, CoinMarketCapDTO coinMarketCapDTO) {
        double totalCurrentBalance = calculateCurrentBalance(cryptos, coinMarketCapDTO);
        return totalCurrentBalance - amountInvested;
    }

    private double calculateProfitPercentage(CryptoDTO dto, double quote) {
        double profit = calculateProfit(dto, quote);
        double netAmount = calculateNetAmount(dto);

        if (netAmount == 0) {
            return 0.0;
        }
        return profit / netAmount * 100;
    }

    private double calculateProfitPercentage(List<Crypto> cryptos, double amountInvested, CoinMarketCapDTO coinMarketCapDTO) {
        if (amountInvested == 0) {
            return 0.0;
        }

        double totalCurrentBalance = calculateCurrentBalance(cryptos, coinMarketCapDTO);
        double profit = totalCurrentBalance - amountInvested;

        return profit / amountInvested * 100;
    }

    private double calculateAveragePrice(List<Crypto> cryptos) {
        double totalInvested = 0.0;
        double totalQuantity = 0.0;

        for (Crypto c : cryptos) {
            if (c.getType() == TransactionType.DEPOSIT) {
                totalInvested += c.getDepositOrWithdraw();
                totalQuantity += c.getQuantity();
            }
        }

        if (totalQuantity == 0) {
            return 0.0;
        }
        return totalInvested / totalQuantity;
    }

}
