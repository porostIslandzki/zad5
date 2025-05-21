package org.example;

import java.util.Optional;

public interface AuthService {
    boolean register(String login, String password, String role);
    Optional<User> login(String login, String password);
}
