package com.engineeringdigest.thejournalapp.repository;

import com.engineeringdigest.thejournalapp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String username);
}
