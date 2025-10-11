package internity.commands;

import internity.core.Date;
import internity.core.InternityException;
import internity.core.Internship;
import internity.core.InternshipList;
import internity.ui.Ui;
import internity.utils.DateFormatter;

/**
 * Represents a command that adds a new {@link Internship} entry to the {@link InternshipList}.
 * <p>
 * The command parses a user input string in the following format:
 * <pre>
 *     add company/COMPANY_NAME role/ROLE_NAME deadline/YYYY-MM-DD pay/PAY_AMOUNT
 * </pre>
 * Example:
 * <pre>
 *     add company/Google role/Software Engineer deadline/2025-09-17 pay/120000
 * </pre>
 * </p>
 *
 * <p>
 * Each field is parsed and validated before creating a new {@link Internship} object.
 * </p>
 */
public class AddCommand extends Command {
    private String company;
    private String role;
    private Date deadline;
    private int pay;

    /**
     * Constructs an {@code AddCommand} and extracts and parses arguments
     * from the provided user input string.
     *
     * @param args the raw user input containing internship details
     * @throws InternityException if the command format is invalid or a parsing error occurs
     */
    public AddCommand(String args) throws InternityException {
        extractArgs(args);
    }

    /**
     * Extracts field values from the user input string using predefined prefixes:
     * {@code company/}, {@code role/}, {@code deadline/}, {@code pay/}.
     * <p>
     * The method splits the input string using a lookahead regular expression to detect
     * the start of each field, then assigns values to corresponding instance variables.
     * </p>
     *
     * <p><b>Example Input:</b><br>
     * {@code company/Google role/Software Engineer deadline/01-12-2025 pay/120000}
     * </p>
     *
     * @param args the user input string containing internship details
     * @throws InternityException if any field is missing, invalid, or cannot be parsed
     */
    private void extractArgs(String args) throws InternityException {
        try {
            String[] parts = args.split("\\s+(?=company/|role/|deadline/|pay/)");

            for (String part : parts) {
                part = part.trim();

                String key = part.contains("/") ? part.substring(0, part.indexOf("/")) : "";
                String value = part.contains("/") ? part.substring(part.indexOf("/") + 1).trim() : "";

                switch (key) {
                case "company" -> company = value;
                case "role" -> role = value;
                case "deadline" -> deadline = DateFormatter.parse(value);
                case "pay" -> pay = Integer.parseInt(value);
                default -> throw InternityException.invalidAddCommand();
                }
            }
        } catch (Exception e) {
            throw InternityException.invalidAddCommand();
        }
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
        Internship internship = new Internship(company, role, deadline, pay);
        InternshipList.add(internship);
        Ui.printAddInternship(internship);
    }

    /**
     * This command will not terminate the application.
     *
     * @return {@code false}
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
