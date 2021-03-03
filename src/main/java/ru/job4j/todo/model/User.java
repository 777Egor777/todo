package ru.job4j.todo.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Модель данных "пользователь"
 *
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 25.01.2021
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(updatable = false, nullable = false)
    private String login;
    @Column(updatable = false, nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;

    public User() {
    }

    public User(String login) {
        this.login = login;
    }

    public User(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (!Objects.equals(login, user.login)) {
            return false;
        }
        if (!Objects.equals(password, user.password)) {
            return false;
        }

        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("login='" + login + "'")
                .add("password='" + password + "'")
                .add("name='" + name + "'")
                .toString();
    }
}
