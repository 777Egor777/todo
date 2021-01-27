package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.function.Function;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 27.01.2021
 */
public class HibernateTaskStore implements TaskStore, AutoCloseable {
    private final StandardServiceRegistry registry =
            new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    private HibernateTaskStore() {
    }

    private static final class Holder {
        private static final TaskStore INSTANCE = new HibernateTaskStore();
    }

    public static TaskStore instOf() {
        return Holder.INSTANCE;
    }

    @SuppressWarnings("DuplicatedCode")
    private <T> T tx(Function<Session, T> func) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = func.apply(session);
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
    public Task get(int id) {
        return tx(session -> session.get(Task.class, id));
    }

    @Override
    public Task add(Task task) {
        return tx(session -> {
            session.save(task);
            return task;
        });
    }

    @Override
    public Task update(Task task) {
        return tx(session -> {
            session.update(task);
            return task;
        });
    }

    @Override
    public List<Task> getAll(String userLogin) {
        return tx(session -> {
            Query query = session.createQuery(
                    "from ru.job4j.todo.model.Task where userLogin = :paramUserLogin");
            query.setParameter("paramUserLogin", userLogin);
            return query.list();
        });
    }

    @Override
    public List<Task> getAllOpen(String userLogin) {
        return tx(session -> {
            Query query = session.createQuery(
                    "from ru.job4j.todo.model.Task where done = :paramDone and userLogin = :paramUserLogin");
            query.setParameter("paramDone", false);
            query.setParameter("paramUserLogin", userLogin);
            return query.list();
        });
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }

}