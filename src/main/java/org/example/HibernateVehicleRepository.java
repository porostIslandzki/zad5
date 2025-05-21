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
        try {
            session.beginTransaction();
            session.persist(vehicle);
            session.getTransaction().commit();
            return vehicle;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException("Nie udało się zapisać pojazdu", e);
        }
    }

    @Override
    public void delete(String id) {
        try {
            Vehicle vehicle = session.get(Vehicle.class, id);
            if (vehicle != null) {
                session.beginTransaction();
                session.remove(vehicle);
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException("Nie udało się usunąć pojazdu", e);
        }
    }
}
