package internity.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
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
}
