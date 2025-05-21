package org.example;

import org.example.repositories.UserRepository;
import org.hibernate.Session;
import java.util.List;
import java.util.Optional;

public abstract class HibernateUserRepository implements UserRepository {

    private final Session session;

    public HibernateUserRepository(Session session) {
        this.session = session;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(session.get(User.class, id));
    }

    @Override
    public List<User> findAll() {
        return session.createQuery("from User", User.class).list();
    }

    @Override
    public User save(User user) {
        try {
            if (!session.getTransaction().isActive()) {
                session.beginTransaction();
            }

            session.persist(user);
            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            throw new RuntimeException("brak", e);
        }
    }


    @Override
    public void delete(String id) {
        try{
            User user = session.get(User.class, id);
            if(user != null){
                session.beginTransaction();
                session.remove(user);
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException("brak", e);
        }
    }
}
