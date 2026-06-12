package com.engineeringdigest.thejournalapp.controller;

import com.engineeringdigest.thejournalapp.entity.JournalEntry;
import com.engineeringdigest.thejournalapp.entity.User;
import com.engineeringdigest.thejournalapp.service.JournalEntryService;
import com.engineeringdigest.thejournalapp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;
    private Map<Long, JournalEntry> journalEntries =new HashMap<>();


    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all , HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){//this converts data from incoming object to java object
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry , userName);

            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }catch(Exception e){
            System.out.println("error"+e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/id/{myId}") //using path variable
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user=userService.findByUserName(userName);
        List <JournalEntry> collect = user.getJournalEntries().stream().filter(x-> x.getId().equals(myId)).toList();
        if(collect!=null){
            Optional<JournalEntry> journalEntry=journalEntryService.findById(myId);
            if(journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }


        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{userName}/{myId}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myId , @PathVariable String userName){
        journalEntryService.deleteById(myId , userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{userName}/{myId}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId myId ,@PathVariable String userName , @RequestBody JournalEntry newEntry){

        JournalEntry old = journalEntryService.findById(myId).orElse(null);
        if(old!=null){
            old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent()!=null && !newEntry.getTitle().isEmpty() ? newEntry.getContent() : old.getContent());
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        journalEntryService.saveEntry(old);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
