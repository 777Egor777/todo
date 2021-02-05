package ru.job4j.candidate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.function.Function;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 05.02.2021
 */
public class StdHbmStore implements AutoCloseable {
    private final StandardServiceRegistry registry =
            new StandardServiceRegistryBuilder()
                    .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private <T> T tx(Function<Session, T> action) {
        final Session session = sf.openSession();
        final Transaction tr = session.beginTransaction();
        try {
            T result = action.apply(session);
            tr.commit();
            return result;
        } catch (Exception ex) {
            tr.rollback();
            throw ex;
        } finally {
            session.close();
        }
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    public <T> T create(T model) {
        return tx(session -> {
            session.save(model);
            return  model;
        });
    }

    public <T> T update(T model) {
        return tx(session -> {
            session.update(model);
            return  model;
        });
    }

    public <T> T get(Class<T> cl, int id) {
        return tx(session -> session.get(cl, id));
    }
}
