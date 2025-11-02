package internity.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import internity.core.Date;
import internity.core.InternityException;
import internity.core.Internship;
import internity.core.InternshipList;

class StorageTest {

    @TempDir
    Path tempDir;

    private Storage storage;
    private String testFilePath;
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;

    @BeforeEach
    void setUp() {
        testFilePath = tempDir.resolve("test_internships.txt").toString();
        storage = new Storage(testFilePath);
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        InternshipList.clear();
        InternshipList.setUsername(null);
    }

    @Test
    void save_emptyList_createsEmptyFile() throws InternityException, IOException {
        ArrayList<Internship> internships = new ArrayList<>();
        storage.save(internships);

        assertTrue(Files.exists(Path.of(testFilePath)));
        List<String> lines = Files.readAllLines(Path.of(testFilePath));
        assertEquals(2, lines.size()); // Username header + username line
        assertEquals("Username (in line below):", lines.get(0));
    }

    @Test
    void save_singleInternship_writesCorrectFormat() throws InternityException, IOException {
        ArrayList<Internship> internships = new ArrayList<>();
        Internship internship = new Internship("Google", "SWE", new Date(15, 3, 2025), 6000);
        internships.add(internship);

        storage.save(internships);

        List<String> lines = Files.readAllLines(Path.of(testFilePath));
        assertEquals(3, lines.size()); // Username header + username + 1 internship
        assertEquals("Username (in line below):", lines.get(0));
        assertEquals("Google | SWE | 15-03-2025 | 6000 | Pending", lines.get(2));
    }

    @Test
    void save_multipleInternships_writesAllEntries() throws InternityException, IOException {
        ArrayList<Internship> internships = new ArrayList<>();
        internships.add(new Internship("Google", "SWE", new Date(15, 3, 2025), 6000));
        internships.add(new Internship("Meta", "Data Scientist", new Date(20, 4, 2025), 7000));
        internships.add(new Internship("Amazon", "DevOps", new Date(1, 5, 2025), 5500));

        storage.save(internships);

        List<String> lines = Files.readAllLines(Path.of(testFilePath));
        assertEquals(5, lines.size()); // Username header + username + 3 internships
        assertEquals("Username (in line below):", lines.get(0));
        assertEquals("Google | SWE | 15-03-2025 | 6000 | Pending", lines.get(2));
        assertEquals("Meta | Data Scientist | 20-04-2025 | 7000 | Pending", lines.get(3));
        assertEquals("Amazon | DevOps | 01-05-2025 | 5500 | Pending", lines.get(4));
    }

    @Test
    void save_withCustomStatus_preservesStatus() throws InternityException, IOException {
        ArrayList<Internship> internships = new ArrayList<>();
        Internship internship = new Internship("Tesla", "ML Engineer", new Date(10, 6, 2025), 8000);
        internship.setStatus("Accepted");
        internships.add(internship);

        storage.save(internships);

        List<String> lines = Files.readAllLines(Path.of(testFilePath));
        assertEquals(3, lines.size()); // Username header + username + 1 internship
        assertEquals("Username (in line below):", lines.get(0));
        assertTrue(lines.get(2).endsWith("Accepted"));
    }

    /**
     * Tests that save() creates multiple levels of missing parent directories.
     * Uses nested path (data/nested/) to verify Storage uses Files.createDirectories()
     * which creates all missing parents, not Files.createDirectory() which would fail.
     * While the app only needs one level (./data/), this tests robustness for any path.
     */
    @Test
    void save_nonExistentDirectory_createsDirectory() throws InternityException {
        String nestedPath = tempDir.resolve("data/nested/internships.txt").toString();
        Storage nestedStorage = new Storage(nestedPath);
        ArrayList<Internship> internships = new ArrayList<>();
        internships.add(new Internship("Apple", "iOS Dev", new Date(5, 7, 2025), 7500));

        nestedStorage.save(internships);

        assertTrue(Files.exists(Path.of(nestedPath)));
    }

    @Test
    void load_nonExistentFile_returnsEmptyList() throws InternityException {
        String nonExistentPath = tempDir.resolve("does_not_exist.txt").toString();
        Storage nonExistentStorage = new Storage(nonExistentPath);

        ArrayList<Internship> internships = nonExistentStorage.load();

        assertEquals(0, internships.size());
    }

