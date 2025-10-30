package internity.logic.commands;

import internity.core.InternityException;
import internity.ui.Ui;

import java.util.logging.Logger;

/**
 * Represents a command that lists all available commands.
 * as an ordered list
 * <br>
 * Command format:
 * {@code help}
 */
public class HelpCommand extends Command {

    private static final Logger logger = Logger.getLogger(HelpCommand.class.getName());

    public HelpCommand() {

    }

    /**
     * Executes the {@code help} command.
     * <p>
     * Prints an ordered list of all available commands.
     * </p>
     */
    @Override
    public void execute() throws InternityException {
        logger.info("Executing help command");
        Ui.printHelp();
        logger.info("Help command executed successfully.");
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
