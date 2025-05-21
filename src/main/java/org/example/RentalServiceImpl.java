package org.example;

import org.example.repositories.RentalRepository;

import java.util.List;
import java.util.Optional;

public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;

    public RentalServiceImpl(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    @Override
    public Optional<Rental> getRental(String id) {
        return rentalRepository.findById(id);
    }

    @Override
    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    @Override
    public void createRental(Rental rental) {
        rentalRepository.save(rental);
    }

    @Override
    public void removeRental(String id) {
        rentalRepository.delete(id);
    }
    @Override
    public void rentVehicle(String userId, String vehicleId) {
        Rental rental = Rental.builder()
                .id(java.util.UUID.randomUUID().toString())
                .userId(userId)
                .vehicleId(vehicleId)
                .rentDate(java.time.LocalDate.now().toString())
                .returnDate(null)
                .build();
        rentalRepository.save(rental);
    }

    @Override
    public void returnVehicle(String userId, String vehicleId) {
        Rental rental = rentalRepository.findAll().stream()
                .filter(r -> r.getUserId().equals(userId) && r.getVehicleId().equals(vehicleId) && r.getReturnDate() == null)
                .findFirst()
                .orElse(null);

        if (rental != null) {
            rental.setReturnDate(java.time.LocalDate.now().toString());
            rentalRepository.save(rental);
        }
    }

}
