package internity.ui;

import internity.core.Internship;

import java.util.ArrayList;

/**
 * The {@code Ui} class provides methods for interacting with the user in the Internity chatbot.
 * <p>
 * It handles all user-facing output such as greetings, status updates, and responses to user actions
 * (e.g. adding, removing, or finding internships). This class is designed to centralise
 * all print-related functionality for consistent formatting and easy maintenance.
 * </p>
 */
public class Ui {
    public static final int INDEX_MAXLEN = 5;
    public static final int COMPANY_MAXLEN = 15;
    public static final int ROLE_MAXLEN = 30;
    public static final int DEADLINE_MAXLEN = 15;
    public static final int PAY_MAXLEN = 10;
    public static final int STATUS_MAXLEN = 10;

    static final String FORMAT_HEADER = "%" + INDEX_MAXLEN + "s %-" + COMPANY_MAXLEN + "s %-" + ROLE_MAXLEN
            + "s %-" + DEADLINE_MAXLEN + "s %-" + PAY_MAXLEN + "s %-" + STATUS_MAXLEN + "s%n";
    static final String FORMAT_CONTENT = "%" + INDEX_MAXLEN + "d %-" + COMPANY_MAXLEN + "s %-" + ROLE_MAXLEN
            + "s %-" + DEADLINE_MAXLEN + "s %-" + PAY_MAXLEN + "d %-" + STATUS_MAXLEN + "s%n";

    /** Horizontal line used to visually separate sections in the console output. */
    static final String LINE = "____________________________________________________" +
            "__________________________________________\n";

    /**
     * Prints a horizontal divider line to the console.
     */
    public static void printHorizontalLine() {
        System.out.print(LINE);
    }

    /**
     * Prints the welcome message and ASCII logo for the Internity chatbot.
     */
    public static void printWelcomeMessage() {
        final String logo = " ___       _                  _ _\n" +
                "|_ _|_ __ | |_ ___ _ __ _ __ (_) |_ _   _\n" +
                " | || '_ \\| __/ _ \\ '__| '_ \\| | __| | | |\n" +
                " | || | | | ||  __/ |  | | | | | |_| |_| |\n" +
                "|___|_| |_|\\__\\___|_|  |_| |_|_|\\__|\\__, |\n" +
                "                                    |___/";
        System.out.println("Hello, welcome to\n" + logo);
        System.out.println("Be on top of your internships management with the Internity chatbot!");
    }

    /**
     * Prints a personalised greeting message to the user.
     *
     * @param input the username entered by the user
     */
    public static void printGreeting(String input) {
        System.out.println("Hello, " + input + "!");
    }

    /**
     * Prints a farewell message when the user exits the chatbot.
     */
    public static void printExit() {
        System.out.println("Thank you for using Internity! Goodbye!");
    }

    /**
     * Prints a confirmation message after successfully adding an internship.
     *
     * <p>
     * This method displays a message acknowledging that a new internship has been added,
     * followed by the internship's details, which are obtained by invoking the
     * {@link Internship#toString()} method of the given {@code Internship} object.
     * </p>
     *
     * @param internship the internship that was added
     */
    public static void printAddInternship(Internship internship) {
        System.out.println("Noted. I've added this internship:");
        System.out.println(internship.toString());
    }

    public static void printRemoveInternship(String item, int totalItems) {
        System.out.println("Noted. I've removed this internship:");
        System.out.println("  " + item);
        System.out.println("Now you have " + totalItems + " internship(s) in the list.");
    }

    public static void printUpdateInternship() {
        System.out.println("Internship status updated successfully!");
    }

    public static void printInternshipListEmpty() {
        System.out.println("Your internship list is currently empty.");
    }

    /**
     * Prints the header for the internship list with a custom message.
     *
     * <p>
     * This method displays a provided message followed by a formatted header row
     * for the internship list. The header includes columns for index, company,
     * role, deadline, pay, and status, with appropriate spacing for readability.
     * </p>
     *
     * @param message the custom message to display before the header
     */
    public static void printInternshipListHeader(String message) {
        System.out.println(message);
        Ui.printHorizontalLine();
        System.out.printf(FORMAT_HEADER,
                "No.", "Company", "Role", "Deadline", "Pay", "Status");
        Ui.printHorizontalLine();
    }

    /**
     * Prints the details of a single internship in a formatted manner.
     *
     * <p>
     * This method displays the details of the given {@link Internship} object
     * in a tabular format, including its index, company name, role, deadline,
     * pay, and status. The details are aligned according to predefined column widths
     * for consistent presentation.
     * </p>
     *
     * @param index      the index of the internship in the list (0-based)
     * @param internship the {@code Internship} object whose details are to be printed
     */
    public static void printInternshipListContent(int index, Internship internship) {
        System.out.printf(FORMAT_CONTENT,
                index + 1,
                internship.getCompany(),
                internship.getRole(),
                internship.getDeadline().toString(),
                internship.getPay(),
                internship.getStatus()
        );
    }

    /**
     * Prints a formatted list of internships that match the user's search keyword.
     *
     * <p>
     * This method first prints a header row and horizontal lines for clear table formatting.
     * It then iterates through the provided list of {@link Internship} objects, displaying each
     * internship's details in a tabular format. The internship details are obtained via the
     * {@link Internship#getCompany()}, {@link Internship#getRole()},
     * {@link Internship#getDeadline()}, {@link Internship#getPay()}
     * and {@link Internship#getStatus()} methods.
     * </p>
     *
     * @param list the list of matching {@code Internship} objects to display
     */
    public static void printFindInternship(ArrayList<Internship> list) {
        printInternshipListHeader("These are the matching internships in your list:");

        int i;
        for (i = 0; i < list.size(); i++) {
            Internship internship = list.get(i);
            printInternshipListContent(i, internship);
        }
    }

    public static void printAskUsername() {
        System.out.println("What is your name?");
    }

    public static void printSetUsername(String username) {
        System.out.println("Username set to " + username);
    }

    public static void printHelp() {
        String commandList = """
                Here are the available commands:

                  - add       : Add a new internship application with company, role, deadline, and pay.
                  - delete    : Remove an internship application at the specified index.
                  - list      : Display all internship applications, optionally sorted by deadline.
                  - find      : Search and list internship applications matching a keyword.
                  - update    : Update the status of an internship application at the specified index.
                  - username  : Set your username for personalised greetings.
                  - dashboard : View statistics about your internship applications.
                  - help      : Display this list again. Your guide to managing internships.
                  - exit      : Terminate this session. Your progress will be saved.

                For verbose instructions, refer to the user guide.
                """;
        System.out.println(commandList);
    }
}
