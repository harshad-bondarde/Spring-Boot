package com.engineeringdigest.thejournalapp.controller;

import com.engineeringdigest.thejournalapp.entity.User;
import com.engineeringdigest.thejournalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("health-check")
    public String healthCheck(){
        return "ok";
    }

    @PostMapping("/createUser")
    public void createUser(@RequestBody User user){
        System.out.println(user);
        userService.saveNewUser(user);
    }
}
