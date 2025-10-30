package internity.core;

import java.util.Set;

// @@author {V1T0bh}
/**
 * Represents an internship entry in the Internity application.
 *
 * <p>
 * Each {@code Internship} object stores key details about an internship,
 * including the company name, role title, application deadline,
 * pay, and current application status.
 * </p>
 *
 * <p>
 * Internships are {@link Comparable} by their {@link #deadline}, allowing
 * sorting by date.
 * </p>
 *
 * <p>
 * Valid statuses include:
 * {@code "Pending"}, {@code "Interested"}, {@code "Applied"},
 * {@code "Interviewing"}, {@code "Offer"}, {@code "Accepted"}, and {@code "Rejected"}.
 * </p>
 */
public class Internship implements Comparable<Internship> {
    private static final Set<String> VALID_STATUSES = Set.of(
            "Pending", "Interested", "Applied", "Interviewing", "Offer", "Accepted", "Rejected"
    );
    private String company;
    private String role;
    private Date deadline;
    private int pay;
    private String status;

    /**
     * Constructs a new {@code Internship} with the given details.
     * <p>
     * The initial status is set to {@code "Pending"} by default.
     * </p>
     *
     * @param company  the name of the company offering the internship
     * @param role     the role or job title of the internship
     * @param deadline the application deadline
     * @param pay      the pay for the internship
     */
    public Internship(String company, String role, Date deadline, int pay) {
        this.company = company;
        this.role = role;
        this.deadline = deadline;
        this.pay = pay;
        this.status = "Pending";
    }

    /**
     * Returns the company offering this internship.
     *
     * @return the company name
     */
    public String getCompany() {
        return company;
    }

    /**
     * Updates the company name for this internship.
     *
     * @param company the new company name
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * Returns the role for this internship.
     *
     * @return the internship role
     */
    public String getRole() {
        return role;
    }

    /**
     * Updates the role for this internship.
     *
     * @param role the new role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Returns the application deadline.
     *
     * @return the deadline as a {@link Date} object
     */
    public Date getDeadline() {
        return deadline;
    }

    /**
     * Updates the application deadline.
     *
     * @param deadline the new application deadline
     */
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    /**
     * Returns the internship pay amount.
     *
     * @return the pay amount
     */
    public int getPay() {
        return pay;
    }

    /**
     * Updates the pay amount for this internship.
     *
     * @param pay the new pay amount
     */
    public void setPay(int pay) {
        this.pay = pay;
    }

    /**
     * Returns the current application status.
     *
     * @return the current status string
     */
    public String getStatus() {
        return status;
    }

    /**
     * Updates the application status for this internship.
     *
     * @param status the new status value
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Compares this internship with another based on their deadlines.
     * <p>
     * Earlier deadlines are considered "less than" later ones.
     * </p>
     *
     * @param o the other internship to compare with
     * @return a negative integer, zero, or a positive integer as this internship’s
     *         deadline is earlier than, equal to, or later than the specified internship’s deadline
     */
    @Override
    public int compareTo(Internship o) {
        return this.deadline.compareTo(o.getDeadline());
    }

    // @@author {BenyAlbatross}
    /**
     * Checks if the given status is valid.
     *
     * @param status The status to validate.
     * @return true if the status is valid, false otherwise.
     */
    public static boolean isValidStatus(String status) {
        return status != null && VALID_STATUSES.contains(status);
    }

    @Override
    public String toString() {
        return "  " + getCompany() + " - " + getRole()
                + " | Deadline: " + getDeadline()
                + " | Pay: " + getPay()
                + " | Status: " + getStatus();
    }
}
