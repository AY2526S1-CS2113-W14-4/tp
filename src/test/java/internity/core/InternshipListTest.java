package internity.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import internity.logic.commands.ListCommand;


class InternshipListTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        InternshipList.clear();
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void add_thenGet_returnsItemAtIndex() throws InternityException {
        InternshipList.add(null);
        InternshipList.add(null);

        assertDoesNotThrow(() -> InternshipList.get(0));
        assertDoesNotThrow(() -> InternshipList.get(1));

        assertNull(InternshipList.get(0));
        assertNull(InternshipList.get(1));
    }

    @Test
    void get_invalidIndex_throwsIndexOutOfBoundsException() {
        assertThrows(InternityException.class, () -> InternshipList.get(0));
        InternshipList.add(null);
        assertThrows(InternityException.class, () -> InternshipList.get(1));
        assertThrows(InternityException.class, () -> InternshipList.get(-1));
    }

    @Test
    void delete_removesGivenItem() throws InternityException {
        InternshipList.add(null);
        InternshipList.add(null);

        InternshipList.delete(0);

        assertDoesNotThrow(() -> InternshipList.get(0));
        assertThrows(InternityException.class, () -> InternshipList.get(1));
    }

    @Test
    public void listAll_whenEmpty_expectedOutcome() throws InternityException {
        InternshipList.listAll(ListCommand.OrderType.DEFAULT);
        assertTrue(outContent.toString().contains("Your internship list is currently empty."));
    }

    @Test
    public void listAll_withEntry_doesNotOutputNoInternshipsFound() throws Exception {
        Internship internship = new Internship("Company A", "Developer", new Date(1,1,2025), 5000);
        InternshipList.add(internship);
        InternshipList.listAll(ListCommand.OrderType.DEFAULT);

        String output = outContent.toString();
        assertFalse(output.contains("No internships found. Please add an internship first."));
    }

    @Test
    void updateCompany_validIndex_updatesFieldAndPrintsMessage() throws InternityException {
        outContent.reset();
        InternshipList.clear();
        Internship i = new Internship("OldCo", "Dev", new Date(1, 1, 2025), 5000);
        InternshipList.add(i);

        InternshipList.updateCompany(0, "NewCo");

        assertEquals("NewCo", InternshipList.get(0).getCompany());
        String out = outContent.toString();
        assertTrue(out.contains("Updated internship 1 company to: NewCo"));
    }

    @Test
    void updateRole_validIndex_updatesFieldAndPrintsMessage() throws InternityException {
        outContent.reset();
        InternshipList.clear();
        Internship i = new Internship("Co", "OldRole", new Date(1, 1, 2025), 5000);
        InternshipList.add(i);

        InternshipList.updateRole(0, "NewRole");

        assertEquals("NewRole", InternshipList.get(0).getRole());
        String out = outContent.toString();
        assertTrue(out.contains("Updated internship 1 role to: NewRole"));
    }

    @Test
    void updateDeadline_validIndex_updatesFieldAndPrintsMessage() throws InternityException {
        outContent.reset();
        InternshipList.clear();
        Internship i = new Internship("Co", "Role", new Date(1, 1, 2025), 5000);
        InternshipList.add(i);

        Date newDeadline = new Date(17, 9, 2025);
        InternshipList.updateDeadline(0, newDeadline);

        assertEquals("17-09-2025", InternshipList.get(0).getDeadline().toString());
        String out = outContent.toString();
        assertTrue(out.contains("Updated internship 1 deadline to: 17-09-2025"));
    }

    @Test
    void updatePay_validIndex_updatesFieldAndPrintsMessage() throws InternityException {
        outContent.reset();
        InternshipList.clear();
        Internship i = new Internship("Co", "Role", new Date(1, 1, 2025), 5000);
        InternshipList.add(i);

        InternshipList.updatePay(0, 9000);

        assertEquals(9000, InternshipList.get(0).getPay());
        String out = outContent.toString();
        assertTrue(out.contains("Updated internship 1 pay to: 9000"));
    }

    @Test
    void updateStatus_validIndex_updatesFieldAndPrintsMessage() throws InternityException {
        outContent.reset();
        InternshipList.clear();
        Internship i = new Internship("Co", "Role", new Date(1, 1, 2025), 5000);
        InternshipList.add(i);

        InternshipList.updateStatus(0, "Accepted");

        assertEquals("Accepted", InternshipList.get(0).getStatus());
        String out = outContent.toString();
        assertTrue(out.contains("Updated internship 1 status to: Accepted"));
    }

    @Test
    void updateCompany_invalidIndex_throwsInternityException() {
        outContent.reset();
        InternshipList.clear();
        Internship i = new Internship("Co", "Role", new Date(1, 1, 2025), 5000);
        InternshipList.add(i);

        assertThrows(InternityException.class, () -> InternshipList.updateCompany(1, "NewCo"));
    }

    @Test
    void updateRole_invalidIndex_throwsInternityException() {
        outContent.reset();
        InternshipList.clear();
        Internship i = new Internship("Co", "Role", new Date(1, 1, 2025), 5000);
        InternshipList.add(i);

        assertThrows(InternityException.class, () -> InternshipList.updateRole(-1, "NewRole"));
    }

    @Test
    void updateDeadline_invalidIndex_throwsInternityException() {
        outContent.reset();
        InternshipList.clear();
        Internship i = new Internship("Co", "Role", new Date(1, 1, 2025), 5000);
        InternshipList.add(i);

        Date newDeadline = new Date(17, 9, 2025);
        assertThrows(InternityException.class, () -> InternshipList.updateDeadline(42, newDeadline));
    }

    @Test
    void updatePay_invalidIndex_throwsInternityException() {
        outContent.reset();
        InternshipList.clear();
        Internship i = new Internship("Co", "Role", new Date(1, 1, 2025), 5000);
        InternshipList.add(i);

        assertThrows(InternityException.class, () -> InternshipList.updatePay(2, 9000));
    }

    public void sortInternships_sortAscending_expectedOutcome() throws InternityException {
        Internship older = new Internship("OlderCo", "Dev", new Date(1, 6, 2024), 0);
        Internship newer = new Internship("NewerCo", "Dev", new Date(1, 1, 2025), 0);

        InternshipList.add(newer);
        InternshipList.add(older);

        InternshipList.sortInternships(ListCommand.OrderType.ASCENDING); // ascending

        assertEquals("OlderCo", InternshipList.get(0).getCompany());
        assertEquals("NewerCo", InternshipList.get(1).getCompany());
    }

    @Test
    public void sortInternships_sortDescending_expectedOutcome() throws InternityException {
        Internship older = new Internship("OlderCo", "Dev", new Date(1, 6, 2024), 0);
        Internship newer = new Internship("NewerCo", "Dev", new Date(1, 1, 2025), 0);

        InternshipList.add(older);
        InternshipList.add(newer);

        InternshipList.sortInternships(ListCommand.OrderType.DESCENDING); // descending

        assertEquals("NewerCo", InternshipList.get(0).getCompany());
        assertEquals("OlderCo", InternshipList.get(1).getCompany());
    }
}
