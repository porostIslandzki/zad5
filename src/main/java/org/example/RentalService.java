package org.example;

import java.util.List;
import java.util.Optional;

public interface RentalService {
    Optional<Rental> getRental(String id);
    List<Rental> getAllRentals();
    void createRental(Rental rental);
    void removeRental(String id);
    void rentVehicle(String userId, String vehicleId);
    void returnVehicle(String userId, String vehicleId);
}
