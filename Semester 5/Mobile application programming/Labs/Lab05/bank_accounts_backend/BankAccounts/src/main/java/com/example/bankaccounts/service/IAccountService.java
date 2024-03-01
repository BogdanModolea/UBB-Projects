package com.example.bankaccounts.service;

import com.example.bankaccounts.entity.Account;

import java.util.List;

public interface IAccountService {
    List<Account> getAll();

    Account addAccount(Account account);

    void deleteAccount(Long id);

    void updateAccount(Account account);
}
