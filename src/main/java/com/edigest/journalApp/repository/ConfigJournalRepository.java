package com.edigest.journalApp.repository;

import com.edigest.journalApp.entity.ConfigJournalAppEntity;
import com.edigest.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {


}
