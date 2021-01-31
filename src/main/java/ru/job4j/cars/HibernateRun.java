package ru.job4j.cars;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 31.01.2021
 */
public class HibernateRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            /*
            Engine engine1 = new Engine();
            Engine engine2 = new Engine();
            Car car1 = new Car(engine2);
            Car car2 = new Car(engine1);
            create(car1, sf);
            create(car2, sf);
             */
            Car car = get(Car.class, 1, sf);
            car.addDriver(new Driver());
            car.addDriver(new Driver());
            car.addDriver(new Driver());
            update(car, sf);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static <T> T create(T model, SessionFactory sf) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(model);
            session.getTransaction().commit();
        }
        return model;
    }

    public static <T> T update(T model, SessionFactory sf) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.update(model);
            session.getTransaction().commit();
        }
        return model;
    }

    public static <T> T get(Class<T> cl, int id, SessionFactory sf) {
        T result;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            result = session.get(cl, id);
            session.getTransaction().commit();
        }
        return result;
    }

    public static <T> List<T> getAll(Class<T> cl, SessionFactory sf) {
        List<T> result;
        try (Session session = sf.openSession()) {
            System.out.println(cl.getName());
            session.beginTransaction();
            result = session.createQuery("from " + cl.getName()).list();
            session.getTransaction().commit();
        }
        return result;
    }
}
