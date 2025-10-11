package internity.ui;

import internity.core.Internship;

/**
 * The Ui Class provides user interface methods for the Internity chatbot.
 * Handles printing messages.
 */
public class Ui {
    static final String line = "____________________________________________________________\n";

    public static void printHorizontalLine() {
        System.out.print(line);
    }

    public static void printWelcomeMessage() {
        final String logo = " ___       _                  _ _\n" +
                "|_ _|_ __ | |_ ___ _ __ _ __ (_) |_ _   _\n" +
                " | || '_ \\| __/ _ \\ '__| '_ \\| | __| | | |\n" +
                " | || | | | ||  __/ |  | | | | | |_| |_| |\n" +
                "|___|_| |_|\\__\\___|_|  |_| |_|_|\\__|\\__, |\n" +
                "                                    |___/";
        System.out.println("Hello, welcome to\n" + logo);
        System.out.println("Be on top of your internships management with the Internity chatbot!");
        System.out.println("What is your name?");
    }

    public static void printGreeting(String input) {
        System.out.println("Hello, " + input + "!");
    }

    public static void printExit() {
        System.out.println("Thank you for using Internity! Goodbye!");
    }

    public static void printAddInternship(Internship internship) {
        System.out.println("Noted. I've added this internship:");
        System.out.println("  "
                + internship.getCompany() + " - " + internship.getRole()
                + " | Pay: " + internship.getPay()
                + " | Deadline: " + internship.getDeadline()
        );
    }

    public static void printRemoveInternship(String item, int totalItems) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + item);
        System.out.println("Now you have " + totalItems + " items in the list.");
    }

    public static void printUpdateInternship() {
        System.out.println("Internship status updated successfully!");
    }

}
