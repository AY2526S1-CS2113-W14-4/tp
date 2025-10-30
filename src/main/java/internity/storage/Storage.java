package internity.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import internity.core.Date;
import internity.core.InternityException;
import internity.core.Internship;
import internity.core.InternshipList;
import internity.ui.Ui;
import internity.utils.DateFormatter;

/**
 * Handles loading and saving internships to a file for persistent storage.
 * The storage format is a pipe-delimited text file where each line represents one internship.
 * Format: company | role | deadline (DD-MM-YYYY) | pay | status
 */
public class Storage {
    private static final Logger logger = Logger.getLogger(Storage.class.getName());

    static {
        logger.setLevel(Level.WARNING);
    }

    // Indices for pipe delimited format
    private static final int IDX_COMPANY = 0;
    private static final int IDX_ROLE = 1;
    private static final int IDX_DEADLINE = 2;
    private static final int IDX_PAY = 3;
    private static final int IDX_STATUS = 4;
    private static final int LEN_REQUIRED_FIELDS = 5;

    private final Path filePath;

    /**
     * Creates a new Storage instance with the specified file path.
     *
     * @param filePath The path to the file for storing internships.
     */
    public Storage(String filePath) {
        assert filePath != null : "File path cannot be null";
        assert !filePath.trim().isEmpty() : "File path cannot be empty";
        this.filePath = Paths.get(filePath);
    }

    /**
     * Loads internships from the storage file.
     * The first line should contain "Username (in line below):"
     * The second line should contain the actual username.
     * Remaining lines contain internship entries.
     *
     * @return ArrayList of internships loaded from the file.
     * @throws InternityException If there is an error reading the file.
     */
    public ArrayList<Internship> load() throws InternityException {
        logger.info("Loading internships from: " + filePath);
        ArrayList<Internship> internships = new ArrayList<>();

        if (!Files.exists(filePath)) {
            logger.info("Storage file does not exist. Starting with empty list.");
            return internships; // First run: nothing to load
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
            // Read first line (username header)
            String line = br.readLine();
            if (line == null || !line.equals("Username (in line below):")) {
                logger.warning("Invalid file format: missing username header");
                throw new InternityException("Invalid storage file format");
            }

            // Read second line (actual username)
            String username = br.readLine();
            if (username != null && !username.trim().isEmpty()) {
                InternshipList.setUsername(username.trim());
                logger.info("Loaded username: " + username.trim());
            }

            // Read remaining lines as internship data
            int lineNumber = 2;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                String errorMessage = parseInternshipFromFile(line, internships);
                if (errorMessage != null) {
                    System.err.println(errorMessage);
                }
            }
        } catch (IOException e) {
            logger.severe("Failed to load internships from " + filePath + ": " + e.getMessage());
            throw new InternityException("Could not load internships: " + e.getMessage());
        }

