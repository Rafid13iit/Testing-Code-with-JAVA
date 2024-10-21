package math;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.FileIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArrayOperationsTest {

    private ArrayOperations arrayOperations;
    private FileIO fileioMock;
    private MyMath myMathMock;

    @BeforeEach
    void setUp() {
        arrayOperations = new ArrayOperations();
        fileioMock = mock(FileIO.class);
        myMathMock = mock(MyMath.class);
    }

    @Test
    void testFindPrimesInFile_Success() {
        when(fileioMock.readFile("numbers.txt")).thenReturn(new int[] {2, 3, 4, 5, 6, 7, 8});
        when(myMathMock.isPrime(2)).thenReturn(true);
        when(myMathMock.isPrime(3)).thenReturn(true);
        when(myMathMock.isPrime(4)).thenReturn(false);
        when(myMathMock.isPrime(5)).thenReturn(true);
        when(myMathMock.isPrime(6)).thenReturn(false);
        when(myMathMock.isPrime(7)).thenReturn(true);
        when(myMathMock.isPrime(8)).thenReturn(false);

        int[] primes = arrayOperations.findPrimesInFile(fileioMock, "numbers.txt", myMathMock);

        assertArrayEquals(new int[] {2, 3, 5, 7}, primes);
    }

    @Test
    void testFindPrimesInFile_EmptyFile() {
        when(fileioMock.readFile("empty.txt")).thenReturn(new int[] {});

        int[] primes = arrayOperations.findPrimesInFile(fileioMock, "empty.txt", myMathMock);

        assertArrayEquals(new int[] {}, primes);
    }
}
