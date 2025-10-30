package internity.logic.commands;

import java.util.logging.Logger;

import internity.core.InternityException;
import internity.core.InternshipList;

/**
 * Represents a command that lists all internship applications
 * as a formatted table
 * <br>
 * Command format:
 * {@code list [sort/asc|sort/desc]}
 */
public class ListCommand extends Command {
    public enum OrderType {
        DEFAULT,
        ASCENDING,
        DESCENDING
    }

    private static final Logger logger = Logger.getLogger(ListCommand.class.getName());
    OrderType order;

    public ListCommand(OrderType o) {
        order = o;
    }

    /**
     * Executes the {@code list} command.
     * <p>
     * Prints a formatted table with all internship applications.
     * </p>
     */
    @Override
    public void execute() throws InternityException {
        logger.info("Executing list command");
        InternshipList.listAll(order);
        logger.info("List command executed successfully.");
    }

    /**
     * Indicates that this command does not terminate the program.
     *
     * @return {@code false}
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
