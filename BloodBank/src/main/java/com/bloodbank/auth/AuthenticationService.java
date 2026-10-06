package com.bloodbank.auth;

import com.bloodbank.common.Repository;

public class AuthenticationService {

    private Repository<User, String> userRepository;

    public AuthenticationService(Repository<User, String> userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String username, String password) {

        User user = userRepository.findById(username);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }
}