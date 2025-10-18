package internity.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import internity.core.InternityException;
import org.junit.jupiter.api.Test;

class CommandFactoryTest {

    private final CommandFactory factory = new CommandFactory();

    @Test
    void createCommand_add_returnsAddCommand() throws InternityException {
        String args = "company/Umbrella Corp role/Researcher deadline/10-10-2025 pay/1000";
        Command command = factory.createCommand("add", args);
        assertInstanceOf(AddCommand.class, command);
    }

    @Test
    void createCommand_delete_returnsDeleteCommand() throws InternityException {
        Command command = factory.createCommand("delete", "1");
        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    void createCommand_update_returnsUpdateCommand() throws InternityException {
        Command command = factory.createCommand("update", "1 status/Pending");
        assertInstanceOf(UpdateCommand.class, command);
    }

    @Test
    void createCommand_list_returnsListCommand() throws InternityException {
        Command command = factory.createCommand("list", "");
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    void createCommand_exit_returnsExitCommand() throws InternityException {
        Command command = factory.createCommand("exit", "");
        assertInstanceOf(ExitCommand.class, command);
    }

    @Test
    void createCommand_unknownCommand_throwsInternityException() {
        InternityException exception = assertThrows(
                InternityException.class,
                () -> factory.createCommand("invalidCommand", "")
        );
        assertEquals("Unknown command: invalidCommand", exception.getMessage());
    }
}

