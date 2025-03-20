package com.example.controller;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.entity.Account;
import com.example.entity.Message;
import org.springframework.http.ResponseEntity;
import java.util.*;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;


    @PostMapping("/register")
    public ResponseEntity<Account> persistAccount(@RequestBody Account account) {
        Account registeredAccount = accountService.persistAccount(account);

        if (registeredAccount != null) {
            return ResponseEntity.ok(registeredAccount);
        } 
        else if (accountService.accountExists(account.getUsername())) {
            return ResponseEntity.status(409).build(); // Conflict
        }
        else {
            return ResponseEntity.status(400).build();
        }
    }


    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account returnedAccount = accountService.login(account);
    
        if (returnedAccount != null) {
            return ResponseEntity.ok(returnedAccount);
        }
        else {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> persistMessage(@RequestBody Message message) {
        Message persistedMessage = messageService.persistMessage(message);
    
        if (persistedMessage != null) {
            return ResponseEntity.ok(persistedMessage);
        }
        else {
            return ResponseEntity.status(400).build();
        }
    }


    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> allMessages = messageService.getAllMessages();
        
        return ResponseEntity.ok(allMessages);
        
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int id) {
        Message returnedMessage = messageService.getMessageById(id);
        return ResponseEntity.ok(returnedMessage);  
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int id) {
        int rowsUpdated = messageService.deleteMessageById(id);
        if (rowsUpdated > 0) {
            return ResponseEntity.ok(rowsUpdated);
        } else {
            return ResponseEntity.ok().build();
        } 
    }
}