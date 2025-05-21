package org.example;

import org.example.repositories.VehicleRepository;

import java.util.List;
import java.util.Optional;

public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Optional<Vehicle> getVehicle(String id) {
        return vehicleRepository.findById(id);
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public void createVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    @Override
    public void removeVehicle(String id) {
        vehicleRepository.delete(id);
    }
    @Override
    public List<Vehicle> listAvailableVehicles() {
        return vehicleRepository.findAll();
    }

}