    @Test
    void load_emptyFile_throwsException() throws IOException {
        Files.createFile(Path.of(testFilePath));

        InternityException thrown = org.junit.jupiter.api.Assertions.assertThrows(
                InternityException.class,
                () -> storage.load()
        );

        assertEquals("Invalid storage file format", thrown.getMessage());
    }

    @Test
    void load_validSingleEntry_loadsCorrectly() throws InternityException, IOException {
        String content = "Username (in line below):\n"
                + "TestUser\n"
                + "Google | SWE | 15-03-2025 | 6000 | Pending\n";
        Files.writeString(Path.of(testFilePath), content);

        ArrayList<Internship> internships = storage.load();

        assertEquals(1, internships.size());
        assertEquals("Google", internships.get(0).getCompany());
        assertEquals("SWE", internships.get(0).getRole());
        assertEquals(6000, internships.get(0).getPay());
        assertEquals("Pending", internships.get(0).getStatus());
    }

    @Test
    void load_multipleValidEntries_loadsAll() throws InternityException, IOException {
        String content = "Username (in line below):\n"
                + "TestUser\n"
                + "Google | SWE | 15-03-2025 | 6000 | Pending\n"
                + "Meta | Data Scientist | 20-04-2025 | 7000 | Accepted\n"
                + "Amazon | DevOps | 01-05-2025 | 5500 | Rejected\n";
        Files.writeString(Path.of(testFilePath), content);

        ArrayList<Internship> internships = storage.load();

        assertEquals(3, internships.size());
        // Verify first entry completely
        assertEquals("Google", internships.get(0).getCompany());
        assertEquals("SWE", internships.get(0).getRole());
        assertEquals(6000, internships.get(0).getPay());
        assertEquals("Pending", internships.get(0).getStatus());
        // Verify second entry completely
        assertEquals("Meta", internships.get(1).getCompany());
        assertEquals("Data Scientist", internships.get(1).getRole());
        assertEquals(7000, internships.get(1).getPay());
        assertEquals("Accepted", internships.get(1).getStatus());
        // Verify third entry completely
        assertEquals("Amazon", internships.get(2).getCompany());
        assertEquals("DevOps", internships.get(2).getRole());
        assertEquals(5500, internships.get(2).getPay());
        assertEquals("Rejected", internships.get(2).getStatus());
    }

    @Test
    void load_fewerFields_skipsLine() throws InternityException, IOException {
        String content = "Username (in line below):\n"
                + "TestUser\n"
                + "Google | SWE | 15-03-2025 | 6000 | Pending\n"
                + "Meta | Data Scientist | 20-04-2025\n"  // Missing pay and status
                + "Amazon | DevOps | 01-05-2025 | 5500 | Rejected\n";
        Files.writeString(Path.of(testFilePath), content);

        ArrayList<Internship> internships = storage.load();

        assertEquals(2, internships.size());
        assertEquals("Google", internships.get(0).getCompany());
        assertEquals("Amazon", internships.get(1).getCompany());
        assertTrue(errContent.toString().contains("Warning: Skipped line with invalid number of fields:"));
    }

    @Test
    void load_invalidPayFormat_skipsLine() throws InternityException, IOException {
        String content = "Username (in line below):\n"
                + "TestUser\n"
                + "Google | SWE | 15-03-2025 | 6000 | Pending\n"
                + "Meta | Data Scientist | 20-04-2025 | NotANumber | Accepted\n"
                + "Amazon | DevOps | 01-05-2025 | 5500 | Rejected\n";
        Files.writeString(Path.of(testFilePath), content);

        ArrayList<Internship> internships = storage.load();

        assertEquals(2, internships.size());
        assertTrue(errContent.toString().contains("Warning: Skipped line with invalid pay format"));
    }

    @Test
    void load_negativePay_skipsLine() throws InternityException, IOException {
        String content = "Username (in line below):\n"
                + "TestUser\n"
                + "Google | SWE | 15-03-2025 | 6000 | Pending\n"
                + "Meta | Data Scientist | 20-04-2025 | -1000 | Accepted\n"  // Negative pay
                + "Amazon | DevOps | 01-05-2025 | 5500 | Rejected\n";
        Files.writeString(Path.of(testFilePath), content);

        ArrayList<Internship> internships = storage.load();

        assertEquals(2, internships.size());
        assertEquals("Google", internships.get(0).getCompany());
        assertEquals("Amazon", internships.get(1).getCompany());
        assertTrue(errContent.toString().contains("Warning: Skipped line with negative pay amount"));
    }

