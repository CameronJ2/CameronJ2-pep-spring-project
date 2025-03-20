package com.example.service;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message persistMessage(Message message){
        if (message.getMessageText() == "" || message.getMessageText().length() > 255 || !accountRepository.findById(message.getPostedBy()).isPresent()){
            return null;
        }

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(int messageId){
        Optional<Message> message = messageRepository.findById(messageId);

        if (message.isPresent()){
            return message.get();
        }
        else{
            return null;
        }
    }

    public int deleteMessageById(int id) {
        Optional<Message> message = messageRepository.findById(id);

        if (message.isPresent()){
            messageRepository.deleteById(id);
            return 1;
        }
        else{
            return 0;
        }
    }

    public int updateMessageById(int id, String newMessage) {
        Optional<Message> messageCheck = messageRepository.findById(id);
        Message message;

        if (!messageCheck.isPresent()){
            return 0;
        } else{
            message = messageCheck.get();
        }

        if (newMessage == null || newMessage.length() > 255 || newMessage.trim().isEmpty()){
            return 0;
        }

        message.setMessageText(newMessage);
        messageRepository.save(message);
        return 1;
    }

    public List<Message> getAllMessagesById(int id){
        return messageRepository.findByPostedBy(id);
    }
}
