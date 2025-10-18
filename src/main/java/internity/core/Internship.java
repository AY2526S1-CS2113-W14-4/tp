package internity.core;

import java.util.Set;

public class Internship {
    private static final Set<String> VALID_STATUSES = Set.of(
            "Pending", "Interested", "Applied", "Interviewing", "Offer", "Accepted", "Rejected"
    );

    private String company;
    private String role;
    private Date deadline;
    private int pay;
    private String status;

    public Internship(String company, String role, Date deadline, int pay) {
        this.company = company;
        this.role = role;
        this.deadline = deadline;
        this.pay = pay;
        this.status = "Pending";
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Checks if the given status is valid.
     *
     * @param status The status to validate.
     * @return true if the status is valid, false otherwise.
     */
    public static boolean isValidStatus(String status) {
        return status != null && VALID_STATUSES.contains(status);
    }
}