    @Test
    void load_invalidStatus_skipsLine() throws InternityException, IOException {
        String content =
                "Username (in line below):\n"
                        + "TestUser\n"
                        + "Google | SWE | 15-03-2025 | 6000 | Pending\n"
                        + "Meta | Data Scientist | 20-04-2025 | 7000 | InvalidStatus\n"  // Invalid status
                        + "Amazon | DevOps | 01-05-2025 | 5500 | Rejected\n";
        Files.writeString(Path.of(testFilePath), content);

        ArrayList<Internship> internships = storage.load();

        assertEquals(2, internships.size());
        assertEquals("Google", internships.get(0).getCompany());
        assertEquals("Amazon", internships.get(1).getCompany());
        assertTrue(errContent.toString().contains("Warning: Skipped line with invalid status"));
    }

    @Test
    void load_invalidDateFormat_skipsLine() throws InternityException, IOException {
        String content = "Username (in line below):\n"
                + "TestUser\n"
                + "Google | SWE | 15-03-2025 | 6000 | Pending\n"
                + "Meta | Data Scientist | 2025/04/20 | 7000 | Accepted\n"  // Wrong date format
                + "Amazon | DevOps | 01-05-2025 | 5500 | Rejected\n";
        Files.writeString(Path.of(testFilePath), content);

        ArrayList<Internship> internships = storage.load();

        assertEquals(2, internships.size());
        String stderr = errContent.toString();
        String expectedError = "Warning: Skipped line - Invalid date format. "
                + "Expected dd-MM-yyyy (e.g. 08-10-2025):";
        assertTrue(stderr.contains(expectedError));
    }

    @Test
    void load_impossibleDate_skipsLine() throws InternityException, IOException {
        String content = "Username (in line below):\n"
                + "TestUser\n"
                + "Google | SWE | 15-03-2025 | 6000 | Pending\n"
                + "Meta | Data Scientist | 31-02-2025 | 7000 | Accepted\n"  // Feb 31 doesn't exist
                + "Amazon | DevOps | 01-05-2025 | 5500 | Rejected\n";
        Files.writeString(Path.of(testFilePath), content);

        ArrayList<Internship> internships = storage.load();

        assertEquals(2, internships.size());
        assertEquals("Google", internships.get(0).getCompany());
        assertEquals("Amazon", internships.get(1).getCompany());
        String stderr = errContent.toString();
        String expectedError = "Warning: Skipped line - Invalid date format. "
                + "Expected dd-MM-yyyy (e.g. 08-10-2025):";
        assertTrue(stderr.contains(expectedError));
    }

    @Test
    void load_nonNumericDate_skipsLine() throws InternityException, IOException {
        String content = "Username (in line below):\n"
                + "TestUser\n"
                + "Google | SWE | 15-03-2025 | 6000 | Pending\n"
                + "Meta | Data Scientist | 1a-03-2025 | 7000 | Accepted\n"  // Contains 'a' in day
                + "Amazon | DevOps | 01-05-2025 | 5500 | Rejected\n";
        Files.writeString(Path.of(testFilePath), content);

        ArrayList<Internship> internships = storage.load();

        assertEquals(2, internships.size());
        assertEquals("Google", internships.get(0).getCompany());
        assertEquals("Amazon", internships.get(1).getCompany());
        String stderr = errContent.toString();
        String expectedError = "Warning: Skipped line - Invalid date format. "
                + "Expected dd-MM-yyyy (e.g. 08-10-2025):";
        assertTrue(stderr.contains(expectedError));
    }

    @Test
    void load_zeroPay_loadsCorrectly() throws InternityException, IOException {
        String content = "Username (in line below):\n"
                + "TestUser\n"
                + "Google | SWE Intern | 15-03-2025 | 0 | Pending\n";
        Files.writeString(Path.of(testFilePath), content);

        ArrayList<Internship> internships = storage.load();

        assertEquals(1, internships.size());
        assertEquals("Google", internships.get(0).getCompany());
        assertEquals("SWE Intern", internships.get(0).getRole());
        assertEquals(0, internships.get(0).getPay());
        assertEquals("Pending", internships.get(0).getStatus());
    }

