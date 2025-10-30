package internity.logic.cli;

import java.util.logging.Logger;

import internity.logic.commands.AddCommand;
import internity.logic.commands.DeleteCommand;
import internity.logic.commands.FindCommand;
import internity.logic.commands.ListCommand;
import internity.logic.commands.UpdateCommand;
import internity.logic.commands.UsernameCommand;
import internity.core.Date;
import internity.core.InternityException;
import internity.ui.Ui;
import internity.utils.DateFormatter;


/**
 * The {@code ArgumentParser} class is responsible for parsing command-line arguments for various commands
 * such as Add, Delete, Find, Update and List.
 *
 * <p>Supported commands include:
 * <ul>
 *      <li>{@link AddCommand}</li>
 *      <li>{@link DeleteCommand}</li>
 *      <li>{@link FindCommand}</li>
 *      <li>{@link UpdateCommand}</li>
 *      <li>{@link ListCommand}</li>
 *      <li>{@link UsernameCommand}</li>
 * </ul>
 */
public final class ArgumentParser {
    private static final int ADD_COMMAND_PARTS = 4;
    private static final int IDX_COMPANY = 0;
    private static final int IDX_ROLE = 1;
    private static final int IDX_DEADLINE = 2;
    private static final int IDX_PAY = 3;
    private static final String ADD_COMMAND_PARSE_LOGIC = "\\s+(?=company/|role/|deadline/|pay/)";

    private static final Logger logger = Logger.getLogger(ArgumentParser.class.getName());

    /**
     * Private constructor to prevent instantiation of the ArgumentParser class.
     */
    private ArgumentParser() {
    }

    /**
     * Parses the arguments provided for the {@link AddCommand} and constructs
     * a corresponding {@code AddCommand} instance.
     *
     * <p>
     * This method expects a single string containing all required fields of the
     * add command in the following format:
     * <pre>
     * company/COMPANY_NAME role/ROLE_NAME deadline/DEADLINE pay/PAY_AMOUNT
     * </pre>
     * The fields must appear in this order and be separated by whitespace.
     * </p>
     *
     * <p>
     * The parsing process includes:
     * <ul>
     *     <li>Splitting the arguments string using a predefined delimiter
     *         {@code ADD_COMMAND_PARSE_LOGIC}.</li>
     *     <li>Verifying that all required fields are present and in the correct order.</li>
     *     <li>Extracting the actual values for company, role, deadline and pay by removing the
     *         respective prefixes ("company/", "role/", "deadline/", "pay/").</li>
     *     <li>Trimming whitespace from all extracted values.</li>
     *     <li>Parsing the deadline string into a {@link Date} object
     *         using {@link DateFormatter#parse(String)}.</li>
     *     <li>Parsing the pay string into an integer.</li>
     * </ul>
     * </p>
     *
     * <p>
     * After extraction, the method performs several validations:
     * <ul>
     *     <li>Ensures none of the extracted values are empty or missing.</li>
     *     <li>Ensures the pay amount is a non-negative integer.</li>
     *     <li>Ensures that the length of the company and role strings does not exceed the
     *         maximum allowed lengths defined in {@link Ui} constants
     *         ({@code COMPANY_MAXLEN} and {@code ROLE_MAXLEN}).</li>
     * </ul>
     * </p>
     *
     * <p>
     * If any of the above validations fail, an {@link InternityException} is thrown with a
     * message indicating an invalid add command. Logging is performed at key stages for
     * debugging purposes.
     * </p>
     *
     * @param args the raw argument string provided by the user for the add command
     * @return a new {@link AddCommand} instance constructed from the parsed and validated arguments
     * @throws InternityException if:
     *     <ul>
     *         <li>The argument string is null or blank.</li>
     *         <li>One or more required fields are missing, empty or in the wrong order.</li>
     *         <li>Any field exceeds its maximum allowed length.</li>
     *         <li>The pay amount is invalid (negative or non-numeric).</li>
     *         <li>Parsing of the deadline fails.</li>
     *     </ul>
     */
    public static AddCommand parseAddCommandArgs(String args) throws InternityException {
        if (args == null || args.isBlank()) {
            throw InternityException.invalidAddCommand();
        }

        assert !args.isBlank() : "Arguments cannot be blank after validation";

        try {
            String[] parts = args.split(ADD_COMMAND_PARSE_LOGIC);
            if (parts.length != ADD_COMMAND_PARTS ||
                    !parts[IDX_COMPANY].startsWith("company/") ||
                    !parts[IDX_ROLE].startsWith("role/") ||
                    !parts[IDX_DEADLINE].startsWith("deadline/") ||
                    !parts[IDX_PAY].startsWith("pay/")) {
                logger.severe("One or more arguments of Add command is in the wrong order.");
                throw InternityException.invalidAddCommand();
            }

            logger.info("Successfully parsed 4 arguments of AddCommand.");

            String company = parts[IDX_COMPANY].substring("company/".length()).trim();
            String role = parts[IDX_ROLE].substring("role/".length()).trim();
            Date deadline = DateFormatter.parse(parts[IDX_DEADLINE].substring("deadline/".length()).trim());
            int pay = Integer.parseInt(parts[IDX_PAY].substring("pay/".length()).trim());

            // throw exception on empty input or invalid pay
            if (company.isEmpty() || role.isEmpty() || pay < 0) {
                logger.severe("One or more arguments of Add command is empty or invalid.");
                throw InternityException.invalidAddCommand();
            }

            // throw exception on exceeding max length
            if (company.length() > Ui.COMPANY_MAXLEN) {
                logger.severe("Company name exceeded max length.");
                throw InternityException.exceedFieldLength("Company", Ui.COMPANY_MAXLEN, company.length());
            }
            if (role.length() > Ui.ROLE_MAXLEN) {
                logger.severe("Role exceeded max length.");
                throw InternityException.exceedFieldLength("Role", Ui.ROLE_MAXLEN, role.length());
            }

            return new AddCommand(company, role, deadline, pay);
        } catch (Exception e) {
            logger.severe("Error executing Add Command: " + e.getMessage());
            throw InternityException.invalidAddCommand();
        }
    }

