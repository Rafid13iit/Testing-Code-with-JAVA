package io;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class FileIOTest {

    private FileIO fileIO;
    private Path validFilePath;
    private Path emptyFilePath;
    private Path invalidFilePath;

    @BeforeEach
    void setUp() throws IOException {
        fileIO = new FileIO();
        validFilePath = Paths.get("test_valid.txt");
        emptyFilePath = Paths.get("test_empty.txt");
        invalidFilePath = Paths.get("test_invalid.txt");

        // Create test files
        Files.writeString(validFilePath, "1\n2\n3\n");
        Files.createFile(emptyFilePath);
        Files.writeString(invalidFilePath, "1\nabc\n5\n");
    }

    @Test
    void testReadFile_ValidFile() {
        int[] expected = {1, 2, 3};
        assertArrayEquals(expected, fileIO.readFile(validFilePath.toString()));
    }

    @Test
    void testReadFile_EmptyFile_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> fileIO.readFile(emptyFilePath.toString()));
    }

    @Test
    void testReadFile_InvalidFile_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> fileIO.readFile("nonexistent.txt"));
    }

    @Test
    void testReadFile_FileWithInvalidEntries() {
        int[] expected = {1, 5};
        assertArrayEquals(expected, fileIO.readFile(invalidFilePath.toString()));
    }
}
