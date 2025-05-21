package org.example;

import java.util.List;
import java.util.Optional;

public interface VehicleService {
    Optional<Vehicle> getVehicle(String id);
    List<Vehicle> getAllVehicles();
    List<Vehicle> listAvailableVehicles();
    void createVehicle(Vehicle vehicle);
    void removeVehicle(String id);
}
