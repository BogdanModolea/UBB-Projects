package com.example.bankaccounts.service;

import com.example.bankaccounts.entity.Account;
import com.example.bankaccounts.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService implements IAccountService{
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account addAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.findById(id).ifPresentOrElse(account -> accountRepository.deleteById(id), () -> {
                    throw new RuntimeException("Account not found!");
                }
        );
    }

    @Override
    @Transactional
    public void updateAccount(Account account) {
        accountRepository.findById(account.getId()).ifPresentOrElse(a -> {
            a.setIban(account.getIban());
            a.setSold(account.getSold());
            a.setCurrency(account.getCurrency());
            a.setBank(account.getBank());
            a.setName(account.getName());
        }, () -> {
            throw new RuntimeException("Account not found!");
        });
    }
}
