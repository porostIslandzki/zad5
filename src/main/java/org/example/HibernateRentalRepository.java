package org.example;

import org.example.repositories.RentalRepository;
import org.hibernate.Session;
import java.util.List;
import java.util.Optional;

public abstract class HibernateRentalRepository implements RentalRepository {

    private final Session session;

    //dodanie transakcji
    @Override
    public Rental save(Rental rental){
        session.beginTransaction();
        session.persist(rental);
        session.getTransaction().commit();
        return rental;
    }

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
    public void delete(String id) {
        Rental rental =
                session.get(Rental.class, id);
        if(rental != null){
            session.beginTransaction();
            session.remove(rental);
            session.getTransaction().commit();
        }
    }
}
