package math;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArithmeticOperationsTest {

    private ArithmeticOperations arithmeticOperations;

    @BeforeEach
    void setUp() {
        arithmeticOperations = new ArithmeticOperations();
    }

    @Test
    void testDivide_Success() {
        assertEquals(5.0, arithmeticOperations.divide(10, 2));
    }

    @Test
    void testDivide_ThrowsArithmeticException() {
        assertThrows(ArithmeticException.class, () -> arithmeticOperations.divide(10, 0));
    }

    @Test
    void testMultiply_Success() {
        assertEquals(20, arithmeticOperations.multiply(4, 5));
    }

    @Test
    void testMultiply_ZeroMultiplication() {
        assertEquals(0, arithmeticOperations.multiply(5, 0));
    }

    @Test
    void testMultiply_ThrowsIllegalArgumentException_NegativeInput() {
        assertThrows(IllegalArgumentException.class, () -> arithmeticOperations.multiply(-1, 5));
    }

    @Test
    void testMultiply_ThrowsIllegalArgumentException_Overflow() {
        assertThrows(IllegalArgumentException.class, () -> arithmeticOperations.multiply(Integer.MAX_VALUE, 2));
    }
}
