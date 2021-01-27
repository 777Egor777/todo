package ru.job4j.todo.store;

import ru.job4j.todo.model.User;

public interface UserStore {
    User add(User user);
    User update(User user);
    User get(String login);
}
