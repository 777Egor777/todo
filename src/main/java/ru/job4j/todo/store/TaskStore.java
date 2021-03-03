package ru.job4j.todo.store;

import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;

import java.util.List;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 27.01.2021
 */
public interface TaskStore extends AutoCloseable {
    Task get(int id);
    Task add(Task task);
    Task update(Task task);
    List<Task> getAll(String userLogin);
    List<Task> getAllOpen(String userLogin);
    List<Category> getAllCategories();
}
