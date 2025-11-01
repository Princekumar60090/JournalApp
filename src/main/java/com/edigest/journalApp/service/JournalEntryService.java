package com.edigest.journalApp.service;

import com.edigest.journalApp.entity.JournalEntry;
import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    // UPDATED METHOD
    @Transactional
    public JournalEntry saveEntry(JournalEntry journalEntry, String userName) {
        User user = userService.findByUserName(userName);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);

        // Ensure the list is initialized before adding to it
        if (user.getJournalEntries() == null) {
            user.setJournalEntries(new ArrayList<>());
        }
        user.getJournalEntries().add(saved);

        // Use the correct save method that DOES NOT re-hash the password
        userService.saveUser(user);
        return saved;
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    // UPDATED METHOD
    @Transactional
    public boolean deleteById(ObjectId id, String userName) {
        boolean removed = false;
        User user = userService.findByUserName(userName);
        if (user != null) {
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                // Use the correct save method that DOES NOT re-hash the password
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
        }
        return removed;
    }
}