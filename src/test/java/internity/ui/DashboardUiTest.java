package internity.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import internity.core.Date;
import internity.core.InternityException;
import internity.core.Internship;
import internity.core.InternshipList;

class DashboardUiTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));

        InternshipList.clear();
        InternshipList.setUsername("TestUser");

        InternshipList.add(new Internship("Google", "SWE", new Date(1, 1, 2025), 8000));
        InternshipList.add(new Internship("Microsoft", "Intern", new Date(15, 12, 2025), 5000));
    }

    @Test
    void printDashboard_withPastDeadlineInternships_printsExpectedInfo() throws InternityException {
        InternshipList.clear();
        Date date1 = new Date(1, 1, 2010); //past
        Date date2 = new Date(15, 12, 2010); // past, nearest
        InternshipList.add(new Internship("Google", "SWE", date1, 8000));
        InternshipList.add(new Internship("Microsoft", "Intern", date2, 5000));

        DashboardUi.printDashboard();

        String output = outContent.toString();

        assertTrue(output.contains("User: TestUser"), "Should print username");
        assertTrue(output.contains("Total Internships: 2"), "Should print correct count");
        assertTrue(output.contains("Nearest Deadline:")
                && output.contains("15-12-2010"), "Should show nearest (latest) deadline");
        assertTrue(output.contains("Applied")
                && output.contains("Pending"), "Should show status overview");
    }

    @Test
    void printDashboard_withFutureDeadlineInternships_printsExpectedInfo() throws InternityException {
        InternshipList.clear();
        Date date1 = new Date(1, 1, 2099); // future
        Date date2 = new Date(15, 12, 2099); // future, nearest
        InternshipList.add(new Internship("Google", "SWE", date1, 8000));
        InternshipList.add(new Internship("Microsoft", "Intern", date2, 5000));

        DashboardUi.printDashboard();

        String output = outContent.toString();

        assertTrue(output.contains("User: TestUser"), "Should print username");
        assertTrue(output.contains("Total Internships: 2"), "Should print correct count");
        assertTrue(output.contains("Nearest Deadline:")
                && output.contains("01-01-2099"), "Should show nearest (earliest) deadline");
        assertTrue(output.contains("Applied")
                && output.contains("Pending"), "Should show status overview");
    }

    @Test
    void printDashboard_withMixDeadlineInternships_printsExpectedInfo() throws InternityException {
        InternshipList.clear();
        Date date1 = new Date(1, 1, 2025); // past
        Date date2 = new Date(15, 12, 2099); // future
        InternshipList.add(new Internship("Google", "SWE", date1, 8000));
        InternshipList.add(new Internship("Microsoft", "Intern", date2, 5000));

        DashboardUi.printDashboard();

        String output = outContent.toString();

        assertTrue(output.contains("User: TestUser"), "Should print username");
        assertTrue(output.contains("Total Internships: 2"), "Should print correct count");
        assertTrue(output.contains("Nearest Deadline:")
                && output.contains("15-12-2099"), "Should show future deadline");
        assertTrue(output.contains("Applied")
                && output.contains("Pending"), "Should show status overview");
    }

    @Test
    void printDashboard_multipleInternshipsWithSameDeadline_printsExpectedInfo() throws InternityException {
        InternshipList.clear();
        Date date1 = new Date(1, 1, 2024);
        Date date2 = new Date(1, 1, 2012);
        InternshipList.add(new Internship("Google", "Coffee Intern", date2, 8000));
        InternshipList.add(new Internship("Google", "SWE", date1, 8000));
        InternshipList.add(new Internship("Microsoft", "Intern", date1, 5000));
        InternshipList.add(new Internship("Netflix", "Ops Intern", date1, 9000));

        DashboardUi.printDashboard();

        String output = outContent.toString();

        assertTrue(output.contains("User: TestUser"), "Should print username");
        assertTrue(output.contains("Nearest Deadline:")
                && output.contains("01-01-2024"), "Should show the nearest deadline");
        assertTrue(output.contains("Found 2 other"), "Should show count of internships with same nearest deadline");
    }

    @Test
    void printDashboard_noInternships_printsNoInternshipsMessage() throws InternityException {
        InternshipList.clear();
        outContent.reset();

        DashboardUi.printDashboard();

        String output = outContent.toString();
        assertTrue(output.contains("No internships found"), "Should indicate no internships");
        assertTrue(output.contains("Guest") || output.contains("User:"), "Should still print username");
    }
}
