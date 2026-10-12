package com.bloodbank.auth;

import org.mindrot.jbcrypt.BCrypt;

public final class PasswordHasher {

    private static final int COST = 12;

    private PasswordHasher() {
    }

    public static String hash(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null.");
        }
        return BCrypt.hashpw(password, BCrypt.gensalt(COST));
    }

    public static boolean matches(char[] password, String passwordHash) {
        if (password == null || passwordHash == null) {
            return false;
        }
        try {
            return BCrypt.checkpw(new String(password), passwordHash);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}