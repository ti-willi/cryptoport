package com.tiwilli.cryptoport.entities;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "tb_portfolio")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double amountInvested;

    @OneToMany(mappedBy = "portfolio")
    private List<Crypto> cryptos = new ArrayList<>();

    public Portfolio() {
    }

    public Portfolio(Long id, String name, Double amountInvested) {
        this.id = id;
        this.name = name;
        this.amountInvested = amountInvested;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmountInvested() {
        return amountInvested;
    }

    public void setAmountInvested(Double amountInvested) {
        this.amountInvested = amountInvested;
    }

    public List<Crypto> getCryptos() {
        return cryptos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Portfolio portfolio = (Portfolio) o;

        return Objects.equals(id, portfolio.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
