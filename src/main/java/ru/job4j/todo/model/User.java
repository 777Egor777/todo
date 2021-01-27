package ru.job4j.todo.model;

import javax.persistence.*;

/**
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
    @Column(updatable = false, nullable = false)
    private String name;

    public User() {
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
}
