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
}
