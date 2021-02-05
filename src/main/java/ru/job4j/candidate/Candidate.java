package ru.job4j.candidate;

import javax.persistence.*;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 04.02.2021
 */
@Entity
@Table(name = "candidate")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int experience;
    private int salary;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private VacancyBase base;

    public Candidate() {
    }

    public Candidate(int id) {
        this.id = id;
    }

    public Candidate(String name, int experience, int salary) {
        this.name = name;
        this.experience = experience;
        this.salary = salary;
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

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public VacancyBase getBase() {
        return base;
    }

    public void setBase(VacancyBase base) {
        this.base = base;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Candidate candidate = (Candidate) o;

        if (id != candidate.id) {
            return false;
        }
        if (experience != candidate.experience) {
            return false;
        }
        if (salary != candidate.salary) {
            return false;
        }
        if (!Objects.equals(name, candidate.name)) {
            return false;
        }
        return Objects.equals(base, candidate.base);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + experience;
        result = 31 * result + salary;
        result = 31 * result + (base != null ? base.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Candidate.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("experience=" + experience)
                .add("salary=" + salary)
                .add("base=" + base)
                .toString();
    }
}
