package ru.job4j.candidate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 05.02.2021
 */
@Entity
@Table(name = "vacancy_base")
public class VacancyBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vacancy> vacancies = new ArrayList<>();

    public void addVacancy(Vacancy vacancy) {
        vacancies.add(vacancy);
    }

    public VacancyBase() {
    }

    public VacancyBase(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Vacancy> getVacancies() {
        return vacancies;
    }

    public void setVacancies(List<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VacancyBase that = (VacancyBase) o;

        if (id != that.id) {
            return false;
        }
        return Objects.equals(vacancies, that.vacancies);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (vacancies != null ? vacancies.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", VacancyBase.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("vacancies=" + vacancies)
                .toString();
    }
}
