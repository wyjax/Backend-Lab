package com.example.junitfirst;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Red -> Green -> Refactor
 */

@SpringBootTest
public class ValidateISBNTest {

    /* Valid Number : 140449116 */
    /* Invalid Number : 140449117 */

    @Test
    public void nineDigitISBNsAreNotAllowed() {
        ValidateISBN validateISBN = new ValidateISBN();

        assertThrows(NumberFormatException.class, () -> {
            boolean result = validateISBN.checkISBN("123456789");
        });
    }

    @Test
    public void checkValidISBN() {
        ValidateISBN validateISBN = new ValidateISBN();
        boolean result = validateISBN.checkISBN("0140449116");

        Assertions.assertTrue(result);
    }

    @Test
    public void checkInValidISBN() {
        ValidateISBN validateISBN = new ValidateISBN();
        boolean result = validateISBN.checkISBN("0140177396");

        Assertions.assertTrue(result);
    }
}
