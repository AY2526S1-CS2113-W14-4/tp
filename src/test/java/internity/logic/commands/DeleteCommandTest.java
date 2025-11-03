package internity.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import internity.core.Internship;
import internity.core.InternityException;
import internity.core.InternshipList;
import internity.core.Date;

class DeleteCommandTest {

    private InternshipList internshipList;

    @BeforeEach
    void setUp() {
        InternshipList.clear();
        // Add some test internships
        InternshipList.add(new Internship("Google", "SWE Intern", new Date(1, 1, 2025), 5000));
        InternshipList.add(new Internship("Meta", "Backend Intern", new Date(15, 2, 2025), 6000));
        InternshipList.add(new Internship("Amazon", "Frontend Intern", new Date(20, 3, 2025), 4500));
    }

    @Test
    void execute_validIndex_deletesInternship() throws InternityException {
        // Arrange: Delete internship at index 1
        DeleteCommand command = new DeleteCommand(1);
        int initialSize = InternshipList.size();

        // Act
        command.execute();

        // Assert
        assertEquals(initialSize - 1, InternshipList.size());
        assertEquals("Google", InternshipList.get(0).getCompany()); // First item unchanged
        assertEquals("Amazon", InternshipList.get(1).getCompany()); // Meta was removed, Amazon shifted
    }

    @Test
    void execute_negativeIndex_throwsException() {
        // Arrange: Delete internship at negative index -1
        DeleteCommand command = new DeleteCommand(-1);

        // Act & Assert
        InternityException exception = assertThrows(InternityException.class, command::execute);
        assertEquals("Invalid internship index: 0", exception.getMessage());
    }

    @Test
    void execute_indexTooLarge_throwsException() {
        // Arrange: Delete internship at idx 10, when max idx = 2
        DeleteCommand command = new DeleteCommand(10);

        // Act & Assert
        InternityException exception = assertThrows(InternityException.class, command::execute);
        assertEquals("Invalid internship index: 11", exception.getMessage());
    }

    @Test
    void execute_indexEqualToSize_throwsException() {
        // Deleting at idx == size_of_list is an off-by-one error
        // Arrange
        int size = InternshipList.size();
        DeleteCommand command = new DeleteCommand(size);

        // Act & Assert
        assertThrows(InternityException.class, command::execute);
    }

    @Test
    void isExit_returnsFalse() {
        // DeleteCommand should not terminate the program
        // Arrange
        DeleteCommand command = new DeleteCommand(0);

        // Act & Assert
        assertFalse(command.isExit());
    }
}
