package ru.job4j.todo.store;

import ru.job4j.todo.model.User;

/**
 * Интерфейс для хранилища пользователей.
 *
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 27.01.2021
 */
public interface UserStore extends AutoCloseable {
    User add(User user);
    User update(User user);
    User get(String login);
}
