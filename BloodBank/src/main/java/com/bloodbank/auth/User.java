package com.bloodbank.auth;

import com.bloodbank.common.Role;

public class User {

    private String username;
    private String passwordHash;
    private Role role;

    public User(String username, String password, Role role) {
        this(username, PasswordHasher.hash(password), role, true);
    }

    private User(String username, String passwordValue, Role role, boolean isHash) {
        this.username = username;
        this.passwordHash = isHash ? passwordValue : PasswordHasher.hash(passwordValue);
        this.role = role;
    }

    public static User fromPasswordHash(String username, String passwordHash, Role role) {
        return new User(username, passwordHash, role, true);
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setPassword(String password) {
        this.passwordHash = PasswordHasher.hash(password);
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", role=" + role +
                '}';
    }
}
