package internity.core;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    }

    // Test save() with empty list
    @Test
    void save_emptyList_createsEmptyFile() throws InternityException, IOException {
        ArrayList<Internship> internships = new ArrayList<>();
        storage.save(internships);

        assertTrue(Files.exists(Path.of(testFilePath)));
        List<String> lines = Files.readAllLines(Path.of(testFilePath));
        assertEquals(0, lines.size());
    }

    // Test save() with single internship
    @Test
    void save_singleInternship_writesCorrectFormat() throws InternityException, IOException {
        ArrayList<Internship> internships = new ArrayList<>();
        Internship internship = new Internship("Google", "SWE", new Date(15, 3, 2025), 6000);
        internships.add(internship);

        storage.save(internships);

        List<String> lines = Files.readAllLines(Path.of(testFilePath));
        assertEquals(1, lines.size());
        assertEquals("Google | SWE | 15-03-2025 | 6000 | Pending", lines.get(0));
    }

    // Test save() with multiple internships
    @Test
    void save_multipleInternships_writesAllEntries() throws InternityException, IOException {
        ArrayList<Internship> internships = new ArrayList<>();
        internships.add(new Internship("Google", "SWE", new Date(15, 3, 2025), 6000));
        internships.add(new Internship("Meta", "Data Scientist", new Date(20, 4, 2025), 7000));
        internships.add(new Internship("Amazon", "DevOps", new Date(1, 5, 2025), 5500));

        storage.save(internships);

        List<String> lines = Files.readAllLines(Path.of(testFilePath));
        assertEquals(3, lines.size());
        assertEquals("Google | SWE | 15-03-2025 | 6000 | Pending", lines.get(0));
        assertEquals("Meta | Data Scientist | 20-04-2025 | 7000 | Pending", lines.get(1));
        assertEquals("Amazon | DevOps | 01-05-2025 | 5500 | Pending", lines.get(2));
    }

    // Test save() with custom status
    @Test
    void save_withCustomStatus_preservesStatus() throws InternityException, IOException {
        ArrayList<Internship> internships = new ArrayList<>();
        Internship internship = new Internship("Tesla", "ML Engineer", new Date(10, 6, 2025), 8000);
        internship.setStatus("Accepted");
        internships.add(internship);

        storage.save(internships);

        List<String> lines = Files.readAllLines(Path.of(testFilePath));
        assertEquals(1, lines.size());
        assertTrue(lines.get(0).endsWith("Accepted"));
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

    // Test load() from non-existent file
    @Test
    void load_nonExistentFile_returnsEmptyList() throws InternityException {
        String nonExistentPath = tempDir.resolve("does_not_exist.txt").toString();
        Storage nonExistentStorage = new Storage(nonExistentPath);

        ArrayList<Internship> internships = nonExistentStorage.load();

        assertEquals(0, internships.size());
    }

    // Test load() from empty file
    @Test
    void load_emptyFile_returnsEmptyList() throws InternityException, IOException {
        Files.createFile(Path.of(testFilePath));

        ArrayList<Internship> internships = storage.load();

        assertEquals(0, internships.size());
    }

    // Test load() with valid single entry
    @Test
    void load_validSingleEntry_loadsCorrectly() throws InternityException, IOException {
        Files.writeString(Path.of(testFilePath), "Google | SWE | 15-03-2025 | 6000 | Pending\n");

        ArrayList<Internship> internships = storage.load();

        assertEquals(1, internships.size());
        assertEquals("Google", internships.get(0).getCompany());
        assertEquals("SWE", internships.get(0).getRole());
        assertEquals(6000, internships.get(0).getPay());
        assertEquals("Pending", internships.get(0).getStatus());
    }

    // Test load() with multiple valid entries
    @Test
    void load_multipleValidEntries_loadsAll() throws InternityException, IOException {
        String content = "Google | SWE | 15-03-2025 | 6000 | Pending\n"
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

    // Test load() with corrupted line - fewer than 5 fields
    @Test
    void load_corruptedLineFewerFields_skipsCorruptedLine() throws InternityException, IOException {
        String content = "Google | SWE | 15-03-2025 | 6000 | Pending\n"
                + "Meta | Data Scientist | 20-04-2025\n"  // Missing pay and status
                + "Amazon | DevOps | 01-05-2025 | 5500 | Rejected\n";
        Files.writeString(Path.of(testFilePath), content);

        ArrayList<Internship> internships = storage.load();

        assertEquals(2, internships.size());
        assertEquals("Google", internships.get(0).getCompany());
        assertEquals("Amazon", internships.get(1).getCompany());
        assertTrue(errContent.toString().contains("Warning: Skipped line with insufficient fields:"));
    }

    // Test load() with corrupted line - invalid pay format
    @Test
    void load_corruptedLineInvalidPay_skipsCorruptedLine() throws InternityException, IOException {
        String content = "Google | SWE | 15-03-2025 | 6000 | Pending\n"
                + "Meta | Data Scientist | 20-04-2025 | NotANumber | Accepted\n"
                + "Amazon | DevOps | 01-05-2025 | 5500 | Rejected\n";
        Files.writeString(Path.of(testFilePath), content);

        ArrayList<Internship> internships = storage.load();

        assertEquals(2, internships.size());
        assertTrue(errContent.toString().contains("Warning: Skipped line with invalid pay amount"));
    }

    // Test load() with corrupted line - invalid date format
    @Test
    void load_corruptedLineInvalidDate_skipsCorruptedLine() throws InternityException, IOException {
        String content = "Google | SWE | 15-03-2025 | 6000 | Pending\n"
                + "Meta | Data Scientist | 2025/04/20 | 7000 | Accepted\n"  // Wrong date format
                + "Amazon | DevOps | 01-05-2025 | 5500 | Rejected\n";
        Files.writeString(Path.of(testFilePath), content);

        ArrayList<Internship> internships = storage.load();

        assertEquals(2, internships.size());
        assertTrue(errContent.toString().contains("Warning: Skipped line with invalid date"));
    }

    // Test load() with corrupted line - invalid status
    @Test
    void load_corruptedLineInvalidStatus_skipsCorruptedLine() throws InternityException, IOException {
        String content = "Google | SWE | 15-03-2025 | 6000 | Pending\n"
                + "Meta | Data Scientist | 20-04-2025 | 7000 | InvalidStatus\n"  // Invalid status
                + "Amazon | DevOps | 01-05-2025 | 5500 | Rejected\n";
        Files.writeString(Path.of(testFilePath), content);

        ArrayList<Internship> internships = storage.load();

        assertEquals(2, internships.size());
        assertEquals("Google", internships.get(0).getCompany());
        assertEquals("Amazon", internships.get(1).getCompany());
        assertTrue(errContent.toString().contains("Warning: Skipped line with invalid status"));
    }

    // Test save() and load() round trip
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

    // Test load() handles extra whitespace
    @Test
    void load_extraWhitespace_trimsCorrectly() throws InternityException, IOException {
        Files.writeString(Path.of(testFilePath), "  Google  |  SWE  |  15-03-2025  |  6000  |  Pending  \n");

        ArrayList<Internship> internships = storage.load();

        assertEquals(1, internships.size());
        assertEquals("Google", internships.get(0).getCompany());
        assertEquals("SWE", internships.get(0).getRole());
    }

    // Test load() with multi-word fields
    @Test
    void load_multiWordFields_loadsCorrectly() throws InternityException, IOException {
        Files.writeString(Path.of(testFilePath),
                "Jane Street | Quantitative Researcher | 25-12-2025 | 10000 | Interviewing\n");

        ArrayList<Internship> internships = storage.load();

        assertEquals(1, internships.size());
        assertEquals("Jane Street", internships.get(0).getCompany());
        assertEquals("Quantitative Researcher", internships.get(0).getRole());
        assertEquals("Interviewing", internships.get(0).getStatus());
    }
}
