package math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyMathTest {

    private final MyMath myMath = new MyMath();

    // Test cases for factorial method
    @Test
    void testFactorial_withValidInput() {
        assertEquals(1, myMath.factorial(0));
        assertEquals(1, myMath.factorial(1));
        assertEquals(120, myMath.factorial(5));
        assertEquals(479001600, myMath.factorial(12));
    }

    @Test
    void testFactorial_withNegativeInput() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            myMath.factorial(-1);
        });
        assertEquals("number should be 0 or above and 12 or below", exception.getMessage());
    }

    @Test
    void testFactorial_withInputGreaterThan12() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            myMath.factorial(13);
        });
        assertEquals("number should be 0 or above and 12 or below", exception.getMessage());
    }

    // Test cases for isPrime method
    @Test
    void testIsPrime_withPrimeNumbers() {
        assertTrue(myMath.isPrime(2));
        assertTrue(myMath.isPrime(3));
        assertTrue(myMath.isPrime(5));
        assertTrue(myMath.isPrime(11));
    }

    @Test
    void testIsPrime_withNonPrimeNumbers() {
        assertFalse(myMath.isPrime(4));
        assertFalse(myMath.isPrime(6));
        assertFalse(myMath.isPrime(9));
        assertFalse(myMath.isPrime(10));
    }

    @Test
    void testIsPrime_withNegativeInput() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            myMath.isPrime(-1);
        });
        assertEquals("No prime numbers below 2", exception.getMessage());
    }

    @Test
    void testIsPrime_withInputZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            myMath.isPrime(0);
        });
        assertEquals("No prime numbers below 2", exception.getMessage());
    }

    @Test
    void testIsPrime_withInputOne() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            myMath.isPrime(1);
        });
        assertEquals("No prime numbers below 2", exception.getMessage());
    }
}
