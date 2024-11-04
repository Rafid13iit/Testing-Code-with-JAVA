package io;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileIOTest {

    @Test
    public void testReadFileFileDoesNotExist() {
        FileIO fileIO = new FileIO();
        String filepath = "src/test/resources/nonexistent_file.txt";
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> fileIO.readFile(filepath)
        );
        assertEquals("Input file does not exist", exception.getMessage());
    }

    @Test
    public void testReadFileEmptyFile() {
        FileIO fileIO = new FileIO();
        String filepath = "src/test/resources/empty_file.txt";
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> fileIO.readFile(filepath)
        );
        assertEquals("Given file is empty", exception.getMessage());
    }

    @Test
    public void testReadFileValidInput() {
        FileIO fileIO = new FileIO();
        String filepath = "src/test/resources/grades_valid.txt";
        int[] actualResult = fileIO.readFile(filepath);
        int[] expectedResult = {3, 9, 0, 2, 10, 9, 3, 8, 0, 3};
        assertArrayEquals(expectedResult, actualResult);
    }

    @Test
    public void testReadFileInvalidInput() {
        FileIO fileIO = new FileIO();
        String filepath = "src/test/resources/grades_invalid.txt";
        int[] actualResult = fileIO.readFile(filepath);
        int[] expectedResult = {3, 9, 2, 10, 8, 0, 3};
        assertArrayEquals(expectedResult, actualResult);
    }
}
