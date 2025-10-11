package internity.commands;

import internity.core.InternityException;
import internity.core.InternshipList;
import internity.ui.Ui;

/**
 * Represents a command that updates the status of an internship application.
 *
 * Command format:
 * update INDEX status/NEW_STATUS
 */
public class UpdateCommand extends Command {
    private final int index;
    private final String newStatus;

    /**
     * Constructs an UpdateCommand from a raw argument string.
     * Example: "1 status/Accepted"
     */
    public UpdateCommand(int index, String newStatus) throws InternityException {
        this.index = index;
        this.newStatus = newStatus;
    }

    @Override
    public void execute() throws InternityException {
        InternshipList.updateStatus(index, newStatus);
        Ui.printUpdateInternship();
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
