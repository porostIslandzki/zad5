package org.example.repositories.impl.jdbc;

import org.example.User;
import org.example.db.JdbcConnectionManager;
import org.example.repositories.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class UserJdbcRepository implements UserRepository {

    private final JdbcConnectionManager connectionManager = JdbcConnectionManager.getInstance();

    @Override
    public Optional<org.example.User> findById(String id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User(
                            rs.getString("id"),
                            rs.getString("login"),
                            rs.getString("password"),
                            rs.getString("role")
                    );
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reading user by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                        rs.getString("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("role")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reading users", e);
        }
        return users;
    }

    @Override
    public User save(User user) {
        if (user.getId() == null || user.getId().isBlank()) {
            user.setId(UUID.randomUUID().toString());
        }

        String sql = "INSERT INTO users (id, login, password, role) VALUES (?, ?, ?, ?)";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, user.getId());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving user", e);
        }
        return user;
    }

    @Override
    public void deleteById(String id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting user", e);
        }
    }
}
