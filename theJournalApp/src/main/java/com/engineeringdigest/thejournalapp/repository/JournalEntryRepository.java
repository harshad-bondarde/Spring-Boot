package com.engineeringdigest.thejournalapp.repository;

import com.engineeringdigest.thejournalapp.entity.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, String> {

}
