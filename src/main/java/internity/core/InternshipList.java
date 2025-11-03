package internity.core;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import internity.logic.commands.ListCommand;
import internity.storage.Storage;
import internity.ui.Ui;



/**
 * The {@code InternshipList} class manages a collection of {@link Internship}
 * objects representing internship applications.
 * <p>
 * It provides methods to add, delete, find, list, retrieve, sort and update internships.
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

    /**
     * Returns a new list of internships sorted by the specified order.
     * The original internship list is not modified.
     *
     * @param order the order type (ASCENDING, DESCENDING, or DEFAULT)
     * @return a new {@code ArrayList<Internship>} view sorted for display
     */
    public static List<Internship> sortInternships(ListCommand.OrderType order) {
        ArrayList<Internship> sortedList = new ArrayList<>(internshipList);

        if (order == ListCommand.OrderType.DESCENDING) {
            sortedList.sort(Comparator.comparing(Internship::getDeadline).reversed());
        } else if (order == ListCommand.OrderType.ASCENDING) {
            sortedList.sort(Comparator.comparing(Internship::getDeadline));
        }
        return sortedList;
    }

    /**
     * Lists internships in a formatted table.
     * Sorting, when requested, is applied only to a temporary copy for display.
     *
     * @param order the display order type
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

        // Pair each internship with its original index
        List<Map.Entry<Integer, Internship>> indexedList = new ArrayList<>();
        for (int i = 0; i < internshipList.size(); i++) {
            indexedList.add(Map.entry(i, internshipList.get(i)));
        }

        // Only sort if an order is specified
        if (order == ListCommand.OrderType.ASCENDING || order == ListCommand.OrderType.DESCENDING) {
            Comparator<Map.Entry<Integer, Internship>> comparator =
                    Comparator.comparing(entry -> entry.getValue().getDeadline());
            if (order == ListCommand.OrderType.DESCENDING) {
                comparator = comparator.reversed();
            }
            indexedList.sort(comparator);
        }

        // Print header
        String header = "Here are the internships in your list";
        if (order == ListCommand.OrderType.ASCENDING) {
            header += " (sorted by deadline ascending):";
        } else if (order == ListCommand.OrderType.DESCENDING) {
            header += " (sorted by deadline descending):";
        } else {
            header += " (in order added):";
        }
        Ui.printInternshipListHeader(header);

        // Display internships with their original indexes
        for (Map.Entry<Integer, Internship> entry : indexedList) {
            int originalIndex = entry.getKey();
            Internship internship = entry.getValue();
            Ui.printInternshipListContent(originalIndex, internship);
        }

        LOGGER.info("Finished listing internships. Total: " + indexedList.size());
        assert (indexedList.size() == size()) : "All internships should be listed";
    }


    // @@author {V1T0bh}
    private static boolean isEmpty() {
        return internshipList.isEmpty();
    }


    public static void updateStatus(int index, String newStatus) throws InternityException {
        if (index < 0 || index >= size()) {
            throw InternityException.invalidInternshipIndex();
        }
        final String normalized = Status.canonical(newStatus);
        Internship internship = internshipList.get(index);
        internship.setStatus(normalized);
    }

    public static void updateCompany(int index, String newCompany) throws InternityException {
        if (index < 0 || index >= size()) {
            throw InternityException.invalidInternshipIndex();
        }
        Internship it = internshipList.get(index);
        it.setCompany(newCompany);
    }

    public static void updateRole(int index, String newRole) throws InternityException {
        if (index < 0 || index >= size()) {
            throw InternityException.invalidInternshipIndex();
        }
        Internship it = internshipList.get(index);
        it.setRole(newRole);
    }

    public static void updateDeadline(int index, Date newDeadline) throws InternityException {
        if (index < 0 || index >= size()) {
            throw InternityException.invalidInternshipIndex();
        }
        Internship it = internshipList.get(index);
        it.setDeadline(newDeadline);
    }

    public static void updatePay(int index, int newPay) throws InternityException {
        if (index < 0 || index >= size()) {
            throw InternityException.invalidInternshipIndex();
        }
        Internship it = internshipList.get(index);
        it.setPay(newPay);
    }

    /**
     * Searches and prints internships that match the specified keyword in either the company name or the role.
     *
     * <p>
     * This method performs a case-insensitive search across all stored internships.
     * If no matches are found, a message is printed via {@link Ui#printNoInternshipFound()}.
     * Otherwise, the matching internships are displayed with their original
     * indices using {@link Ui#printInternshipListHeader(String)} and
     * {@link Ui#printInternshipListContent(int, Internship)}.
     * </p>
     *
     * @param keyword the search keyword to look for within the company or role fields
     */
    public static void findInternship(String keyword) {
        // Store matching internships and their original indices
        ArrayList<Integer> matchingIndices = new ArrayList<>();
        ArrayList<Internship> matchingInternships = new ArrayList<>();

        LOGGER.info("Searching for internships that match keyword.");
        for (int i = 0; i < internshipList.size(); i++) {
            Internship thisInternship = internshipList.get(i);
            if (thisInternship.getCompany().toLowerCase().contains(keyword.toLowerCase()) ||
                    thisInternship.getRole().toLowerCase().contains(keyword.toLowerCase())) {
                matchingIndices.add(i);
                matchingInternships.add(thisInternship);
            }
        }
        LOGGER.info("Search completed successfully.");

        if (matchingInternships.isEmpty()) {
            LOGGER.info("No matching internships were found.");
            Ui.printNoInternshipFound();
            return;
        }

        LOGGER.info("Matching internships found. Printing matching internships.");
        Ui.printInternshipListHeader("These are the matching internships in your list:");
        for (int i = 0; i < matchingInternships.size(); i++) {
            Ui.printInternshipListContent(matchingIndices.get(i), matchingInternships.get(i));
        }
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

    /**
     * Finds the internship with the nearest deadline.
     * <p>
     * Returns a {@link AbstractMap.SimpleEntry} containing the internship with the nearest deadline
     * and the count of other internships that share the same deadline. The method first searches for
     * internships with future deadlines (including today). If none exist, it returns
     * the most recent past deadline.
     * </p>
     * <p>
     * Assumes the internship list is non-empty.
     * </p>
     *
     * @return an {@link AbstractMap.SimpleEntry} where the key is the internship with the nearest deadline,
     *         and the value is the count of additional internships with the same deadline
     * @throws InternityException if an error occurs while accessing internship data
     */
    public static AbstractMap.SimpleEntry<Internship, Integer> findNearestDeadlineInternship()
            throws InternityException {
        LOGGER.info("Finding internship with nearest deadline.");
        assert InternshipList.size() > 0 : "Cannot find nearest deadline in empty list";

        Internship nearest = null;
        // no. of internships with same deadline as nearest
        int countSameDeadline = 0;

        // check if this internship has the nearest deadline (at least as near as the current nearest)
        boolean isNearestDeadline;
        //  check if the deadline is in the future (including today)
        boolean isDeadlineInFuture;
        // check if the deadline is the same as the current nearest deadline
        boolean isSameDeadline;

        // get the internship with the nearest deadline that is in the future
        for (int i = 0; i < InternshipList.size(); i++) {
            Internship internship = InternshipList.get(i);

            isNearestDeadline = (nearest == null)
                    || (internship.getDeadline().compareTo(nearest.getDeadline()) <= 0);
            isDeadlineInFuture = (Date.getToday().compareTo(internship.getDeadline()) <= 0);

            if (isNearestDeadline && isDeadlineInFuture) {
                isSameDeadline = (nearest != null)
                        && (internship.getDeadline().compareTo(nearest.getDeadline()) == 0);

                if (isSameDeadline) {
                    countSameDeadline += 1;
                } else {
                    nearest = internship;
                    countSameDeadline = 0;
                }
            }
        }

        // if no internships have future deadlines, get the nearest past deadline
        if (nearest == null) {
            LOGGER.fine("No internships with valid future deadlines found.");
            LOGGER.info("Finding past nearest deadline.");

            for (int i = 0; i < InternshipList.size(); i++) {
                Internship internship = InternshipList.get(i);

                isNearestDeadline = (nearest == null)
                        || (internship.getDeadline().compareTo(nearest.getDeadline()) >= 0);

                if (isNearestDeadline) {
                    isSameDeadline = (nearest != null)
                            && (internship.getDeadline().compareTo(nearest.getDeadline()) == 0);

                    if (isSameDeadline) {
                        countSameDeadline += 1;
                    } else {
                        nearest = internship;
                        countSameDeadline = 0;
                    }
                }
            }
        }

        LOGGER.fine("Found nearest deadline internship: " + nearest);
        LOGGER.fine("Found occurrence of nearest deadline: " + countSameDeadline);

        return new AbstractMap.SimpleEntry<>(nearest, countSameDeadline);
    }
}
