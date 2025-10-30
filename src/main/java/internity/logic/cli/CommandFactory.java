package internity.logic.cli;

import internity.logic.commands.AddCommand;
import internity.logic.commands.Command;
import internity.logic.commands.DashboardCommand;
import internity.logic.commands.DeleteCommand;
import internity.logic.commands.ExitCommand;
import internity.logic.commands.FindCommand;
import internity.logic.commands.ListCommand;
import internity.logic.commands.UpdateCommand;
import internity.logic.commands.UsernameCommand;
import internity.logic.commands.HelpCommand;
import internity.core.InternityException;

/**
 * A factory class responsible for creating {@link Command} objects
 * based on the user input command word and its arguments.
 * <p>
 * The {@code CommandFactory} delegates argument parsing to the
 * {@link ArgumentParser} and returns the corresponding {@link Command}
 * subclass instance for execution. If the provided command word does not
 * match any recognized command, an {@link InternityException} is thrown.
 * </p>
 *
 * <p>Supported commands include:</p>
 * <ul>
 *   <li>{@link AddCommand}</li>
 *   <li>{@link DeleteCommand}</li>
 *   <li>{@link FindCommand}</li>
 *   <li>{@link UpdateCommand}</li>
 *   <li>{@link ListCommand}</li>
 *   <li>{@link UsernameCommand}</li>
 *   <li>{@link HelpCommand}</li>
 *   <li>{@link ExitCommand}</li>
 * </ul>
 */
public class CommandFactory {
    /**
     * Creates a {@link Command} instance based on the specified command word
     * and argument string.
     * <p>
     * The method determines which command to create by matching the given
     * {@code commandWord} against known commands. It then delegates argument
     * parsing to the appropriate static method in {@link ArgumentParser}.
     * </p>
     *
     * @param commandWord the command keyword (e.g., "add", "delete", "update")
     * @param args the arguments to be parsed and passed to the command
     * @return a concrete {@link Command} object representing the parsed command
     * @throws InternityException if the command is unknown or argument parsing fails
     */
    public Command createCommand(String commandWord, String args) throws InternityException {
        switch (commandWord) {
        case "add":
            return ArgumentParser.parseAddCommandArgs(args);
        case "delete":
            return ArgumentParser.parseDeleteCommandArgs(args);
        case "find":
            return ArgumentParser.parseFindCommandArgs(args);
        case "update":
            return ArgumentParser.parseUpdateCommandArgs(args);
        case "list":
            return ArgumentParser.parseListCommandArgs(args);
        case "username":
            return ArgumentParser.parseUsernameCommandArgs(args);
        case "dashboard":
            return new DashboardCommand();
        case "help":
            return new HelpCommand();
        case "exit":
            return new ExitCommand();
        default:
            throw InternityException.unknownCommand(commandWord);
        }
    }
}
