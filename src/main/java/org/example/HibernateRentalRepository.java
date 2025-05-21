package org.example;

import org.example.repositories.RentalRepository;
import org.hibernate.Session;
import java.util.List;
import java.util.Optional;

public abstract class HibernateRentalRepository implements RentalRepository {

    private final Session session;

    public HibernateRentalRepository(Session session) {
        this.session = session;
    }

    @Override
    public Optional<Rental> findById(String id) {
        return Optional.ofNullable(session.get(Rental.class, id));
    }

    @Override
    public List<Rental> findAll() {
        return session.createQuery("from Rental", Rental.class).list();
    }

    @Override
    public Rental save(Rental rental) {
        session.persist(rental);
        return rental;
    }

    @Override
    public void delete(String id) {
        Optional<Rental> r = findById(id);
        if (r.isPresent()) session.remove(r);
    }
}
