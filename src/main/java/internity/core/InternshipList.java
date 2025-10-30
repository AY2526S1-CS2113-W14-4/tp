package internity.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import internity.logic.commands.ListCommand;
import internity.storage.Storage;
import internity.ui.Ui;

/**
 * The {@code InternshipList} class manages a collection of {@link Internship}
 * objects representing internship applications.
 * <p>
 * It provides methods to add, delete, retrieve, list, sort, and search internships.
 * The class also handles persistence by loading from and saving to storage.
 * </p>
 */
public class InternshipList {
    private static final Logger LOGGER = Logger.getLogger(InternshipList.class.getName());
    private static final ArrayList<Internship> internshipList = new ArrayList<>();
    private static Storage storage;
    private static String username;

    private InternshipList() {
    }

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
        internshipList.clear();
        internshipList.addAll(loadedInternships);
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
        storage.save(internshipList);
    }

    // @@author {V1T0bh}
    /**
     * Adds a new {@link Internship} to the {@code ArrayList} of internships.
     *
     * <p>
     * This method appends the specified {@code Internship} object to the
     * internal list that stores all internship applications.
     * </p>
     *
     * @param item the {@code Internship} object to be added to the list
     */
    public static void add(Internship item) {
        LOGGER.info("Adding new internship to the ArrayList");
        internshipList.add(item);
        LOGGER.info("New internship has been added successfully.");
    }

    /**
     * Deletes an {@link Internship} from the {@code ArrayList} based on the given index.
     *
     * <p>
     * This method removes the {@code Internship} object located at the specified
     * index from the internal list of internships. If the index is invalid,
     * an {@code InternityException} is thrown.
     * </p>
     *
     * @param index the index of the {@code Internship} to be deleted
     * @throws InternityException if the provided index is out of bounds
     */
    public static void delete(int index) throws InternityException {
        if (index < 0 || index >= internshipList.size()) {
            throw new InternityException("Invalid internship index: " + (index + 1));
        }
        internshipList.remove(index);
    }

    /**
     * Deletes an {@link Internship} from the {@code ArrayList} based on the given index.
     *
     * <p>
     * This method removes the {@code Internship} object located at the specified
     * index from the internal list of internships. If the index is invalid,
     * an {@code InternityException} is thrown.
     * </p>
     *
     * @param index the index of the {@code Internship} to be deleted
     * @throws InternityException if the provided index is out of bounds
     */
    public static Internship get(int index) throws InternityException {
        if (index < 0 || index >= internshipList.size()) {
            throw new InternityException("Invalid internship index: " + (index + 1));
        }
        return internshipList.get(index);
    }

    public static int size() {
        return internshipList.size();
    }

    // @@author {V1T0bh}
    /**
     * Sort internships by deadline in the specified order.
     *
     * @param order the order type (ASCENDING or DESCENDING)
     */
    public static void sortInternships(ListCommand.OrderType order) {
        if (order == ListCommand.OrderType.DESCENDING) {
            internshipList.sort(Comparator.comparing(Internship::getDeadline).reversed());
        } else if (order == ListCommand.OrderType.ASCENDING) {
            internshipList.sort(Comparator.comparing(Internship::getDeadline));
        }
    }

    // @@author {V1T0bh}
    /**
     * Lists all internships in a formatted table.
     *
     * @param order the order type for sorting the internships
     * @throws InternityException if there is an error during listing
     */
    public static void listAll(ListCommand.OrderType order) throws InternityException {
        LOGGER.info("Listing all internships");

        if (InternshipList.isEmpty()) {
            LOGGER.warning("No internships found to list");
            Ui.printInternshipListEmpty();
            assert (size() == 0) : "Internship list should be empty";
            return;
        }
        assert (size() > 0) : "Internship list should not be empty";

        sortInternships(order);

        Ui.printInternshipListHeader("Here are the internships in your list:");
        int i;
        for (i = 0; i < InternshipList.size(); i++) {
            Internship internship = InternshipList.get(i);
            LOGGER.fine("Listing internship at index: " + i);
            Ui.printInternshipListContent(i, internship);
        }
        LOGGER.info("Finished listing internships. Total: " + i);
        assert (i == size()) : "All internships should be listed";
    }

    // @@author {V1T0bh}
    private static boolean isEmpty() {
        return internshipList.isEmpty();
    }


    public static void updateStatus(int index, String newStatus) throws InternityException {
        if (index < 0 || index >= size()) {
            throw InternityException.invalidInternshipIndex();
        }
        if (newStatus == null || !Status.isValid(newStatus)) {
            throw InternityException.invalidStatus(String.valueOf(newStatus));
        }
        final String normalized = Status.canonical(newStatus);
        Internship internship = internshipList.get(index);
        internship.setStatus(normalized);
        System.out.println("Updated internship " + (index + 1) + " status to: " + normalized);
    }

    public static void updateCompany(int index, String newCompany) throws InternityException {
        if (index < 0 || index >= size()) {
            throw InternityException.invalidInternshipIndex();
        }
        Internship it = internshipList.get(index);
        it.setCompany(newCompany);
        System.out.println("Updated internship " + (index + 1) + " company to: " + newCompany);
    }

    public static void updateRole(int index, String newRole) throws InternityException {
        if (index < 0 || index >= size()) {
            throw InternityException.invalidInternshipIndex();
        }
        Internship it = internshipList.get(index);
        it.setRole(newRole);
        System.out.println("Updated internship " + (index + 1) + " role to: " + newRole);
    }

    public static void updateDeadline(int index, Date newDeadline) throws InternityException {
        if (index < 0 || index >= size()) {
            throw InternityException.invalidInternshipIndex();
        }
        Internship it = internshipList.get(index);
        it.setDeadline(newDeadline);
        System.out.println("Updated internship " + (index + 1) + " deadline to: " + newDeadline);
    }

    public static void updatePay(int index, int newPay) throws InternityException {
        if (index < 0 || index >= size()) {
            throw InternityException.invalidInternshipIndex();
        }
        Internship it = internshipList.get(index);
        it.setPay(newPay);
        System.out.println("Updated internship " + (index + 1) + " pay to: " + newPay);
    }

    /**
     * Searches for internships that match the specified keyword in either the company name or the role.
     *
     * <p>
     * This method performs a case-insensitive search across all stored internships by filtering those whose
     * company name or role contains the given keyword. If no matches are found, a message is printed to
     * indicate that no internships match the criteria. Otherwise, the matching internships are displayed
     * through the {@link Ui#printFindInternship(ArrayList)} method.
     * </p>
     *
     * @param keyword the search keyword to look for within the company or role fields
     */
    public static void findInternship(String keyword) {
        LOGGER.info("Searching for internships that match keyword.");
        ArrayList<Internship> matchingInternships = internshipList.stream()
                .filter(internship ->
                        internship.getCompany().toLowerCase().contains(keyword.toLowerCase()) ||
                                internship.getRole().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toCollection(ArrayList::new)
                );
        LOGGER.info("Search completed successfully.");

        if (matchingInternships.isEmpty()) {
            LOGGER.info("No matching internships were found.");
            System.out.println("No internships with this company or role found.");
            return;
        }

        LOGGER.info("Matching internships found.");
        LOGGER.info("Printing matching internships.");
        Ui.printFindInternship(matchingInternships);
        LOGGER.info("Matching internships printed successfully.");
    }

    public static void clear() {
        internshipList.clear();
    }

    public static void setUsername(String username) {
        InternshipList.username = username;
    }

    public static String getUsername() {
        return username;
    }
}
