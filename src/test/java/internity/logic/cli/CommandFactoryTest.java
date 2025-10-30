package internity.logic.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import internity.logic.commands.AddCommand;
import internity.logic.commands.DashboardCommand;
import internity.logic.commands.DeleteCommand;
import internity.logic.commands.ExitCommand;
import internity.logic.commands.HelpCommand;
import internity.logic.commands.ListCommand;
import internity.logic.commands.UpdateCommand;
import internity.logic.commands.UsernameCommand;
import internity.logic.commands.FindCommand;
import internity.logic.commands.Command;

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
    void createCommand_find_returnsFindCommand() throws InternityException {
        Command command = factory.createCommand("find", "keyword");
        assertInstanceOf(FindCommand.class, command);
    }

    @Test
    void createCommand_username_returnsUsernameCommand() throws InternityException {
        Command command = factory.createCommand("username", "Walter White");
        assertInstanceOf(UsernameCommand.class, command);
    }

    @Test
    void createCommand_dashboard_returnsDashboardCommand() throws InternityException {
        Command command = factory.createCommand("dashboard", "");
        assertInstanceOf(DashboardCommand.class, command);
    }

    @Test
    void createCommand_dashboardWithBlankArgs_returnsDashboardCommand() throws InternityException {
        Command command = factory.createCommand("dashboard", "   ");
        assertInstanceOf(DashboardCommand.class, command);
    }

    @Test
    void createCommand_help_returnsHelpCommand() throws InternityException {
        Command command = factory.createCommand("help", "");
        assertInstanceOf(HelpCommand.class, command);
    }

    @Test
    void createCommand_helpWithBlankArgs_returnsHelpCommand() throws InternityException {
        Command command = factory.createCommand("help", "   ");
        assertInstanceOf(HelpCommand.class, command);
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