    /**
     * Parses the arguments for Delete Command to create an {@link DeleteCommand} instance.
     *
     * @param args arguments for {@link DeleteCommand}
     * @return an instance of {@link DeleteCommand} constructed from the parsed arguments.
     * @throws InternityException if the arguments are missing or invalid.
     */
    public static DeleteCommand parseDeleteCommandArgs(String args) throws InternityException {
        if (args == null || args.isBlank()) {
            throw InternityException.invalidDeleteCommand();
        }
        try {
            int oneBasedIndex = Integer.parseInt(args.trim());

            int zeroBasedIndex = oneBasedIndex - 1;

            return new DeleteCommand(zeroBasedIndex);

        } catch (NumberFormatException e) {
            throw InternityException.invalidInternshipIndex();
        }
    }

    /**
     * Parses the arguments provided for the {@link FindCommand} and constructs a corresponding
     *  {@code FindCommand} instance.
     *
     * <p>
     * This method expects a non-empty string representing the keyword to search for in the
     * company name or role of internships. The search is case-insensitive.
     * </p>
     *
     * @param args the search keyword provided by the user for the find command
     * @return a new {@link FindCommand} instance constructed from the parsed keyword
     * @throws InternityException if the argument string is {@code null} or blank
     */
    public static FindCommand parseFindCommandArgs(String args) throws InternityException {
        if (args == null || args.isBlank()) {
            throw InternityException.invalidFindCommand();
        }
        return new FindCommand(args);
    }

