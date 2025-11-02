package internity.ui;

import internity.core.Internship;

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
    public static final int COMPANY_MAXLEN = 30;
    public static final int ROLE_MAXLEN = 30;
    public static final int DEADLINE_MAXLEN = 15;
    public static final int PAY_MAXLEN = 10;
    public static final int STATUS_MAXLEN = 10;

    //table formatting strings
    static final String FORMAT_HEADER = "%" + INDEX_MAXLEN + "s %-" + COMPANY_MAXLEN + "s %-" + ROLE_MAXLEN
            + "s %-" + DEADLINE_MAXLEN + "s %-" + PAY_MAXLEN + "s %-" + STATUS_MAXLEN + "s%n";
    static final String FORMAT_CONTENT = "%" + INDEX_MAXLEN + "d %-" + COMPANY_MAXLEN + "s %-" + ROLE_MAXLEN
            + "s %-" + DEADLINE_MAXLEN + "s %-" + PAY_MAXLEN + "d %-" + STATUS_MAXLEN + "s%n";

    /** Horizontal line used to visually separate sections in the console output. */
    static final String LINE = "____________________________________________________" +
            "_________________________________________________________\n";

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
     * @param internshipInfo details of the internship that was added
     */
    public static void printAddInternship(String internshipInfo, int totalItems) {
        System.out.println("Added this internship:");
        System.out.println(internshipInfo);
        System.out.println("Now you have " + totalItems + " internship(s) in the list.");
    }

    public static void printRemoveInternship(String internshipInfo, int totalItems) {
        System.out.println("Removed this internship:");
        System.out.println(internshipInfo);
        System.out.println("Now you have " + totalItems + " internship(s) in the list.");
    }

    /**
     * Prints a confirmation message after successfully updating an internship field.
     *
     * <p>
     * This method displays a message indicating that a specific field of an internship
     * has been successfully updated, along with the new value of that field.
     * </p>
     *
     * @param field    the name of the field that was updated (e.g., "company", "role")
     * @param index    the index of the internship in the list (0-based)
     * @param newValue the new value assigned to the updated field
     */
    public static void printUpdateInternship(String field, int index, String newValue) {
        System.out.printf("Internship %s at index %d successfully updated to: %s%n",
                field.toLowerCase(),
                index + 1,
                newValue);
    }

    /**
     * Prints a summary of the internship update, showing both the original and updated details.
     *
     * <p>
     * This method displays a message indicating that an internship at a specific index
     * has been successfully updated. It then prints the original internship details
     * followed by the updated internship details, using the {@link Internship#toString()}
     * method for formatting.
     * </p>
     *
     * @param index            the index of the internship in the list (0-based)
     * @param oldInternship    the original {@code Internship} object before the update
     * @param updatedInternship the updated {@code Internship} object after the update
     */
    public static void printUpdateSummary(int index, Internship oldInternship, Internship updatedInternship) {
        System.out.println("Internship at index " + (index + 1) + " successfully updated:");
        System.out.println("Original:\n" + oldInternship.toString());
        System.out.println("Updated:\n" + updatedInternship.toString());
    }

    public static void printInternshipListEmpty() {
        System.out.println("Your internship list is currently empty.");
    }

    public static void printNoInternshipFound() {
        System.out.println("No internships with this company or role found.");
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
                  - update    : Update any field of an internship application at the specified index.
                  - username  : Set your username for personalised greetings.
                  - dashboard : View statistics about your internship applications.
                  - help      : Display this list again. Your guide to managing internships.
                  - exit      : Terminate this session. Your progress will be saved.

                For verbose instructions, refer to the user guide.
                """;
        System.out.print(commandList);
    }
}
