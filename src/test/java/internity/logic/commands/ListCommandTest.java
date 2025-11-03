package internity.logic.commands;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import internity.core.Date;
import internity.core.InternityException;
import internity.core.Internship;
import internity.core.InternshipList;

class ListCommandTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        InternshipList.clear();
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        InternshipList.clear();
    }

    @Test
    void execute_whenNoEntries_printsNoInternshipsFound() throws InternityException {
        InternshipList.clear();
        ListCommand listCommand = new ListCommand(ListCommand.OrderType.DEFAULT);
        listCommand.execute();

        assertTrue(outContent.toString().contains("Your internship list is currently empty."));
    }

    @Test
    void execute_withEntry_doesNotPrintNoInternshipsFound() throws InternityException {
        Internship internship = new Internship("Company A", "Developer", new Date(1,1,2025), 5000);
        InternshipList.add(internship); // dummy entry
        ListCommand listCommand = new ListCommand(ListCommand.OrderType.DEFAULT);
        listCommand.execute();

        assertFalse(outContent.toString().contains("No internships found. Please add an internship first."));
    }

    @Test
    void execute_doesNotThrow() {
        ListCommand listCommand = new ListCommand(ListCommand.OrderType.DEFAULT);
        assertDoesNotThrow(listCommand::execute);
    }

    @Test
    public void list_sortDoesNotAffectOriginalList() throws InternityException {
        InternshipList.clear();
        InternshipList.add(new Internship("Google", "SWE", new Date(10, 12, 2025), 9000));
        InternshipList.add(new Internship("Amazon", "Intern", new Date(15, 11, 2025), 8500));

        InternshipList.sortInternships(ListCommand.OrderType.ASCENDING);

        Internship original = InternshipList.get(0);
        assertEquals("Google", original.getCompany()); // insertion order retained
    }

    @Test
    public void list_defaultOrder_displaysInInsertionOrder() throws InternityException {
        InternshipList.clear();
        InternshipList.add(new Internship("Google", "SWE", new Date(10, 12, 2025), 9000));
        InternshipList.add(new Internship("Amazon", "Intern", new Date(15, 11, 2025), 8500));

        List<Internship> result = InternshipList.sortInternships(ListCommand.OrderType.DEFAULT);

        assertEquals("Google", result.get(0).getCompany());
        assertEquals("Amazon", result.get(1).getCompany());
    }

    @Test
    public void list_sortAscending_sortsByEarliestDeadline() throws InternityException {
        InternshipList.clear();
        InternshipList.add(new Internship("Google", "SWE", new Date(10, 12, 2025), 9000));
        InternshipList.add(new Internship("Amazon", "Intern", new Date(15, 11, 2025), 8500));

        List<Internship> result = InternshipList.sortInternships(ListCommand.OrderType.ASCENDING);

        assertEquals("Amazon", result.get(0).getCompany());
        assertEquals("Google", result.get(1).getCompany());
    }

    @Test
    public void list_sortDescending_sortsByLatestDeadline() throws InternityException {
        InternshipList.clear();
        InternshipList.add(new Internship("Google", "SWE", new Date(10, 12, 2025), 9000));
        InternshipList.add(new Internship("Amazon", "Intern", new Date(15, 11, 2025), 8500));

        List<Internship> result = InternshipList.sortInternships(ListCommand.OrderType.DESCENDING);

        assertEquals("Google", result.get(0).getCompany());
        assertEquals("Amazon", result.get(1).getCompany());
    }
}