    @Test
    void load_emptyCompany_skipsLine() throws InternityException, IOException {
        String content = "Username (in line below):\n"
                + "TestUser\n"
                + "Google | SWE | 15-03-2025 | 6000 | Pending\n"
                + " | Data Scientist | 20-04-2025 | 7000 | Accepted\n"  // Empty company
                + "Amazon | DevOps | 01-05-2025 | 5500 | Rejected\n";
        Files.writeString(Path.of(testFilePath), content);

        ArrayList<Internship> internships = storage.load();

        assertEquals(2, internships.size());
        assertEquals("Google", internships.get(0).getCompany());
        assertEquals("Amazon", internships.get(1).getCompany());
        assertTrue(errContent.toString().contains("Warning: Skipped line with empty company or role"));
    }

    @Test
    void load_emptyRole_skipsLine() throws InternityException, IOException {
        String content = "Username (in line below):\n"
                + "TestUser\n"
                + "Google | SWE | 15-03-2025 | 6000 | Pending\n"
                + "Meta |  | 20-04-2025 | 7000 | Accepted\n"  // Empty role
                + "Amazon | DevOps | 01-05-2025 | 5500 | Rejected\n";
        Files.writeString(Path.of(testFilePath), content);

        ArrayList<Internship> internships = storage.load();

        assertEquals(2, internships.size());
        assertEquals("Google", internships.get(0).getCompany());
        assertEquals("Amazon", internships.get(1).getCompany());
        assertTrue(errContent.toString().contains("Warning: Skipped line with empty company or role"));
    }

    @Test
    void load_extraFields_loadsFirstFiveFields() throws InternityException, IOException {
        String content = "Username (in line below):\n"
                + "TestUser\n"
                + "Google | SWE | 15-03-2025 | 6000 | Pending | Extra | MoreExtra\n";
        Files.writeString(Path.of(testFilePath), content);

        ArrayList<Internship> internships = storage.load();

        assertTrue(errContent.toString().contains("Warning: Skipped line with invalid number of fields"));
    }

    @Test
    void saveAndLoad_roundTrip_preservesData() throws InternityException {
        ArrayList<Internship> originalInternships = new ArrayList<>();
        originalInternships.add(new Internship("Google", "SWE", new Date(15, 3, 2025), 6000));
        originalInternships.add(new Internship("Meta", "Data Scientist", new Date(20, 4, 2025), 7000));
        originalInternships.get(1).setStatus("Accepted");

        storage.save(originalInternships);
        ArrayList<Internship> loadedInternships = storage.load();

        assertEquals(2, loadedInternships.size());
        assertEquals("Google", loadedInternships.get(0).getCompany());
        assertEquals("SWE", loadedInternships.get(0).getRole());
        assertEquals(6000, loadedInternships.get(0).getPay());
        assertEquals("Pending", loadedInternships.get(0).getStatus());
        assertEquals("Meta", loadedInternships.get(1).getCompany());
        assertEquals("Accepted", loadedInternships.get(1).getStatus());
    }

    @Test
    void load_extraWhitespace_trimsCorrectly() throws InternityException, IOException {
        String content = "Username (in line below):\n"
                + "TestUser\n"
                + "  Google  |  SWE  |  15-03-2025  |  6000  |  Pending  \n";
        Files.writeString(Path.of(testFilePath), content);

        ArrayList<Internship> internships = storage.load();

        assertEquals(1, internships.size());
        assertEquals("Google", internships.get(0).getCompany());
        assertEquals("SWE", internships.get(0).getRole());
    }

    @Test
    void load_multiWordFields_loadsCorrectly() throws InternityException, IOException {
        String content = "Username (in line below):\n"
                + "TestUser\n"
                + "Jane Street | Quantitative Researcher | 25-12-2025 | 10000 | Interviewing\n";
        Files.writeString(Path.of(testFilePath), content);

        ArrayList<Internship> internships = storage.load();

        assertEquals(1, internships.size());
        assertEquals("Jane Street", internships.get(0).getCompany());
        assertEquals("Quantitative Researcher", internships.get(0).getRole());
        assertEquals("Interviewing", internships.get(0).getStatus());
    }

    // Username persistence tests

    @Test
    void save_withUsername_writesUsernameHeader() throws InternityException, IOException {
        InternshipList.setUsername("JohnDoe");
        ArrayList<Internship> internships = new ArrayList<>();
        internships.add(new Internship("Google", "SWE", new Date(15, 3, 2025), 6000));

        storage.save(internships);

        List<String> lines = Files.readAllLines(Path.of(testFilePath));
        assertEquals(3, lines.size());
        assertEquals("Username (in line below):", lines.get(0));
        assertEquals("JohnDoe", lines.get(1));
        assertEquals("Google | SWE | 15-03-2025 | 6000 | Pending", lines.get(2));
    }

