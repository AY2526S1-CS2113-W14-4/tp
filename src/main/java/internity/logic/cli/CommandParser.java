package internity.logic.cli;

import java.util.logging.Level;
import java.util.logging.Logger;

import internity.logic.commands.Command;
import internity.core.InternityException;

/**
 * Parses raw user input into executable {@link Command} objects. <br>
 * The {@code CommandParser} is responsible for identifying the command keyword,
 * validating the input and constructing the instance of the
 * corresponding command.
 *
 * <p>Supported commands include:
 * <ul>
 *      <li>{@code exit} - exits the program</li>
 * </ul>
 */
public class CommandParser {
    private static final Logger logger = Logger.getLogger(CommandParser.class.getName());

    static {
        logger.setLevel(Level.WARNING);
    }

    /**
     * Parses the given input string and returns the corresponding {@link Command}. <br>
     * The first token (before the first space) is treated as the command keyword.
     *
     * @param input raw user input
     * @return a {@link Command} corresponding to the input
     * @throws InternityException if input is null or blank or unknown command is entered
     */
    public Command parseInput(String input) throws InternityException {
        if (input == null || input.isBlank()) {
            throw InternityException.invalidInput();
        }

        validateValidAscii(input);

        assert !input.isBlank() : "Input should not be blank after validation";

        String[] parts = input.trim().split("\\s+", 2);
        assert parts.length >= 1 : "Splitting input should result in at least one part";

        String commandWord = parts[0].toLowerCase();
        String args = parts.length > 1 ? parts[1] : "";

        logger.fine(() -> "Parsed command: \"" + commandWord + "\" with args: \"" + args + "\"");

        assert !commandWord.isBlank() : "Command keyword must not be blank";
        assert args != null : "Args should never be null (may be empty string)";

        CommandFactory commandFactory = new CommandFactory();
        Command command = commandFactory.createCommand(commandWord, args);

        logger.fine(() -> "Parsed command: \"" + commandWord + "\" with args: \"" + args + "\"");

        assert command != null : "CommandFactory should never return null command";

        logger.info(() -> "Successfully created command: " + command.getClass().getSimpleName());
        return command;
    }

    /**
     * Validates that the given input string contains only printable ASCII characters.
     *
     * <p>This method checks each character in the input string to ensure that:
     * <ul>
     *     <li>All characters are printable ASCII characters, including uppercase and lowercase letters,
     *         digits, and symbols.</li>
     *     <li>Printable ASCII characters have values ranging from 32 (space) to 126 (tilde ~).</li>
     * </ul>
     * </p>
     *
     * @param input the string to validate
     * @throws InternityException if the input contains non-printable ASCII characters
     */
    public void validateValidAscii(String input) throws InternityException {
        int[] codePoints = input.codePoints().toArray();
        for (int cp : codePoints) {
            if (cp < 32 || cp > 126) { // Only printable ASCII
                String invalidChar = new String(Character.toChars(cp));
                logger.warning("Input contains invalid character: " + invalidChar);
                throw new InternityException("Invalid character detected: '" + invalidChar + "'");
            }
        }
    }

}
