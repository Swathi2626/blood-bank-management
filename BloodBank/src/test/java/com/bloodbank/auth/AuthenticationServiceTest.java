package com.bloodbank.auth;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class AuthenticationServiceTest {

    @Test
    public void demoPasswordIsHashedAndLoginDoesNotRequireASymbol() {
        InMemoryUserRepository repository = new InMemoryUserRepository();
        AuthenticationService service = new AuthenticationService(repository);

        assertNotEquals("admin123", repository.findById("admin").getPasswordHash());
        assertNotNull(service.login("admin", "admin123".toCharArray()));
        assertNull(service.login("admin", "wrong-password".toCharArray()));
    }
}