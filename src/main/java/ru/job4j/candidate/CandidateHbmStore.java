package ru.job4j.candidate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 04.02.2021
 */
public class CandidateHbmStore implements AutoCloseable {
    private final StandardServiceRegistry registry =
            new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    <T> T tx(Function<Session, T> action) {
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

    public void add(Candidate candidate) {
        tx(session -> session.save(candidate));
    }

    public void update(Candidate candidate) {
        tx(session -> {
            Query query = session.createQuery(
                    "update Candidate c set c.name = :paramName, c.experience = :paramExp, c.salary = :paramSalary where c.id = :paramId");
            query.setParameter("paramId", candidate.getId());
            query.setParameter("paramName", candidate.getName());
            query.setParameter("paramExp", candidate.getExperience());
            query.setParameter("paramSalary", candidate.getSalary());
            query.executeUpdate();
            return null;
        });
    }

    public void delete(Candidate candidate) {
        tx(session -> {
            Query query = session.createQuery(
                    "delete from Candidate where id = :paramId");
            query.setParameter("paramId", candidate.getId());
            query.executeUpdate();
            return null;
        });
    }

    public List<Candidate> getAll() {
        return tx(session -> session.createQuery("from Candidate ").list());
    }

    public Candidate getById(int id) {
        return (Candidate) tx(session -> session.createQuery(
                "from Candidate where id = :paramId").setParameter("paramId", id).uniqueResult());
    }

    public List<Candidate> getByName(String name) {
        return tx(session -> session.createQuery(
                "from Candidate where name = :paramName").setParameter("paramName", name).list());
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
