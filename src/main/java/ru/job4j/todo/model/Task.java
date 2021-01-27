package ru.job4j.todo.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 27.01.2021
 */
@Entity
@Table(name = "items")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(updatable = false, nullable = false)
    private String userLogin;
    @Column(updatable = false, nullable = false)
    private String description;
    @Column(updatable = false, nullable = false)
    private Timestamp created;
    @Column(nullable = false)
    private boolean done;

    public Task() {
    }

    public Task(String userLogin, String description, Timestamp created, boolean done) {
        this.userLogin = userLogin;
        this.description = description;
        this.created = created;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return id == task.id
                && done == task.done
                && Objects.equals(userLogin, task.userLogin)
                && Objects.equals(description, task.description)
                && Objects.equals(created, task.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userLogin, description, created, done);
    }

    @Override
    public String toString() {
        return "Task{"
                + "id=" + id
                + ", userLogin='" + userLogin + '\''
                + ", desc='" + description + '\''
                + ", created=" + created
                + ", done=" + done
                + '}';
    }
}
