package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 04.02.2021
 */
public class HbmRun {
    public static void main(String[] args) {
        Student rsl = null;

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            try (Session session = sf.openSession()) {
                Transaction tx = session.beginTransaction();
                rsl = session.createQuery(
                        "select distinct st from Student st "
                         + " join fetch st.account a"
                         + " join fetch a.libBooks b"
                         + " where st.id = :paramId",
                        Student.class
                ).setParameter("paramId", 1)
                        .uniqueResult();
                System.out.println(rsl);
                tx.commit();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }

        System.out.println(rsl);
    }
}
