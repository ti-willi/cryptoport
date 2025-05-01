package com.tiwilli.cryptoport.entities;

import com.tiwilli.cryptoport.entities.enums.TransactionType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tb_crypto")
public class Crypto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cryptoId;
    private String name;
    private String symbol;
    private String logoUrl;
    private LocalDate date;
    private Double depositOrWithdraw;
    private Double cryptoValue;
    private Double quantity;
    private Double brokerageFee;
    private TransactionType type;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    public Crypto() {
    }

    public Crypto(Long id, Long cryptoId, String name, String symbol, String logoUrl, LocalDate date, Double depositOrWithdraw, Double cryptoValue, Double quantity, Double brokerageFee, TransactionType type, Portfolio portfolio) {
        this.id = id;
        this.cryptoId = cryptoId;
        this.name = name;
        this.symbol = symbol;
        this.logoUrl = logoUrl;
        this.date = date;
        this.depositOrWithdraw = depositOrWithdraw;
        this.cryptoValue = cryptoValue;
        this.quantity = quantity;
        this.brokerageFee = brokerageFee;
        this.type = type;
        this.portfolio = portfolio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCryptoId() {
        return cryptoId;
    }

    public void setCryptoId(Long cryptoId) {
        this.cryptoId = cryptoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getDepositOrWithdraw() {
        return depositOrWithdraw;
    }

    public void setDepositOrWithdraw(Double depositOrWithdraw) {
        this.depositOrWithdraw = depositOrWithdraw;
    }

    public Double getCryptoValue() {
        return cryptoValue;
    }

    public void setCryptoValue(Double cryptoValue) {
        this.cryptoValue = cryptoValue;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getBrokerageFee() {
        return brokerageFee;
    }

    public void setBrokerageFee(Double brokerageFee) {
        this.brokerageFee = brokerageFee;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Crypto crypto = (Crypto) o;

        return Objects.equals(id, crypto.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
