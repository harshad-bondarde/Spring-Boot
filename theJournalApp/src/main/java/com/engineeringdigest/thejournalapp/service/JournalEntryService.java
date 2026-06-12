package com.engineeringdigest.thejournalapp.service;

import com.engineeringdigest.thejournalapp.entity.JournalEntry;
import com.engineeringdigest.thejournalapp.entity.User;
import com.engineeringdigest.thejournalapp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public JournalEntry saveEntry(JournalEntry journalEntry, String userName){
        User user = userService.findByUserName(userName);
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveUser(user);
        return journalEntry;
    }

    public JournalEntry saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
        return journalEntry;
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(String.valueOf(id));
    }

    public boolean deleteById(ObjectId id, String userName){
        User user = userService.findByUserName(userName);
        journalEntryRepository.deleteById(String.valueOf(id));
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveUser(user);
        return true;
    }



}
