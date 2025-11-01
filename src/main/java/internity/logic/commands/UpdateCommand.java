package internity.logic.commands;

import internity.core.Date;
import internity.core.InternityException;
import internity.core.Internship;
import internity.core.InternshipList;
import internity.ui.Ui;

/**
 * Represents a command that updates one or more fields of an existing internship entry
 * in the {@link InternshipList}.
 * <p>
 * The command parses a user input string in the following format:
 * <pre>
 *     update INDEX [company/COMPANY_NAME] [role/ROLE_NAME] [deadline/DD-MM-YYYY] [pay/PAY_AMOUNT] [status/NEW_STATUS]
 * </pre>
 * Example:
 * <pre>
 *     update 1 company/Google role/Software Engineer pay/9000 status/Accepted
 * </pre>
 * </p>
 *
 * <p>
 * Each provided field will be validated and isUpdated in the corresponding internship entry.
 * </p>
 */
public class UpdateCommand extends Command {
    private final int index;     
    private final String company;
    private final String role;    
    private final Date deadline;   
    private final Integer pay;   
    private final String status;   

    /**
     * Constructs an {@code UpdateCommand} that updates one or more internship fields.
     *
     * @param index    the index of the internship to be isUpdated (0-based)
     * @param company  the new company name, or {@code null} if unchanged
     * @param role     the new role, or {@code null} if unchanged
     * @param deadline the new application deadline, or {@code null} if unchanged
     * @param pay      the new pay amount, or {@code null} if unchanged
     * @param status   the new internship status, or {@code null} if unchanged
     */
    public UpdateCommand(int index, String company, String role, Date deadline, Integer pay, String status) {
        this.index = index;
        this.company = company;
        this.role = role;
        this.deadline = deadline;
        this.pay = pay;
        this.status = status;
    }

    /**
     * Constructs a simplified {@code UpdateCommand} that only updates the internship status.
     *
     * @param index     the index of the internship to be isUpdated (0-based)
     * @param newStatus the new internship status
     */
    public UpdateCommand(int index, String newStatus) {
        this(index, null, null, null, null, newStatus);
    }

    /**
     * Executes the update command by applying each provided field update to the specified internship.
     * <p>
     * Each non-null parameter will trigger a corresponding update in {@link InternshipList}.
     * If no valid fields are provided, an {@link InternityException} will be thrown.
     * </p>
     *
     * <p>
     * After performing the update, a confirmation message is displayed through {@link Ui#printUpdateInternship()}.
     * </p>
     *
     * @throws InternityException if the index is invalid or no fields are provided.
     */
    @Override
    public void execute() throws InternityException {
        boolean isUpdated = false;
        Internship OldInternship = InternshipList.get(index);
        if (company != null) {
            InternshipList.updateCompany(index, company);
            isUpdated = true;
        }
        if (role != null) {
            InternshipList.updateRole(index, role);
            isUpdated = true;
        }
        if (deadline != null) {
            InternshipList.updateDeadline(index, deadline);
            isUpdated = true;
        }
        if (pay != null) {
            InternshipList.updatePay(index, pay);
            isUpdated = true;
        }
        if (status != null) {
            InternshipList.updateStatus(index, status);
            isUpdated = true;
        }
        if (!isUpdated) {
            throw new InternityException(
                "Provide at least one field to update: company/, role/, deadline/, pay/, status/"
            );
        }
        Ui.printUpdateSummary(index, OldInternship, InternshipList.get(index));
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
