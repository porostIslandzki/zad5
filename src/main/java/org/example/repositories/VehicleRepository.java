package org.example.repositories;

import org.example.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository {
    Optional<Vehicle> findById(String id);
    List<Vehicle> findAll();
    Vehicle save(Vehicle vehicle);
    void delete(String id);

    void deleteById(String id);
}