    /**
     * Parses the arguments for Update Command to create an {@link UpdateCommand} instance.
     *
     * @param args arguments for {@link UpdateCommand}
     * @return an instance of {@link UpdateCommand} constructed from the parsed arguments.
     * @throws InternityException if the arguments are missing or invalid.
     */
    public static UpdateCommand parseUpdateCommandArgs(String args) throws InternityException {
        String trimmed = requireArgs(args);
        String[] idxAndTagged = splitIndexAndTagged(trimmed);
        int index = parseOneBasedIndex(idxAndTagged[0]);
        String tagged = requireTagged(idxAndTagged[1]);

        String[] parts = tagged.split("\\s+(?=company/|role/|deadline/|pay/|status/)");
        String company = null;
        String role = null;
        Date deadline = null;
        Integer pay = null;
        String status = null;

        try {
            for (String part : parts) {
                String p = part.trim();
                if (p.isEmpty()) {
                    continue;
                }
                if (p.startsWith("company/")) {
                    company = valueAfterTag(p, "company/");
                    if (company.isEmpty()) {
                        logger.severe("Company name is empty.");
                        throw InternityException.emptyField("company/");
                    }
                    if (company.length() > Ui.COMPANY_MAXLEN) {
                        logger.severe("Company name exceeded max length.");
                        throw InternityException.exceedFieldLength("Company",
                                Ui.COMPANY_MAXLEN,
                                company.length());
                    }
                } else if (p.startsWith("role/")) {
                    role = valueAfterTag(p, "role/");
                    if (role.isEmpty()) {
                        logger.severe("Role is empty.");
                        throw InternityException.emptyField("role/");
                    }
                    if (role.length() > Ui.ROLE_MAXLEN) {
                        logger.severe("Role exceeded max length.");
                        throw InternityException.exceedFieldLength("Role", Ui.ROLE_MAXLEN, role.length());
                    }
                } else if (p.startsWith("deadline/")) {
                    String d = valueAfterTag(p, "deadline/");
                    deadline = DateFormatter.parse(d);
                } else if (p.startsWith("pay/")) {
                    String payStr = valueAfterTag(p, "pay/");
                    int payVal = Integer.parseInt(payStr);
                    if (payVal < 0) {
                        throw new NumberFormatException();
                    }
                    pay = payVal;
                } else if (p.startsWith("status/")) {
                    status = valueAfterTag(p, "status/");
                    if (status.isEmpty()) {
                        throw InternityException.emptyField("status/");
                    }
                } else {
                    throw InternityException.unknownUpdateField(p);
                }
            }
        } catch (NumberFormatException e) {
            throw InternityException.invalidPayFormat();
        }

        if (company == null && role == null && deadline == null && pay == null && status == null) {
            throw InternityException.noUpdateFieldsProvided();
        }

        return new UpdateCommand(index, company, role, deadline, pay, status);
    }

    /**
     * Parses the arguments for List Command to create an {@link ListCommand} instance.
     *
     * @param args arguments for {@link ListCommand}
     * @return an instance of ListCommand constructed from the parsed arguments.
     *      Returns a default ListCommand if no arguments are provided.
     * @throws InternityException if the arguments are missing or invalid.
     */
    public static ListCommand parseListCommandArgs(String args) throws InternityException {
        if (args == null || args.isBlank()) {
            return new ListCommand(ListCommand.OrderType.DEFAULT); // Default order
        }

        if (!args.startsWith("sort/")) {
            throw InternityException.invalidListCommand();
        }

        String[] splitArgs = args.split("\\s+sort/");
        if (splitArgs.length > 1) {
            throw InternityException.invalidListCommand();
        }

        String order = splitArgs[0].substring("sort/".length()).trim();
        if (order.equals("asc")) {
            return new ListCommand(ListCommand.OrderType.ASCENDING);
        } else if (order.equals("desc")) {
            return new ListCommand(ListCommand.OrderType.DESCENDING);
        } else {
            throw InternityException.invalidListCommand();
        }
    }

    private static String requireArgs(String args) throws InternityException {
        if (args == null || args.isBlank()) {
            throw InternityException.invalidUpdateFormat();
        }
        return args.trim();
    }

    private static String requireTagged(String tagged) throws InternityException {
        if (tagged.isBlank()) {
            throw InternityException.noUpdateFieldsProvided();
        }
        return tagged;
    }

    private static String[] splitIndexAndTagged(String trimmed) throws InternityException {
        int firstSpace = trimmed.indexOf(' ');
        if (firstSpace < 0) {
            throw InternityException.invalidUpdateFormat();
        }
        String indexToken = trimmed.substring(0, firstSpace).trim();
        String tagged = trimmed.substring(firstSpace + 1).trim();
        return new String[] { indexToken, tagged };
    }

    private static int parseOneBasedIndex(String indexToken) throws InternityException {
        try {
            return Integer.parseInt(indexToken) - 1;
        } catch (NumberFormatException e) {
            throw InternityException.invalidIndexForUpdate();
        }
    }

    private static String valueAfterTag(String token, String tag) {
        return token.substring(tag.length()).trim();
    }

    /**
     * Parses the arguments for Username Command to create an {@link UsernameCommand} instance.
     *
     * @param args arguments for {@link UsernameCommand}
     * @return an instance of {@link UsernameCommand} constructed from the parsed arguments.
     * @throws InternityException if the arguments are missing or invalid.
     */
    public static UsernameCommand parseUsernameCommandArgs(String args) throws InternityException {
        if (args == null || args.isBlank()) {
            throw InternityException.invalidUsernameCommand();
        }
        return new UsernameCommand(args);
    }
}
