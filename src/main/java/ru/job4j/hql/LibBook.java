package ru.job4j.hql;

import javax.persistence.*;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 04.02.2021
 */
@Entity
@Table(name = "books")
public class LibBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String publishingHouse;

    public LibBook() {
    }

    public LibBook(int id) {
        this.id = id;
    }

    public LibBook(String name, String publishingHouse) {
        this.name = name;
        this.publishingHouse = publishingHouse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LibBook libBook = (LibBook) o;

        if (id != libBook.id) {
            return false;
        }
        if (!Objects.equals(name, libBook.name)) {
            return false;
        }
        return Objects.equals(publishingHouse, libBook.publishingHouse);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (publishingHouse != null ? publishingHouse.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", LibBook.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("publishingHouse='" + publishingHouse + "'")
                .toString();
    }
}
