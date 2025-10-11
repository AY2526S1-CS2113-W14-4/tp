package internity.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import internity.core.Date;
import internity.core.InternityException;
import internity.core.Internship;
import internity.core.InternshipList;

class UpdateCommandTest {
    @BeforeEach
    void setUp() {
        InternshipList.clear();
        InternshipList.add(new Internship("Google", "SWE Intern", new Date(15, 11, 2025), 8000));
    }

    @Test
    void execute_validArgs_updatesStatusSuccessfully() throws InternityException {
        UpdateCommand command = new UpdateCommand(0, "Accepted");
        command.execute();
        assertEquals("Accepted", InternshipList.get(0).getStatus());
    }

    @Test
    void execute_invalidIndex_doesNotUpdateStatus() throws InternityException {
        UpdateCommand command = new UpdateCommand(5, "Rejected");
        assertThrows(InternityException.class, command::execute);
        assertEquals("Pending", InternshipList.get(0).getStatus());
    }

    @Test
    void isExit_always_returnsFalse() throws InternityException {
        UpdateCommand command = new UpdateCommand(0, "Accepted");
        assertFalse(command.isExit());
    }
}
