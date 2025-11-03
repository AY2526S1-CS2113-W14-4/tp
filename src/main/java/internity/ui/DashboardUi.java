package internity.ui;

import java.util.*;
import java.util.logging.Logger;

import internity.core.InternityException;
import internity.core.Internship;
import internity.core.InternshipList;

/**
 * Provides a command-line dashboard interface for the Internity application.
 * <p>
 * This class contains static methods to display key information about the current
 * user's internship tracking status, including:
 * <ul>
 *     <li>Username</li>
 *     <li>Total number of internships</li>
 *     <li>Nearest internship deadline</li>
 *     <li>Status breakdown of internships</li>
 * </ul>
 * </p>
 */
public class DashboardUi {
    private static final Logger logger = Logger.getLogger(DashboardUi.class.getName());
    private static final String INDENT = "  ";

    /**
     * Prints the complete dashboard to the console.
     * <p>
     * The dashboard includes:
     * <ul>
     *     <li>Current user's username</li>
     *     <li>Total internships</li>
     *     <li>Nearest internship deadline</li>
     *     <li>Status overview of all internships</li>
     * </ul>
     * </p>
     */
    public static void printDashboard() throws InternityException {
        printUser();
        printInternshipCount();
        printNearestDeadline();
        printStatusOverview();
    }

    /**
     * Prints the current user's username to the console.
     * <p>
     * If no username is set, displays {@code Guest} as the default.
     * </p>
     */
    public static void printUser() {
        String user = InternshipList.getUsername();
        System.out.println("User: " + (user != null ? user : "Guest"));
        assert user != null : "Username should not be null";
        logger.fine("Displayed user: " + user);
    }

    /**
     * Prints the total number of internships currently tracked.
     */
    public static void printInternshipCount() {
        int count = InternshipList.size();
        System.out.println("Total Internships: " + count);
        assert count >= 0 : "Internship count should never be negative";
        logger.fine("Total internships: " + count);
    }

    /**
     * Prints a breakdown of internships by their application status.
     * <p>
     * Statuses are displayed in the following fixed order:
     * {@code Pending, Interested, Applied, Interviewing, Offer, Accepted, Rejected}.
     * </p>
     *
     * @throws InternityException if an error occurs while accessing internship data
     */
    public static void printStatusOverview() throws InternityException {
        if (InternshipList.size() == 0) {
            System.out.println("\nStatus Overview: No internships found.");
            logger.warning("Status overview requested but internship list is empty");
            return;
        }

        List<String> statusOrder = List.of(
                "Pending", "Interested", "Applied", "Interviewing",
                "Offer", "Accepted", "Rejected"
        );

        Map<String, Integer> statusCount = new LinkedHashMap<>();

        for (String status : statusOrder) {
            statusCount.put(status, 0);
        }

        for (int i = 0; i < InternshipList.size(); i++) {
            Internship internship;
            internship = InternshipList.get(i);

            String status = internship.getStatus();
            if (statusCount.containsKey(status)) {
                statusCount.put(status, statusCount.get(status) + 1);
            }
        }

        System.out.println("\nStatus Overview:");
        for (String status : statusOrder) {
            System.out.printf("%s%-15s : %d%n", INDENT, status, statusCount.get(status));
        }
        logger.fine("Status overview printed");
    }

    /**
     * Prints the internship with the nearest upcoming deadline.
     * <p>
     * If there are no internships, or none with valid deadlines, prints an appropriate message.
     * </p>
     */
    public static void printNearestDeadline() throws InternityException {
        if (InternshipList.size() == 0) {
            System.out.println("\nNearest Deadline: No internships found.");
            logger.warning("Nearest deadline requested but internship list is empty");
            return;
        }

        AbstractMap.SimpleEntry<Internship, Integer> nearestResult = InternshipList.findNearestDeadlineInternship();
        Internship nearest = nearestResult.getKey();
        int countNearest = nearestResult.getValue();

        if (nearest == null) {
            System.out.println("\nNearest Deadline: No valid deadlines found.");
            return;
        }

        boolean isDeadlineInPast = nearest.getDeadline().compareTo(internity.core.Date.getToday()) < 0;

        System.out.println("\nNearest Deadline:");
        System.out.printf("  %s | %s @ %s %s%n",
                nearest.getDeadline().toString(),
                nearest.getRole(),
                nearest.getCompany(),
                (isDeadlineInPast)? "(OVERDUE!)" : "");
        if (countNearest > 0) {
            System.out.printf(INDENT + "(Found %d other internship(s) with the same deadline)%n", countNearest);
        }

        logger.fine("Nearest deadline displayed: " + nearest);
    }

}
