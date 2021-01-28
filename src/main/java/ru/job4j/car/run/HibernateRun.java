package ru.job4j.car.run;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.car.model.Brand;
import ru.job4j.car.model.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 28.01.2021
 */
public class HibernateRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Brand brand = create(new Brand("LADA"), sf);
            Model model2010 = create(new Model("2010"), sf);
            Model model2011 = create(new Model("2011"), sf);
            Model model2012 = create(new Model("2012"), sf);
            Model model2013 = create(new Model("2013"), sf);
            Model model2014 = create(new Model("2014"), sf);
            brand.addModel(model2010);
            brand.addModel(model2011);
            brand.addModel(model2012);
            brand.addModel(model2013);
            brand.addModel(model2014);
            update(brand, sf);
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
}
