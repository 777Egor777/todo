package ru.job4j.lazy.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 29.01.2021
 */
@Entity
@Table(name = "car_model")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(updatable = false, nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "car_brand_id")
    private CarBrand carBrand;

    public CarModel() {
    }

    public CarModel(String name, CarBrand carBrand) {
        this.name = name;
        this.carBrand = carBrand;
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

    public CarBrand getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(CarBrand carBrand) {
        this.carBrand = carBrand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CarModel carModel = (CarModel) o;

        if (id != carModel.id) {
            return false;
        }
        if (!Objects.equals(name, carModel.name)) {
            return false;
        }
        return Objects.equals(carBrand, carModel.carBrand);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (carBrand != null ? carBrand.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CarModel.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("brand=" + carBrand.getName())
                .toString();
    }
}
