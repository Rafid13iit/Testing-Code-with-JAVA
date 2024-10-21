package math;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyMathTest {

    private MyMath myMath;

    @BeforeEach
    void setUp() {
        myMath = new MyMath();
    }

    @Test
    void testFactorial_ValidInput() {
        assertEquals(120, myMath.factorial(5));
        assertEquals(1, myMath.factorial(0));
        assertEquals(479001600, myMath.factorial(12));
    }

    @Test
    void testFactorial_InvalidInput_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> myMath.factorial(-1));
        assertThrows(IllegalArgumentException.class, () -> myMath.factorial(13));
    }

    @Test
    void testIsPrime_ValidPrime() {
        assertTrue(myMath.isPrime(5));
        assertTrue(myMath.isPrime(13));
        assertTrue(myMath.isPrime(2));
    }

    @Test
    void testIsPrime_ValidNonPrime() {
        assertFalse(myMath.isPrime(4));
        assertFalse(myMath.isPrime(1));
        assertFalse(myMath.isPrime(9));
    }

    @Test
    void testIsPrime_InvalidInput_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> myMath.isPrime(0));
        assertThrows(IllegalArgumentException.class, () -> myMath.isPrime(1));
    }
}
