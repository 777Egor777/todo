package ru.job4j.todo.store;

import org.junit.Test;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class HibernateTaskStoreTest {

    @Test
    public void get() throws Exception {
        try (TaskStore tasks = HibernateTaskStore.testInstance();
             UserStore users = HibernateUserStore.testInstance()) {
            User user = new User("egor", "123", "egor");
            users.add(user);
            Task task = new Task("egor", "Make README.md to TODO list project",
                                 new Date(), false);
            task = tasks.add(task);
            assertThat(tasks.get(task.getId()), is(task));
        }
    }

    @Test
    public void update() throws Exception {
        try (TaskStore tasks = HibernateTaskStore.testInstance();
             UserStore users = HibernateUserStore.testInstance()) {
            User user = new User("egor", "123", "egor");
            users.add(user);
            Task task = new Task("egor", "Make README.md to TODO list project",
                    new Date(), false);
            task = tasks.add(task);
            task.setDone(true);
            task= tasks.update(task);
            assertThat(tasks.get(task.getId()), is(task));
        }
    }

    @Test
    public void getAll() throws Exception {
        try (TaskStore tasks = HibernateTaskStore.testInstance();
             UserStore users = HibernateUserStore.testInstance()) {
            User user = new User("egor", "123", "egor");
            users.add(user);
            Task task = new Task("egor", "Make README.md to TODO list project",
                    new Date(), false);
            task = tasks.add(task);
            Task task2 = new Task("egor", "Buy cheese",
                    new Date(), false);
            task2 = tasks.add(task2);
            List<Task> listOfTasks = List.of(task, task2);
            assertThat(tasks.getAll("egor"), is(listOfTasks));
        }
    }

    @Test
    public void getAllOpen() throws Exception {
        try (TaskStore tasks = HibernateTaskStore.testInstance();
             UserStore users = HibernateUserStore.testInstance()) {
            User user = new User("egor", "123", "egor");
            users.add(user);
            Task task = new Task("egor", "Make README.md to TODO list project",
                    new Date(), false);
            task = tasks.add(task);
            Task task2 = new Task("egor", "Buy cheese",
                    new Date(), false);
            task2 = tasks.add(task2);
            Task task3 = new Task("egor", "GYM monday",
                    new Date(), true);
            tasks.add(task3);
            List<Task> listOfTasks = List.of(task, task2);
            assertThat(tasks.getAllOpen("egor"), is(listOfTasks));
        }
    }
}