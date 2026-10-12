package com.bloodbank.auth;

import com.bloodbank.common.Repository;

public class AuthenticationService {

    private Repository<User, String> userRepository;

    public AuthenticationService(Repository<User, String> userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String username, char[] password) {
        if (username == null || username.isBlank() || password == null || password.length == 0) {
            return null;
        }

        User user = userRepository.findById(username.trim());

        if (user != null && PasswordHasher.matches(password, user.getPasswordHash())) {
            return user;
        }

        return null;
    }
}