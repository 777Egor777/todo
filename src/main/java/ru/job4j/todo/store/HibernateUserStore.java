package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.User;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

/**
 * Hibernate-хранилище для пользователей.
 *
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 25.01.2021
 */
public class HibernateUserStore implements UserStore {
    private final StandardServiceRegistry registry =
            new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    private HibernateUserStore() {
    }

    private static final class Holder {
        private static final UserStore INSTANCE = new HibernateUserStore();
    }

    public static UserStore instOf() {
        return Holder.INSTANCE;
    }

    public static UserStore testInstance() {
        return new HibernateUserStore();
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    private <T> T tx(Function<Session, T> func) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            session.clear();
            T rsl = func.apply(session);
            session.flush();
            tx.commit();
            return rsl;
        } catch (Exception ex) {
            tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
    }

    @Override
    public User add(User user) {
        return tx(session -> {
            session.save(user);
            return user;
        });
    }

    @Override
    public User update(User user) {
        return tx(session -> {
            session.update(user);
            return user;
        });
    }


    @Override
    public User get(String login) {
        return tx(session -> session.get(User.class, login));
    }
}
