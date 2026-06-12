package com.digest.journalApp.controller;

import com.digest.journalApp.entity.JournalEntry;
import com.digest.journalApp.entity.User;
import com.digest.journalApp.service.JournalEntryService;
import com.digest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName){
        User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all , HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry , @PathVariable String userName){//this converts data from incoming object to java object
        try {
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
        Optional<JournalEntry> journalEntry=journalEntryService.findById(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
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
