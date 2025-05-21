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
        session.persist(user);
        return user;
    }

    @Override
    public void delete(String id) {
        Optional<User> u = findById(id);
        if (u != null) session.remove(u);
    }
}
