package ru.job4j.candidate;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 04.02.2021
 */
public class HbmRun {
    public static void main(String[] args) {
        VacancyBase base = new VacancyBase();
        try (StdHbmStore store = new StdHbmStore()) {
            Vacancy vac1 = store.create(new Vacancy("Java Trainee"));
            Vacancy vac2 = store.create(new Vacancy("Java Junior"));
            Vacancy vac3 = store.create(new Vacancy("Java Middle"));
            Vacancy vac4 = store.create(new Vacancy("Java Senior"));
            Vacancy vac5 = store.create(new Vacancy("Java Architecture"));
            Vacancy vac6 = store.create(new Vacancy("Java TechLead"));

            base.addVacancy(vac1);
            base.addVacancy(vac2);
            base.addVacancy(vac3);
            base.addVacancy(vac4);
            base.addVacancy(vac5);
            base.addVacancy(vac6);

            base = store.create(base);
        }

        try (CandidateHbmStore store = new CandidateHbmStore()) {
            Candidate c = store.getById(1);
            c.setBase(base);
            store.update(c);
            System.out.println(store.getAll());
        }
    }
}
