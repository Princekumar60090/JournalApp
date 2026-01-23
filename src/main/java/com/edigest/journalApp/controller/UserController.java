package com.edigest.journalApp.controller;

import com.edigest.journalApp.api.response.WeatherResponse;
import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.service.UserService;
import com.edigest.journalApp.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name="User APIs",description = "Read, Update & Delete User")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private WeatherService weatherService;

    // UPDATED METHOD
    @PutMapping
    @Operation(summary = "Update user details")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);

        if (userInDb != null) {
            // Only update username if a new one is provided and not empty
            if (user.getUserName() != null && !user.getUserName().isEmpty()) {
                userInDb.setUserName(user.getUserName());
            }

            // Only update password if a new one is provided and not empty
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                userInDb.setPassword(userService.encodePassword(user.getPassword()));
            }

            userService.saveUser(userInDb);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    @Operation(summary = "Delete user by Id")
    public ResponseEntity<?> deleteUserById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        userService.deleteByUserName(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping
    @Operation(summary = "Greet user with Weather Response of given city")
    public ResponseEntity<?> greetings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse =  weatherService.getWeather("Mumbai");
        String greeting="";
        if(weatherResponse!=null){
            greeting= ", Weather feels like "+weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi "+authentication.getName()+ greeting ,HttpStatus.OK);
    }
}