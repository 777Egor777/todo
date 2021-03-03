package ru.job4j.todo.store;

import org.junit.Test;
import ru.job4j.todo.model.User;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class HibernateUserStoreTest {

    @Test
    public void add() throws Exception {
        try(UserStore store = HibernateUserStore.testInstance()) {
            User user = new User("test", "test", "test");
            user = store.add(user);
            assertThat(store.get("test"), is(user));
        }
    }

    @Test
    public void update() throws Exception {
        try(UserStore store = HibernateUserStore.testInstance()) {
            User user = new User("test", "test", "test");
            user = store.add(user);
            user.setName("egor");
            user = store.update(user);
            assertThat(store.get("test"), is(user));
        }
    }
}