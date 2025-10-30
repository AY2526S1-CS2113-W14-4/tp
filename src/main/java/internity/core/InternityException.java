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
    /**
     * Constructs an {@code InternityException} with the specified detail message.
     *
     * @param message the detail message for the exception
     */
    public InternityException(String message) {
        super(message);
    }

    /**
     * Returns an exception indicating that the input provided is null or blank.
     *
     * @return an {@code InternityException} for invalid input
     */
    public static InternityException invalidInput() {
        return new InternityException("Input cannot be null or blank");
    }

    /**
     * Returns an exception indicating that the command entered is unknown.
     *
     * @param message the unrecognized command entered by the user
     * @return an {@code InternityException} for an unknown command
     */
    public static InternityException unknownCommand(String message) {
        return new InternityException("Unknown command: " + message);
    }

    /**
     * Returns an exception indicating that the provided date format is invalid.
     *
     * @return an {@code InternityException} for an invalid date format
     */
    public static InternityException invalidDateFormat() {
        return new InternityException("Invalid date format. Expected dd-MM-yyyy (e.g. 08-10-2025)");
    }

    /**
     * Returns an exception indicating that the add command format is invalid.
     *
     * @return an {@code InternityException} for an invalid add command format
     */
    public static InternityException invalidAddCommand() {
        return new InternityException("Invalid add command.\n" +
                "Usage: add company/COMPANY_NAME" +
                " role/ROLE_NAME" +
                " deadline/DEADLINE" +
                " pay/PAY_AMOUNT"
        );
    }

    /**
     * Returns an exception indicating that the delete command format is invalid.
     *
     * @return an {@code InternityException} for an invalid delete command format
     */
    public static InternityException invalidDeleteCommand() {
        return new InternityException("Invalid delete command.\nUsage: delete INDEX");
    }

    /**
     * Returns an exception indicating that the find command format is invalid.
     *
     * @return an {@code InternityException} for an invalid find command format
     */
    public static InternityException invalidFindCommand() {
        return new InternityException("Invalid find command.\nUsage: find KEYWORD");
    }

    /**
     * Returns an exception indicating that the specified internship index is invalid.
     *
     * @return an {@code InternityException} for an invalid internship index
     */
    public static InternityException invalidInternshipIndex() {
        return new InternityException("Invalid internship index.");
    }

    /**
     * Returns an exception indicating that the list command format is invalid.
     *
     * @return an {@code InternityException} for an invalid list command format
     */
    public static InternityException invalidListCommand() {
        return new InternityException("Invalid list command.\nUsage: list [sort/asc|sort/desc]");
    }

    /**
     * Returns an exception indicating that the update command format is invalid.
     *
     * @return an {@code InternityException} for an invalid update command format
     */
    public static InternityException invalidUpdateFormat() {
        return new InternityException(
                "Invalid update command.\nUsage: update INDEX field/VALUE"
        );
    }

    /**
     * Returns an exception indicating that no fields were provided for an update command.
     *
     * @return an {@code InternityException} when no update fields are provided
     */
    public static InternityException noUpdateFieldsProvided() {
        return new InternityException(
                "Provide at least one field to update: company/, role/, deadline/, pay/, status/"
        );
    }

    /**
     * Returns an exception indicating that a required field is empty.
     *
     * @param tag the name of the empty field
     * @return an {@code InternityException} for an empty field
     */
    public static InternityException emptyField(String tag) {
        return new InternityException(tag + " cannot be empty");
    }

    /**
     * Returns an exception indicating that a field exceeds its maximum allowed length.
     *
     * @param tag         the name of the field
     * @param maxLength   the maximum allowed length for the field
     * @param givenLength the actual length of the provided field value
     * @return an {@code InternityException} for a field exceeding maximum length
     */
    public static InternityException exceedFieldLength(String tag, int maxLength, int givenLength) {
        return new InternityException(tag + " cannot exceed " +
                maxLength + " characters : given " +
                givenLength + " characters");
    }

    /**
     * Returns an exception indicating that the index provided for an update command is invalid.
     *
     * @return an {@code InternityException} for an invalid update index
     */
    public static InternityException invalidIndexForUpdate() {
        return new InternityException(
                "Invalid index. Use a positive integer, for example: update 1 company/Google"
        );
    }

    /**
     * Returns an exception indicating that the pay format provided is invalid.
     *
     * @return an {@code InternityException} for an invalid pay format
     */
    public static InternityException invalidPayFormat() {
        return new InternityException("Invalid pay. Use a whole number (example: pay/8000)");
    }

    /**
     * Returns an exception indicating that an unknown field was provided in the update command.
     *
     * @param token the unknown field token
     * @return an {@code InternityException} for an unknown update field
     */
    public static InternityException unknownUpdateField(String token) {
        return new InternityException(
                "Unknown update field in \"" + token
                        + "\". Allowed: company, role, deadline, pay, status"
        );
    }

    /**
     * Returns an exception indicating that an invalid status value was provided.
     *
     * @param value the invalid status value
     * @return an {@code InternityException} for an invalid status value
     */
    public static InternityException invalidStatus(String value) {
        return new InternityException(
                "Invalid status \"" + value
                        + "\". Allowed: Pending, Interested, Applied, Interviewing, Offer, Accepted, Rejected"
        );
    }

    /**
     * Returns an exception indicating that the username command format is invalid.
     *
     * @return an {@code InternityException} for an invalid username command format
     */
    public static InternityException invalidUsernameCommand() {
        return new InternityException("Invalid username command.\nUsage: username USERNAME");
    }
}
