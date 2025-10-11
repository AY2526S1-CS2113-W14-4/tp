package internity.core;

/**
 * Represents custom exceptions used throughout the Internity application.
 *
 * <p>
 * This class extends {@code Exception} and provides static methods that create a specific
 * exception message for common error conditions such as invalid input or unknown commands.
 * </p>
 */
public class InternityException extends Exception {
    public InternityException(String message) {
        super(message);
    }

    public static InternityException invalidInput() {
        return new InternityException("Input cannot be null or blank");
    }

    public static InternityException unknownCommand(String message) {
        return new InternityException("Unknown command: " + message);
    }

    public static InternityException invalidDateFormat() {
        return new InternityException("Invalid date format. Expected dd-MM-yyyy (e.g. 08-10-2025)");
    }

    public static InternityException invalidAddCommand() {
        return new InternityException("Invalid add command. Usage: add company/COMPANY_NAME" +
                " role/ROLE_NAME deadline/DEADLINE pay/PAY_AMOUNT");
    }

    public static InternityException invalidDeleteCommand() {
        return new InternityException("Invalid delete command. Usage: delete INDEX");
    }

    public static InternityException invalidInternshipIndex() {
        return new InternityException("Invalid internship index.");
    }
}
