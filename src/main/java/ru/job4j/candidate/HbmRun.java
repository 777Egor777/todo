package ru.job4j.candidate;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 04.02.2021
 */
public class HbmRun {
    public static void main(String[] args) {
        try (CandidateHbmStore store = new CandidateHbmStore()) {
            store.add(new Candidate("Egor", 1, 100));
            store.add(new Candidate("Ivan", 4, 200));
            store.add(new Candidate("Egor", 10, 500));
            System.out.println("All: " + System.lineSeparator() + store.getAll());
            System.out.println("With id 1: " + System.lineSeparator() + store.getById(1));
            System.out.println("With name Egor" + System.lineSeparator() + store.getByName("Egor"));
            Candidate c = new Candidate("Egor", 2, 150);
            c.setId(1);
            store.update(c);
            System.out.println("Update with id=1");
            System.out.println(store.getAll());
            System.out.println("Delete with id=3");
            store.delete(new Candidate(3));
            System.out.println(store.getAll());

        }
    }
}