    @Test
    void save_withNullUsername_writesEmptyUsername() throws InternityException, IOException {
        InternshipList.setUsername(null);
        ArrayList<Internship> internships = new ArrayList<>();
        internships.add(new Internship("Meta", "Data Scientist", new Date(20, 4, 2025), 7000));

        storage.save(internships);

        List<String> lines = Files.readAllLines(Path.of(testFilePath));
        assertEquals(3, lines.size());
        assertEquals("Username (in line below):", lines.get(0));
        assertEquals("", lines.get(1));
        assertEquals("Meta | Data Scientist | 20-04-2025 | 7000 | Pending", lines.get(2));
    }

    @Test
    void save_withEmptyUsername_writesEmptyUsername() throws InternityException, IOException {
        InternshipList.setUsername("");
        ArrayList<Internship> internships = new ArrayList<>();

        storage.save(internships);

        List<String> lines = Files.readAllLines(Path.of(testFilePath));
        assertEquals(2, lines.size());
        assertEquals("Username (in line below):", lines.get(0));
        assertEquals("", lines.get(1));
    }

    @Test
    void save_usernameWithSpaces_trimsAndSaves() throws InternityException, IOException {
        InternshipList.setUsername("John Doe");
        ArrayList<Internship> internships = new ArrayList<>();

        storage.save(internships);

        List<String> lines = Files.readAllLines(Path.of(testFilePath));
        assertEquals(2, lines.size());
        assertEquals("Username (in line below):", lines.get(0));
        assertEquals("John Doe", lines.get(1));
    }

    @Test
    void load_withValidUsername_loadsUsername() throws InternityException, IOException {
        String content = "Username (in line below):\n"
                + "Alice\n"
                + "Google | SWE | 15-03-2025 | 6000 | Pending\n";
        Files.writeString(Path.of(testFilePath), content);

        ArrayList<Internship> internships = storage.load();

        assertEquals(1, internships.size());
        assertEquals("Alice", InternshipList.getUsername());
        assertEquals("Google", internships.get(0).getCompany());
    }

    @Test
    void load_withEmptyUsername_loadsEmptyUsername() throws InternityException, IOException {
        String content = "Username (in line below):\n"
                + "\n"
                + "Meta | Data Scientist | 20-04-2025 | 7000 | Accepted\n";
        Files.writeString(Path.of(testFilePath), content);

        ArrayList<Internship> internships = storage.load();

        assertEquals(1, internships.size());
        assertNull(InternshipList.getUsername());
        assertEquals("Meta", internships.get(0).getCompany());
    }

    @Test
    void load_usernameWithWhitespace_trimsUsername() throws InternityException, IOException {
        String content = "Username (in line below):\n"
                + "  BobSmith  \n"
                + "Amazon | DevOps | 01-05-2025 | 5500 | Rejected\n";
        Files.writeString(Path.of(testFilePath), content);

        ArrayList<Internship> internships = storage.load();

        assertEquals(1, internships.size());
        assertEquals("BobSmith", InternshipList.getUsername());
    }

    @Test
    void load_usernameWithMultipleWords_loadsCorrectly() throws InternityException, IOException {
        String content = "Username (in line below):\n"
                + "Jane Marie Doe\n"
                + "Tesla | ML Engineer | 10-06-2025 | 8000 | Pending\n";
        Files.writeString(Path.of(testFilePath), content);

        ArrayList<Internship> internships = storage.load();

        assertEquals(1, internships.size());
        assertEquals("Jane Marie Doe", InternshipList.getUsername());
    }

    @Test
    void load_missingUsernameHeader_throwsException() throws IOException {
        String content = "Google | SWE | 15-03-2025 | 6000 | Pending\n";
        Files.writeString(Path.of(testFilePath), content);

        InternityException thrown = org.junit.jupiter.api.Assertions.assertThrows(
                InternityException.class,
                () -> storage.load()
        );

        assertEquals("Invalid storage file format", thrown.getMessage());
    }

