package org.example.repositories;

import org.example.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(String id);
    List<User> findAll();
    User save(User user);
    void delete(String id);

    Optional<User> findByLogin(String login);

    void deleteById(String id);
}
