package internity.logic.commands;

import internity.core.InternityException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HelpCommandTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void execute_printsHelpMessage() throws InternityException {
        HelpCommand helpCommand = new HelpCommand();
        helpCommand.execute();

        assertTrue(outContent.toString().contains("Here are the available commands:"));
    }

    @Test
    void execute_containsAddCommand() throws InternityException {
        HelpCommand helpCommand = new HelpCommand();
        helpCommand.execute();

        assertTrue(outContent.toString().contains("add"));
    }

    @Test
    void execute_containsDeleteCommand() throws InternityException {
        HelpCommand helpCommand = new HelpCommand();
        helpCommand.execute();

        assertTrue(outContent.toString().contains("delete"));
    }

    @Test
    void execute_containsListCommand() throws InternityException {
        HelpCommand helpCommand = new HelpCommand();
        helpCommand.execute();

        assertTrue(outContent.toString().contains("list"));
    }

    @Test
    void execute_containsFindCommand() throws InternityException {
        HelpCommand helpCommand = new HelpCommand();
        helpCommand.execute();

        assertTrue(outContent.toString().contains("find"));
    }

    @Test
    void execute_containsUpdateCommand() throws InternityException {
        HelpCommand helpCommand = new HelpCommand();
        helpCommand.execute();

        assertTrue(outContent.toString().contains("update"));
    }

    @Test
    void execute_containsExitCommand() throws InternityException {
        HelpCommand helpCommand = new HelpCommand();
        helpCommand.execute();

        assertTrue(outContent.toString().contains("exit"));
    }

    @Test
    void execute_doesNotThrow() {
        HelpCommand helpCommand = new HelpCommand();
        assertDoesNotThrow(helpCommand::execute);
    }
}
