package com.edigest.journalApp.repository;

import com.edigest.journalApp.entity.JournalEntry;
import com.edigest.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String username);
    void deleteByUserName(String username);

    List<User> getUserForSA();
}