    @Test
    void load_invalidUsernameHeader_throwsException() throws IOException {
        String content = "Username:\n"
                + "JohnDoe\n"
                + "Google | SWE | 15-03-2025 | 6000 | Pending\n";
        Files.writeString(Path.of(testFilePath), content);

        InternityException thrown = org.junit.jupiter.api.Assertions.assertThrows(
                InternityException.class,
                () -> storage.load()
        );

        assertEquals("Invalid storage file format", thrown.getMessage());
    }

    @Test
    void saveAndLoad_usernameRoundTrip_preservesUsername() throws InternityException {
        InternshipList.setUsername("TestUser123");
        ArrayList<Internship> originalInternships = new ArrayList<>();
        originalInternships.add(new Internship("Google", "SWE", new Date(15, 3, 2025), 6000));
        originalInternships.add(new Internship("Meta", "Data Scientist", new Date(20, 4, 2025), 7000));

        storage.save(originalInternships);

        // Clear username to ensure it's loaded from file
        InternshipList.setUsername(null);
        ArrayList<Internship> loadedInternships = storage.load();

        assertEquals("TestUser123", InternshipList.getUsername());
        assertEquals(2, loadedInternships.size());
        assertEquals("Google", loadedInternships.get(0).getCompany());
        assertEquals("Meta", loadedInternships.get(1).getCompany());
    }

    @Test
    void save_emptyListWithUsername_writesOnlyUsername() throws InternityException, IOException {
        InternshipList.setUsername("EmptyListUser");
        ArrayList<Internship> internships = new ArrayList<>();

        storage.save(internships);

        List<String> lines = Files.readAllLines(Path.of(testFilePath));
        assertEquals(2, lines.size());
        assertEquals("Username (in line below):", lines.get(0));
        assertEquals("EmptyListUser", lines.get(1));
    }

    @Test
    void load_onlyUsernameNoInternships_loadsUsername() throws InternityException, IOException {
        String content = "Username (in line below):\n"
                + "OnlyUsername\n";
        Files.writeString(Path.of(testFilePath), content);

        ArrayList<Internship> internships = storage.load();

        assertEquals(0, internships.size());
        assertEquals("OnlyUsername", InternshipList.getUsername());
    }

    @Test
    void saveAndLoad_changeUsername_newUsernameIsPersisted() throws InternityException, IOException {
        // Initial save with username "OriginalUser"
        InternshipList.setUsername("OriginalUser");
        ArrayList<Internship> internships = new ArrayList<>();
        internships.add(new Internship("Google", "SWE", new Date(15, 3, 2025), 6000));
        storage.save(internships);

        // Load and verify original username
        InternshipList.clear();
        InternshipList.setUsername(null);
        ArrayList<Internship> loadedInternships1 = storage.load();
        assertEquals("OriginalUser", InternshipList.getUsername());
        assertEquals(1, loadedInternships1.size());

        // Change username and save again with the same internships
        InternshipList.setUsername("NewUser");
        storage.save(loadedInternships1);

        // Load again and verify new username is persisted
        InternshipList.clear();
        InternshipList.setUsername(null); // Simulate fresh start
        ArrayList<Internship> loadedInternships2 = storage.load(); // Simulate load from file

        assertEquals("NewUser", InternshipList.getUsername());
        assertEquals(1, loadedInternships2.size());
        assertEquals("Google", loadedInternships2.get(0).getCompany());

        // Verify file content directly
        List<String> lines = Files.readAllLines(Path.of(testFilePath));
        assertEquals("Username (in line below):", lines.get(0));
        assertEquals("NewUser", lines.get(1));
        assertEquals("Google | SWE | 15-03-2025 | 6000 | Pending", lines.get(2));
    }

    @Test
    void saveAndLoad_companyWithPipe_preservesPipe() throws InternityException {
        ArrayList<Internship> internships = new ArrayList<>();
        internships.add(new Internship("Comp|any", "SWE", new Date(15, 3, 2025), 6000));
        storage.save(internships);

        InternshipList.clear();
        ArrayList<Internship> loadedInternships = storage.load();

        assertEquals(1, loadedInternships.size());
        assertEquals("Comp|any", loadedInternships.get(0).getCompany());
        assertEquals("SWE", loadedInternships.get(0).getRole());
    }

