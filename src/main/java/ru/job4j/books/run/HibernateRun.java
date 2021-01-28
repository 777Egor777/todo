package ru.job4j.books.run;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.books.model.Author;
import ru.job4j.books.model.Book;
import ru.job4j.car.model.Brand;
import ru.job4j.car.model.Model;

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
            Author author1 = new Author("auth1");
            Book book1 = new Book("book1");
            Book book2 = new Book("book1");
            Book book3 = new Book("book1");
            book1 = create(book1, sf);
            book2 = create(book2, sf);
            book3 = create(book3, sf);
            author1.addBook(book1);
            author1.addBook(book2);
            Author author2 = new Author("auth2");
            author2.addBook(book1);
            author2.addBook(book3);
            author1 = create(author1, sf);
            author2 = create(author2, sf);
            delete(author1, sf);
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

    public static <T> void delete(T model, SessionFactory sf) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.delete(model);
            session.getTransaction().commit();
        }
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
