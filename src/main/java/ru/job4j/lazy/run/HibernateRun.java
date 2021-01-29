package ru.job4j.lazy.run;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.lazy.model.CarBrand;
import ru.job4j.lazy.model.CarModel;

import java.util.List;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 29.01.2021
 */
public class HibernateRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
                CarBrand lada = create(new CarBrand("LADA"), sf);
                CarModel carModel2010 = new CarModel("2010", lada);
                CarModel carModelNiva = new CarModel("Niva", lada);
                create(carModel2010, sf);
                create(carModelNiva, sf);
                CarBrand bmw = create(new CarBrand("BMW"), sf);
                CarModel modelx5 = new CarModel("X5", bmw);
                CarModel modelx6 = new CarModel("X6", bmw);
                create(modelx5, sf);
                create(modelx6, sf);
                print(sf);

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

    public static void print(SessionFactory sf) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            List<CarBrand> list = session.createQuery("from ru.job4j.lazy.model.CarBrand").list();
            for (CarBrand brand : list) {
                for (CarModel model : brand.getCarModels()) {
                    System.out.println(model);
                }
            }
            session.getTransaction().commit();
        }
    }
}