    @Test
    void saveAndLoad_roleWithPipe_preservesPipe() throws InternityException {
        ArrayList<Internship> internships = new ArrayList<>();
        internships.add(new Internship("Google", "SW|E", new Date(15, 3, 2025), 6000));
        storage.save(internships);

        InternshipList.clear();
        ArrayList<Internship> loadedInternships = storage.load();

        assertEquals(1, loadedInternships.size());
        assertEquals("Google", loadedInternships.get(0).getCompany());
        assertEquals("SW|E", loadedInternships.get(0).getRole());
    }

    @Test
    void load_deadlineWithPipe_skipsLine() throws InternityException, IOException {
        String content = "Username (in line below):\n\n"
                + "Google | SWE | 15-03|2025 | 6000 | Pending\n";
        Files.writeString(Path.of(testFilePath), content);

        ArrayList<Internship> internships = storage.load();

        assertEquals(0, internships.size());
    }

    @Test
    void load_payWithPipe_skipsLine() throws InternityException, IOException {
        String content = "Username (in line below):\n\n"
                + "Google | SWE | 15-03-2025 | 60|00 | Pending\n";
        Files.writeString(Path.of(testFilePath), content);

        ArrayList<Internship> internships = storage.load();

        assertEquals(0, internships.size());
    }

    @Test
    void load_statusWithPipe_skipsLine() throws InternityException, IOException {
        String content = "Username (in line below):\n\n"
                + "Google | SWE | 15-03-2025 | 6000 | Pend|ing\n";
        Files.writeString(Path.of(testFilePath), content);

        ArrayList<Internship> internships = storage.load();

        assertEquals(0, internships.size());
    }

    private static String buildAsciiString(int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i <= end; i++) {
            sb.append((char) i);
        }
        return sb.toString();
    }

    @Test
    void load_asciiRole32to61_loadsCorrectly() throws InternityException {
        String role = "A"; // Start with a character as 32 is a space
        role += buildAsciiString(32, 60);
        ArrayList<Internship> internships = new ArrayList<>();
        internships.add(new Internship("Google", role, new Date(15, 3, 2025), 6000));
        storage.save(internships);

        ArrayList<Internship> loadedInternships = storage.load();

        assertEquals(1, loadedInternships.size());
        assertEquals("Google", loadedInternships.get(0).getCompany());
        assertEquals(role, loadedInternships.get(0).getRole());
        assertEquals(6000, loadedInternships.get(0).getPay());
        assertEquals("Pending", loadedInternships.get(0).getStatus());
    }

    @Test
    void load_asciiRole62to91_loadsCorrectly() throws InternityException {
        String role = buildAsciiString(61, 90);
        ArrayList<Internship> internships = new ArrayList<>();
        internships.add(new Internship("Google", role, new Date(15, 3, 2025), 6000));
        storage.save(internships);

        ArrayList<Internship> loadedInternships = storage.load();

        assertEquals(1, loadedInternships.size());
        assertEquals("Google", loadedInternships.get(0).getCompany());
        assertEquals(role, loadedInternships.get(0).getRole());
        assertEquals(6000, loadedInternships.get(0).getPay());
        assertEquals("Pending", loadedInternships.get(0).getStatus());
    }

    @Test
    void load_asciiRole92to121_loadsCorrectly() throws InternityException {
        String role = buildAsciiString(91, 120);
        ArrayList<Internship> internships = new ArrayList<>();
        internships.add(new Internship("Google", role, new Date(15, 3, 2025), 6000));
        storage.save(internships);

        ArrayList<Internship> loadedInternships = storage.load();

        assertEquals(1, loadedInternships.size());
        assertEquals("Google", loadedInternships.get(0).getCompany());
        assertEquals(role, loadedInternships.get(0).getRole());
        assertEquals(6000, loadedInternships.get(0).getPay());
        assertEquals("Pending", loadedInternships.get(0).getStatus());
    }

    @Test
    void load_asciiRole122to126_loadsCorrectly() throws InternityException {
        String role = buildAsciiString(121, 126);
        ArrayList<Internship> internships = new ArrayList<>();
        internships.add(new Internship("Google", role, new Date(15, 3, 2025), 6000));
        storage.save(internships);

        ArrayList<Internship> loadedInternships = storage.load();

        assertEquals(1, loadedInternships.size());
        assertEquals("Google", loadedInternships.get(0).getCompany());
        assertEquals(role, loadedInternships.get(0).getRole());
        assertEquals(6000, loadedInternships.get(0).getPay());
        assertEquals("Pending", loadedInternships.get(0).getStatus());
    }
}
