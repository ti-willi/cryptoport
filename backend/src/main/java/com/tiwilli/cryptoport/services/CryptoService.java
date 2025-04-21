package com.tiwilli.cryptoport.services;

import com.tiwilli.cryptoport.dto.CryptoDTO;
import com.tiwilli.cryptoport.dto.CryptoMinDTO;
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

import java.util.*;
import java.util.stream.Collectors;

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
        //updateBalance(entity);
        entity = repository.save(entity);
        //updatePortfolio(dto.getPortfolioId());
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
        entity.setDate(dto.getDate());
        entity.setDepositOrWithdraw(dto.getDepositOrWithdraw());
        entity.setCryptoValue(dto.getCryptoValue());
        entity.setCurrentBalance(Utils.decimalFormat(calculateCurrentBalance(dto, 60000.0)));
        entity.setQuantity(calculateQuantity(dto));
        entity.setProfit(Utils.decimalFormat(calculateProfit(dto, 60000.0)));
        entity.setProfitPercentage(Utils.decimalFormat(calculateProfitPercentage(dto, 60000.0)));
        entity.setType(dto.getType());
        entity.setBrokerageFee(dto.getBrokerageFee());

        Portfolio portfolio = portfolioRepository.getReferenceById(dto.getPortfolioId());
        entity.setPortfolio(portfolio);
    }

    @Transactional(readOnly = true)
    public List<CryptoMinDTO> getGroupedCryptoBalances(Long id) {
        List<Crypto> allCryptos = repository.findByPortfolioId(id);

        Map<String, List<Crypto>> grouped = allCryptos.stream()
                .collect(Collectors.groupingBy(Crypto::getName));

        List<CryptoMinDTO> result = new ArrayList<>();

        for (Map.Entry<String, List<Crypto>> entry : grouped.entrySet()) {
            String name = entry.getKey();
            List<Crypto> cryptos = entry.getValue();

            CryptoMinDTO dto = new CryptoMinDTO();
            dto.setName(name);
            dto.setAmountInvested(Utils.decimalFormat(calculateAmountInvested(cryptos)));
            dto.setCurrentBalance(Utils.decimalFormat(calculateCurrentBalance(cryptos)));
            dto.setQuantity(calculateQuantity(cryptos));
            dto.setAveragePrice(Utils.decimalFormat(calculateAveragePrice(cryptos)));
            dto.setProfit(Utils.decimalFormat(calculateProfit(cryptos, dto.getAmountInvested())));
            dto.setProfitPercentage(Utils.decimalFormat(calculateProfitPercentage(cryptos, dto.getAmountInvested())));

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

    private double calculateCurrentBalance(CryptoDTO dto, double currentValue) {
        double quantity = calculateQuantity(dto);

        return quantity * currentValue;
    }

    private double calculateCurrentBalance(List<Crypto> cryptos) {
        return cryptos.stream()
                .mapToDouble(Crypto::getCurrentBalance)
                .sum();
    }

    private double calculateProfit(CryptoDTO dto, double currentValue) {
        double quantity = calculateQuantity(dto);
        if (dto.getType() == TransactionType.DEPOSIT) {
            return (currentValue * quantity) - dto.getDepositOrWithdraw();
        }
        else {
            return dto.getDepositOrWithdraw() - (dto.getCryptoValue() * Math.abs(quantity));
        }
    }

    private double calculateProfit(List<Crypto> cryptos, double amountInvested) {
        double totalCurrentBalance = cryptos.stream()
                .mapToDouble(Crypto::getCurrentBalance)
                .sum();

        return totalCurrentBalance - amountInvested;
    }

    private double calculateProfitPercentage(CryptoDTO dto, double currentValue) {
        double profit = calculateProfit(dto, currentValue);
        double netAmount = calculateNetAmount(dto);

        if (netAmount == 0) {
            return 0;
        }
        return (profit / netAmount) * 100;
    }

    private double calculateProfitPercentage(List<Crypto> cryptos, double amountInvested) {
        if (amountInvested == 0) return 0.0;

        double totalCurrentBalance = cryptos.stream()
                .mapToDouble(Crypto::getCurrentBalance)
                .sum();

        double profit;
        if (amountInvested > 0) {
            profit = totalCurrentBalance - amountInvested;
        }
        else {
            profit = totalCurrentBalance + amountInvested;
        }

        return (profit / amountInvested) * 100.0;
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
