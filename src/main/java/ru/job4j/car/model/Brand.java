package ru.job4j.car.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 28.01.2021
 */
@Entity
@Table(name = "brand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(updatable = false, nullable = false)
    private String name;
    @OneToMany(cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<Model> models = new ArrayList<>();

    public Brand() {
    }

    public Brand(String name) {
        this.name = name;
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

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }

    public void addModel(Model model) {
        this.models.add(model);
    }
}
