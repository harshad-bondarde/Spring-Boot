package com.engineeringdigest.thejournalapp.service;

import com.engineeringdigest.thejournalapp.entity.JournalEntry;
import com.engineeringdigest.thejournalapp.entity.User;
import com.engineeringdigest.thejournalapp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired // Optional in newer Spring versions if it's the only constructor
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public void saveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user); //since userName is unique if password is changed same user will get override , else if userName is also new then new user will bw created
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }

    public boolean deleteById(ObjectId id){
        userRepository.deleteById(id);
        return true;
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
}