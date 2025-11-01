package com.edigest.journalApp.service;

import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.repository.JournalEntryRepository;
import com.edigest.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Correct method for creating a brand new user
    public void saveNewUser(User user) {
        // The problematic try-catch block has been removed.
        // The logic is the same, but now it will throw an exception on failure.
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);
    }

    // NEW METHOD: For updating an existing user without re-hashing the password
    public void saveUser(User user) {
        userRepository.save(user);
    }

    // NEW HELPER METHOD: To be used when password is being intentionally changed
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    @Transactional
    public void deleteByUserName(String username) {
        User user = userRepository.findByUserName(username);
        if (user != null && user.getJournalEntries() != null && !user.getJournalEntries().isEmpty()) {
            journalEntryRepository.deleteAll(user.getJournalEntries());
        }
        userRepository.deleteByUserName(username);
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}