package org.example;

import org.example.repositories.VehicleRepository;
import org.hibernate.Session;
import java.util.List;
import java.util.Optional;

public abstract class HibernateVehicleRepository implements VehicleRepository {

    private final Session session;

    public HibernateVehicleRepository(Session session) {
        this.session = session;
    }

    @Override
    public Optional<Vehicle> findById(String id) {
        return Optional.ofNullable(session.get(Vehicle.class, id));
    }

    @Override
    public List<Vehicle> findAll() {
        return session.createQuery("from Vehicle", Vehicle.class).list();
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        session.persist(vehicle);
        return vehicle;
    }

    @Override
    public void delete(String id) {
        Optional<Vehicle> v = findById(id);
        if (v.isPresent()) session.remove(v);
    }
}
