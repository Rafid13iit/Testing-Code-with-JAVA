package math;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArithmeticOperationsTest {

    private ArithmeticOperations arithmeticOperations;

    @BeforeEach
    public void setUp() {
        arithmeticOperations = new ArithmeticOperations();
    }

    // Tests for divide method

    @Test
    public void testDivideNormalValues() {
        double result = arithmeticOperations.divide(10, 2);
        assertEquals(5.0, result, 0.001, "10 / 2 should be 5");
    }

    @Test
    public void testDivideWithZeroNumerator() {
        double result = arithmeticOperations.divide(0, 5);
        assertEquals(0.0, result, "0 / 5 should be 0");
    }

    @Test
    public void testDivideWithZeroDenominator() {
        Exception exception = assertThrows(ArithmeticException.class, () -> {
            arithmeticOperations.divide(10, 0);
        });
        assertEquals("Cannot divide with zero", exception.getMessage());
    }

    // Tests for multiply method


    @Test
    public void testMultiplyNormalValues() {
        int result = arithmeticOperations.multiply(3, 4);
        assertEquals(12, result, "3 * 4 should be 12");
    }

    @Test
    public void testMultiplyWithZero() {
        int result = arithmeticOperations.multiply(5, 0);
        assertEquals(0, result, "5 * 0 should be 0");
    }

    @Test
    public void testMultiplyWithNegativeInput() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            arithmeticOperations.multiply(-1, 5);
        });
        assertEquals("x & y should be >= 0", exception.getMessage());
    }

    @Test
    public void testMultiplyOverflow() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            arithmeticOperations.multiply(Integer.MAX_VALUE, 2);
        });
        assertEquals("The product does not fit in an Integer variable", exception.getMessage());
    }
}
