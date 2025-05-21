package org.example;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUser(String id);
    List<User> getAllUsers();
    void createUser(User user);
    void removeUser(String id);
}
