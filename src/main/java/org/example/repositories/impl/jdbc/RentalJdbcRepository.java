package org.example.repositories.impl.jdbc;


import org.example.Rental;
import org.example.db.JdbcConnectionManager;
import org.example.repositories.RentalRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class RentalJdbcRepository implements RentalRepository {

    private final JdbcConnectionManager connectionManager = JdbcConnectionManager.getInstance();

    @Override
    public Optional<org.example.Rental> findById(String id) {
        String sql = "SELECT * FROM rental WHERE id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Rental rental = new Rental(
                            rs.getString("id"),
                            rs.getString("vehicle_id"),
                            rs.getString("user_id"),
                            rs.getString("rent_date"),
                            rs.getString("return_date")
                    );
                    return Optional.of(rental);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reading rental by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Rental> findAll() {
        List<Rental> rentals = new ArrayList<>();
        String sql = "SELECT * FROM rental";

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Rental rental = new Rental(
                        rs.getString("id"),
                        rs.getString("vehicle_id"),
                        rs.getString("user_id"),
                        rs.getString("rent_date"),
                        rs.getString("return_date")
                );
                rentals.add(rental);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error reading rentals", e);
        }
        return rentals;
    }

    @Override
    public Rental save(Rental rental) {
        if (rental.getId() == null || rental.getId().isBlank()) {
            rental.setId(UUID.randomUUID().toString());
        }

        String sql = "INSERT INTO rental (id, vehicle_id, user_id, rent_date, return_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, rental.getId());
            stmt.setString(2, rental.getVehicleId());
            stmt.setString(3, rental.getUserId());
            stmt.setString(4, rental.getRentDate());
            stmt.setString(5, rental.getReturnDate());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving rental", e);
        }

        return rental;
    }

    @Override
    public void deleteById(String id) {
        String sql = "DELETE FROM rental WHERE id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting rental", e);
        }
    }

    @Override
    public Optional<Rental> findByVehicleIdAndReturnDateIsNull(String vehicleId) {
        return Optional.empty();
    }
}
