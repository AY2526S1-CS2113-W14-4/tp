package internity.core;

import internity.ui.Ui;

import java.util.ArrayList;

import java.util.logging.Logger;

public class InternshipList {
    private static final Logger logger = Logger.getLogger(InternshipList.class.getName());
    private static final ArrayList<Internship> List = new ArrayList<>();
    private static Storage storage;

    private InternshipList() {}

    /**
     * Sets the storage instance for auto-saving.
     *
     * @param storageInstance The storage instance to use for persistence.
     */
    public static void setStorage(Storage storageInstance) {
        storage = storageInstance;
    }

    /**
     * Loads internships from storage.
     *
     * @throws InternityException If there is an error loading from storage.
     */
    public static void loadFromStorage() throws InternityException {
        if (storage == null) {
            return;
        }
        ArrayList<Internship> loadedInternships = storage.load();
        List.clear();
        List.addAll(loadedInternships);
    }

    /**
     * Saves internships to storage.
     *
     * @throws InternityException If there is an error saving to storage.
     */
    public static void saveToStorage() throws InternityException {
        if (storage == null) {
            return;
        }
        storage.save(List);
    }

    public static void add(Internship item) {
        List.add(item);
    }

    public static void delete(int index) throws InternityException {
        if (index < 0 || index >= List.size()) {
            throw new InternityException("Invalid internship index: " + (index + 1));
        }
        List.remove(index);
    }

    public static Internship get(int index) throws InternityException {
        if (index < 0 || index >= List.size()) {
            throw new InternityException("Invalid internship index: " + (index + 1));
        }
        return List.get(index);
    }

    public static int size() {
        return List.size();
    }

    // list all
    public static void listAll() throws InternityException {
        logger.info("Listing all internships");

        if (InternshipList.isEmpty()) {
            logger.warning("No internships found to list");
            System.out.println("No internships found. Please add an internship first.");
            assert (size() == 0) : "Internship list should be empty";
            return;
        }

        assert (size() > 0) : "Internship list should not be empty";
        System.out.printf("%-5s %-15s %-15s %-15s %-10s %-10s%n",
                "No.", "Company", "Role", "Deadline", "Pay", "Status");
        Ui.printHorizontalLine();
        int i;
        for (i = 0; i < InternshipList.size(); i++) {
            Internship internship = InternshipList.get(i);
            logger.fine("Listing internship at index: " + i);
            System.out.printf("%-5d %-15s %-15s %-15s %-10d %-10s%n",
                    i + 1,
                    internship.getCompany(),
                    internship.getRole(),
                    internship.getDeadline().toString(),
                    internship.getPay(),
                    internship.getStatus()
            );
        }
        logger.info("Finished listing internships. Total: " + i);
        assert (i == size()) : "All internships should be listed";
    }

    private static boolean isEmpty() {
        return List.isEmpty();
    }


    public static void updateStatus(int index, String newStatus) throws InternityException {
        if (index < 0 || index >= List.size()) {
            throw InternityException.invalidInternshipIndex();
        }
        Internship internship = List.get(index);
        internship.setStatus(newStatus);
        System.out.println("Updated internship " + (index + 1) + " status to: " + newStatus);
    }

    public static void clear() {
        List.clear();
    }
}
