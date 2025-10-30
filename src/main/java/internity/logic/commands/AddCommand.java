package internity.logic.commands;

import java.util.logging.Logger;

import internity.core.Date;
import internity.core.InternityException;
import internity.core.Internship;
import internity.core.InternshipList;
import internity.ui.Ui;

/**
 * Represents a command that adds a new {@link Internship} entry to the {@link InternshipList}.
 * <p>
 * The command parses a user input string in the following format:
 * <pre>
 *     add company/COMPANY_NAME role/ROLE_NAME deadline/DEADLINE pay/PAY_AMOUNT
 * </pre>
 * Example:
 * <pre>
 *     add company/Google role/Software Engineer deadline/17-09-2025 pay/120000
 * </pre>
 * </p>
 *
 * <p>
 * Each field is parsed and validated before creating a new {@link Internship} object.
 * </p>
 */
public class AddCommand extends Command {
    private static final Logger logger = Logger.getLogger(AddCommand.class.getName());

    private final String company;
    private final String role;
    private final Date deadline;
    private final int pay;

    /**
     * Constructs an {@code AddCommand} with the specified internship details.
     *
     * @param company  the name of the company offering the internship
     * @param role     the internship role or position
     * @param deadline the application deadline for the internship in dd-MM-yyyy
     * @param pay      the monthly pay for the internship
     */
    public AddCommand(String company, String role, Date deadline, int pay) {
        this.company = company;
        this.role = role;
        this.deadline = deadline;
        this.pay = pay;
    }

    /**
     * Executes the add command by creating a new {@link Internship} object and
     * adding it to the global static {@link InternshipList}.
     * <p>
     * Once added, the {@link Ui} class is used to show a confirmation message.
     * </p>
     *
     * @throws InternityException if an error occurs.
     */
    @Override
    public void execute() throws InternityException {
        logger.info("Executing add command");
        Internship internship = new Internship(company, role, deadline, pay);
        InternshipList.add(internship);
        Ui.printAddInternship(internship);
        logger.info("Add command executed successfully.");
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
