package org.example.repositories.impl.json;

import com.google.gson.reflect.TypeToken;
import org.example.User;
import org.example.db.JsonFileStorage;
import org.example.repositories.UserRepository;


import java.util.*;

public abstract class UserJsonRepository implements UserRepository {

    private final JsonFileStorage<User> storage =
            new JsonFileStorage<>("users.json", new TypeToken<List<User>>(){}.getType());

    private final List<User> users;

    public UserJsonRepository() {
        this.users = new ArrayList<>(storage.load());
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    @Override
    public Optional<User> findById(String id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    @Override
    public User save(User user) {
        if (user.getId() == null || user.getId().isBlank()) {
            user.setId(UUID.randomUUID().toString());
        } else {
            delete(user.getId());
        }
        users.add(user);
        storage.save(users);
        return user;
    }

    @Override
    public void delete(String id) {
        users.removeIf(u -> u.getId().equals(id));
        storage.save(users);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return users.stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst();
    }
}
