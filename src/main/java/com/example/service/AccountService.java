package com.example.service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    AccountRepository accountRepository;
    
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account persistAccount(Account account){
        if (accountRepository.findByUsername(account.getUsername()) != null){
            return null;
        }
        if (account.getUsername() == ""){
            return null;
        }
        if (account.getPassword().length() < 4){
            return null;
        }

        return accountRepository.save(account);
    }

    public boolean accountExists(String username) {
        return accountRepository.findByUsername(username) != null;
    }


    public Account login(Account account){
        if (accountRepository.findByUsername(account.getUsername()) == null){
            return null;
        }
        Account existing = accountRepository.findByUsername(account.getUsername());

        if (!account.getPassword().equals(existing.getPassword())){
            return null;
        }

        return existing;
    }

}
