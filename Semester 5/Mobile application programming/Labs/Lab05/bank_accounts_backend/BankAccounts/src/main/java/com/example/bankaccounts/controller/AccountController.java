package com.example.bankaccounts.controller;

import com.example.bankaccounts.dto.AccountDTO;
import com.example.bankaccounts.dto.Message;
import com.example.bankaccounts.entity.Account;
import com.example.bankaccounts.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private IAccountService accountService;

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAccounts() {
        logger.info("Getting all accounts");
        return new ResponseEntity<>(accountService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/accounts/{appId}")
    public ResponseEntity<Account> addAccount(@RequestBody AccountDTO accountDTO, @PathVariable String appId) {
        logger.info("Added new account " + accountDTO);
        Account account = accountService.addAccount(accountDTO.toModel());
        simpMessagingTemplate.convertAndSend("/changes/listen", new Message(Message.Type.ADD, account, appId));
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @DeleteMapping("/accounts/{id}&{appId}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id, @PathVariable String appId) {
        logger.info("Deleting account with id " + id);
        accountService.deleteAccount(id);
        simpMessagingTemplate.convertAndSend("/changes/listen", new Message(Message.Type.DELETE, id, appId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/accounts/{appId}")
    public ResponseEntity<?> updateAccount(@RequestBody AccountDTO accountDTO, @PathVariable String appId) {
        logger.info("Updating account " + accountDTO);
        accountService.updateAccount(accountDTO.toModel());
        simpMessagingTemplate.convertAndSend("/changes/listen", new Message(Message.Type.UPDATE, accountDTO, appId));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
