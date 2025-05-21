package org.example.repositories;

import org.example.Rental;

import java.util.List;
import java.util.Optional;

public interface RentalRepository {
    Optional<Rental> findById(String id);
    List<Rental> findAll();
    Rental save(Rental rental);
    void delete(String id);

    void deleteById(String id);

    //TODO: Użycie funkcji findByVehicleIdAndReturnDateIsNull w swojej logice
    Optional<Rental> findByVehicleIdAndReturnDateIsNull(String vehicleId);
}
