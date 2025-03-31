package com.tiwilli.cryptoport.services;

import com.tiwilli.cryptoport.dto.InvestmentDTO;
import com.tiwilli.cryptoport.entities.Investment;
import com.tiwilli.cryptoport.repositories.InvestmentRepository;
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
public class InvestmentService {

    @Autowired
    private InvestmentRepository repository;

    @Transactional(readOnly = true)
    public InvestmentDTO findById(Long id) {
        Investment investment = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Entity not found"));
        return new InvestmentDTO(investment);
    }

    @Transactional(readOnly = true)
    public Page<InvestmentDTO> findAll(Pageable pageable) {
        Page<Investment> result = repository.findAll(pageable);
        return result.map(InvestmentDTO::new);
    }

    @Transactional
    public InvestmentDTO insert(InvestmentDTO dto) {
        Investment entity = new Investment();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new InvestmentDTO(entity);
    }

    @Transactional
    public InvestmentDTO update(Long id, InvestmentDTO dto) {
        try {
            Investment entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new InvestmentDTO(entity);
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
    private void copyDtoToEntity(InvestmentDTO dto, Investment entity) {
        entity.setName(dto.getName());
        entity.setDate(dto.getDate());
        entity.setDepositOrWithdraw(dto.getDepositOrWithdraw());
        entity.setCryptoValue(dto.getCryptoValue());
        entity.setQuantity(dto.getQuantity());
        entity.setProfit(dto.getProfit());
        entity.setProfitPercentage(dto.getProfitPercentage());
        entity.setBrokerage(dto.getBrokerage());

    }

}
