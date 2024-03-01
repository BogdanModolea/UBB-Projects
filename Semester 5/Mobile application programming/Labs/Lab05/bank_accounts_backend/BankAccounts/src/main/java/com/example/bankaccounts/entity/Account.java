package com.example.bankaccounts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String iban;
    private String sold;
    private String currency;
    private String bank;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(iban, account.iban) && Objects.equals(sold, account.sold) && Objects.equals(currency, account.currency) && Objects.equals(bank, account.bank) && Objects.equals(name, account.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, iban, sold, currency, bank, name);
    }
}
