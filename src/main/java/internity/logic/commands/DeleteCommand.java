package internity.logic.commands;

import java.util.logging.Logger;

import internity.core.Internship;
import internity.core.InternityException;
import internity.core.InternshipList;
import internity.ui.Ui;

/**
 * Represents a command to delete an internship entry from the list.
 * The internship is identified by its index in the list.
 */
public class DeleteCommand extends Command {
    private static final Logger logger = Logger.getLogger(DeleteCommand.class.getName());
    private final int index;

    /**
     * Constructs a DeleteCommand with the specified index.
     *
     * @param index The zero-based index of the internship to delete.
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the delete command by removing the internship at the specified index.
     * Displays the deleted internship information and the updated total count.
     *
     * @throws InternityException If the index is out of bounds or deletion fails.
     */
    @Override
    public void execute() throws InternityException {
        logger.info("Executing delete command for index: " + index);

        // Get the internship before deleting to display its info
        Internship internship = InternshipList.get(index);

        String internshipInfo = internship.getCompany() + " - " + internship.getRole();
        // Assertion for internshipInfo not required here as strong concatenation will always be true
        logger.fine("Retrieved internship: " + internshipInfo);

        // Delete the internship
        InternshipList.delete(index);

        // Get the new size after deletion
        int totalItems = InternshipList.size();
        assert totalItems >= 0 : "Size cannot be negative after deletion";

        // Print the removal message
        Ui.printRemoveInternship(internshipInfo, totalItems);
        logger.info("Delete command executed successfully. Remaining items: " + totalItems);
    }

    /**
     * Indicates whether this command should exit the application.
     *
     * @return {@code false}, as the delete command does not exit the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}




