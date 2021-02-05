package ru.job4j.hql;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 04.02.2021
 */
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private boolean active;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LibBook> libBooks = new ArrayList<>();

    public void addBook(LibBook libBook) {
        libBooks.add(libBook);
    }

    public Account() {
    }

    public Account(int id) {
        this.id = id;
    }

    public Account(String username, boolean active) {
        this.username = username;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<LibBook> getLibBooks() {
        return libBooks;
    }

    public void setLibBooks(List<LibBook> libBooks) {
        this.libBooks = libBooks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Account account = (Account) o;

        if (id != account.id) {
            return false;
        }
        if (active != account.active) {
            return false;
        }
        if (!Objects.equals(username, account.username)) {
            return false;
        }
        return Objects.equals(libBooks, account.libBooks);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (libBooks != null ? libBooks.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Account.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("username='" + username + "'")
                .add("active=" + active)
                .add("books=" + libBooks)
                .toString();
    }
}