        logger.info("Successfully loaded " + internships.size() + " internships");
        return internships;
    }

    /**
     * Parses a single line from the storage file into an Internship object.
     *
     * @param line The line to parse.
     * @param internships The list to add the parsed internship to.
     * @return Error message if parsing failed, null if successful.
     */
    private String parseInternshipFromFile(String line, ArrayList<Internship> internships) {
        assert line != null : "Line to parse cannot be null";

        String[] parts = line.split("\\|");

        // Trim all parts
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        // Validate field count
        if (parts.length != LEN_REQUIRED_FIELDS) {
            return "Warning: Skipped line with invalid number of fields: " + line;
        }

        // Parse and validate all fields in one place
        return parseAndValidateFields(parts, line, internships);
    }

    /**
     * Parses and validates all fields of an internship entry from storage.
     * This centralizes all parsing and validation logic for clarity and maintainability.
     *
     * Validation rules:
     * - Company and role must not be empty
     * - Pay must be a valid integer format
     * - Pay must be non-negative
     * - Deadline must be in valid DD-MM-YYYY format
     * - Deadline must represent a valid calendar date (no Feb 31, etc.)
     * - Status must be one of the valid status values
     *
     * @param parts The split and trimmed line parts.
     * @param line The original line for error reporting.
     * @param internships The list to add the parsed internship to.
     * @return Error message if parsing/validation failed, null if successful.
     */
    private String parseAndValidateFields(String[] parts, String line, ArrayList<Internship> internships) {
        String company = parts[IDX_COMPANY];
        String role = parts[IDX_ROLE];
        String deadlineStr = parts[IDX_DEADLINE];

        // Validate non-empty company and role
        if (company.isEmpty() || role.isEmpty()) {
            logger.warning("Empty company or role in line: " + line);
            return "Warning: Skipped line with empty company or role: " + line;
        }

        // Validate company and role length does not exceed limits
        if (company.length() > Ui.COMPANY_MAXLEN) {
            logger.warning("Company name too long in line: " + line);
            return "Warning: Skipped line with company name exceeding "+ Ui.COMPANY_MAXLEN + " characters: " + line;
        }
        if (role.length() > Ui.ROLE_MAXLEN) {
            logger.warning("Role name too long in line: " + line);
            return "Warning: Skipped line with role name exceeding "+ Ui.ROLE_MAXLEN + " characters: " + line;
        }

        // Parse pay
        int pay;
        try {
            pay = Integer.parseInt(parts[IDX_PAY]);
        } catch (NumberFormatException e) {
            logger.warning("Invalid pay format in line: " + line + " - " + e.getMessage());
            return "Warning: Skipped line with invalid pay format: " + line;
        }

        // Validate pay is non-negative
        if (pay < 0) {
            logger.warning("Negative pay in line: " + line + " - pay: " + pay);
            return "Warning: Skipped line with negative pay amount: " + line;
        }

        String status = parts[IDX_STATUS];
        // Validate status
        if (!Internship.isValidStatus(status)) {
            logger.warning("Invalid status in line: " + line + " - status: " + status);
            return "Warning: Skipped line with invalid status: " + line;
        }

        // Parse and validate date
        Date deadline;
        try {
            deadline = DateFormatter.parse(deadlineStr);
        } catch (InternityException e) {
            logger.warning("Invalid date in line: " + line + " - " + e.getMessage());
            return "Warning: Skipped line - " + e.getMessage() + ": " + line;
        }

        // Create and add internship
        Internship internship = new Internship(company, role, deadline, pay);
        internship.setStatus(status);
        internships.add(internship);

        return null;
    }

    /**
     * Saves internships to the storage file.
     * The first line contains "Username (in line below):"
     * The second line contains the actual username.
     * Followed by internship entries on subsequent lines.
     *
     * @param internships The list of internships to save.
     * @throws InternityException If there is an error writing to the file.
     */
    public void save(ArrayList<Internship> internships) throws InternityException {
        assert internships != null : "Internships list cannot be null";

        logger.info("Saving " + internships.size() + " internships to: " + filePath);

        try {
            // Create parent directories if they don't exist
            if (filePath.getParent() != null) {
                Files.createDirectories(filePath.getParent());
                logger.warning("Created parent directories for: " + filePath);
            }

            try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filePath.toFile())))) {
                // Write username header and value
                pw.println("Username (in line below):");
                String username = InternshipList.getUsername();
                pw.println(username != null ? username : "");

                // Write internships
                for (Internship internship : internships) {
                    pw.println(formatInternshipForFile(internship));
                }
            }
            logger.info("Successfully saved " + internships.size() + " internships");
        } catch (IOException e) {
            logger.severe("Failed to save internships to " + filePath + ": " + e.getMessage());
            throw new InternityException("Could not save internships: " + e.getMessage());
        }
    }

    /**
     * Formats an internship for storage in the file.
     *
     * @param internship The internship to format.
     * @return A pipe-delimited string representation of the internship.
     */
    private String formatInternshipForFile(Internship internship) {
        assert internship != null : "Internship to format cannot be null";
        assert internship.getCompany() != null : "Company cannot be null";
        assert internship.getRole() != null : "Role cannot be null";
        assert internship.getDeadline() != null : "Deadline cannot be null";
        assert internship.getStatus() != null : "Status cannot be null";

        return internship.getCompany() + " | "
                + internship.getRole() + " | "
                + internship.getDeadline().toString() + " | "
                + internship.getPay() + " | "
                + internship.getStatus();
    }
}
