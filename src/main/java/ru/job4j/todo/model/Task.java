package ru.job4j.todo.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Модель данных "задача"
 *
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

    @ManyToOne
    @JoinColumn(name = "userLogin")
    private User user;

    @Column(updatable = false, nullable = false)
    private String description;

    @Column(updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(nullable = false)
    private boolean done;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Category> categories = new ArrayList<>();

    public Task() {
    }

    public Task(String userLogin, String description, Date created, boolean done) {
        this.user = new User(userLogin);
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getUserLogin() {
        return user.getLogin();
    }

    public void setUserLogin(String userLogin) {
        user.setLogin(userLogin);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void addCategory(Category category) {
        categories.add(category);
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
                && Objects.equals(user.getLogin(), task.getUserLogin())
                && Objects.equals(description, task.description)
                && Objects.equals(created.getTime(), task.created.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user.getLogin(), description, created, done);
    }

    @Override
    public String toString() {
        return "Task{"
                + "id=" + id
                + ", userLogin='" + user.getLogin() + '\''
                + ", desc='" + description + '\''
                + ", created=" + created
                + ", done=" + done
                + '}';
    }
}
