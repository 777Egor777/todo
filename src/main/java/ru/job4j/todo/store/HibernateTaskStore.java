package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * Hibernate-хранилище для задач.
 *
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 27.01.2021
 */
public class HibernateTaskStore implements TaskStore {
    /**
     * Создаём один раз объект SessionFactory
     * при помощи StandardServiceRegistry.
     */
    private final StandardServiceRegistry registry =
            new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    private HibernateTaskStore() {
    }

    /**
     * Шаблон синглотна "Holder"
     */
    private static final class Holder {
        private static final TaskStore INSTANCE = new HibernateTaskStore();
    }

    /**
     * @return объект Синглтона.
     */
    public static TaskStore instOf() {
        return Holder.INSTANCE;
    }

    /**
     * Этот метод нужен для тестов.
     * @return Объект класса для тестов.
     */
    public static TaskStore testInstance() {
        return new HibernateTaskStore();
    }

    /**
     * Здесь применяется шаблон "wrapper".
     *
     * @param func - функция, совершающая
     *               определённое действие
     *               с помощью переданной
     *               в качестве параметра сессии.
     * @param <T> - Generic тип возвращаемого
     *              значения.
     * @return Результат выполнения операции.
     */
    @SuppressWarnings("DuplicatedCode")
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

    /**
     * Метод извлекает из
     * хранилища задачу с
     * заданным id и
     * возвращает её.
     *
     * @param id - id задачи.
     * @return Задача с заданным
     *         id.
     */
    @Override
    public Task get(int id) {
        return tx(session -> session.get(Task.class, id));
    }

    /**
     * Метод добавляет
     * задачу в хранилище.
     *
     * @param task - добавляемая
     *               задача.
     * @return Добавляемая задача
     *         с присвоенным id.
     */
    @Override
    public Task add(Task task) {
        return tx(session -> {
            session.save(task);
            return task;
        });
    }

    /**
     * Метод обновляет
     * существующую задачу
     * в хранилище.
     *
     * @param task - обновляемая задача
     * @return Ту же задачу, что и получает
     *         в качестве параметра.
     */
    @Override
    public Task update(Task task) {
        return tx(session -> {
            session.update(task);
            return task;
        });
    }

    /**
     * Возвращает все задачи
     * конкретного пользователя.
     * (По его ключу в базе - логину).
     *
     * @param userLogin - логин пользователя.
     * @return Все задачи пользователя
     *         с данным логином.
     */
    @Override
    public List<Task> getAll(String userLogin) {
        return tx(session -> {
            Query query = session.createQuery(
                    "from ru.job4j.todo.model.Task where user = :paramUser");
            query.setParameter("paramUser", new User(userLogin));
            return query.list();
        });
    }

    /**
     * Возвращает все открытые задачи
     * конкретного пользователя.
     * (По его ключу в базе - логину).
     *
     * @param userLogin - логин пользователя.
     * @return Все открытые задачи пользователя
     *         с данным логином.
     */
    @Override
    public List<Task> getAllOpen(String userLogin) {
        return tx(session -> {
            Query query = session.createQuery(
                    "from ru.job4j.todo.model.Task where done = :paramDone and user = :paramUser");
            query.setParameter("paramDone", false);
            query.setParameter("paramUser", new User(userLogin));
            return query.list();
        });
    }

    /**
     * Метод возвращает все
     * категории, которые
     * могут быть у задач.
     *
     * @return Список всех категорий.
     */
    @Override
    public List<Category> getAllCategories() {
        return tx(session ->
                session.createQuery("from ru.job4j.todo.model.Category").list());
    }

    /**
     * Метод освобождает ресурсы.
     * Уничтожает StandardServiceRegistry,
     * а с ней и SessionFactory.
     */
    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
