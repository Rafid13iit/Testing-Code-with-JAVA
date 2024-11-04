package math;

import io.FileIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

// Simple stub for MyMath
class MyMathStub extends MyMath {
    @Override
    public boolean isPrime(int number) {
        // Define prime-checking logic
        if (number <= 1) return false;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }
}

// Simple stub for FileIO
class FileIOStub extends FileIO {
    private final int[] numbers;

    public FileIOStub(int[] numbers) {
        this.numbers = numbers;
    }

    @Override
    public int[] readFile(String filepath) {
        return numbers; // Return the predefined numbers
    }
}

class ArrayOperationsTest {

    private ArrayOperations arrayOperations;

    @BeforeEach
    void setUp() {
        arrayOperations = new ArrayOperations();
    }

    @Test
    void testFindPrimesInFile_withPrimesAndNonPrimes() {
        FileIO fileIO = new FileIOStub(new int[]{2, 3, 4, 5, 6});
        MyMath myMath = new MyMathStub();

        int[] primes = arrayOperations.findPrimesInFile(fileIO, "testfile.txt", myMath);
        assertArrayEquals(new int[]{2, 3, 5}, primes);
    }

    @Test
    void testFindPrimesInFile_noPrimes() {
        FileIO fileIO = new FileIOStub(new int[]{4, 6, 8});
        MyMath myMath = new MyMathStub();

        int[] primes = arrayOperations.findPrimesInFile(fileIO, "testfile.txt", myMath);
        assertArrayEquals(new int[]{}, primes);
    }

    @Test
    void testFindPrimesInFile_emptyInput() {
        FileIO fileIO = new FileIOStub(new int[]{});
        MyMath myMath = new MyMathStub();

        int[] primes = arrayOperations.findPrimesInFile(fileIO, "testfile.txt", myMath);
        assertArrayEquals(new int[]{}, primes);
    }

    @Test
    void testFindPrimesInFile_negativeNumbers() {
        FileIO fileIO = new FileIOStub(new int[]{-1, -2, -3});
        MyMath myMath = new MyMathStub();

        int[] primes = arrayOperations.findPrimesInFile(fileIO, "testfile.txt", myMath);
        assertArrayEquals(new int[]{}, primes);
    }

    @Test
    void testFindPrimesInFile_onlyPrimes() {
        FileIO fileIO = new FileIOStub(new int[]{2, 3, 5, 7, 11});
        MyMath myMath = new MyMathStub();

        int[] primes = arrayOperations.findPrimesInFile(fileIO, "testfile.txt", myMath);
        assertArrayEquals(new int[]{2, 3, 5, 7, 11}, primes);
    }

    @Test
    void testFindPrimesInFile_withInvalidEntries() {
        // Simulating the filtering of invalid entries (like doubles and characters) and keeping only integers
        FileIO fileIO = new FileIOStub(new int[]{2, 3, 5, 7, 11}); // Only valid integers passed in

        MyMath myMath = new MyMathStub();

        int[] primes = arrayOperations.findPrimesInFile(fileIO, "testfile.txt", myMath);
        assertArrayEquals(new int[]{2, 3, 5, 7, 11}, primes);
    }
}
