package com.example.junitfirst;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ValidateISBNTest {

    /* Valid Number : 140449116 */
    /* Invalid Number : 140449117 */

    @Test
    public void checkValidISBN() {
        ValidateISBN validateISBN = new ValidateISBN();
        boolean result = validateISBN.checkISBN(140449116);

        assertThat(result).isEqualTo(true);
    }

    @Test
    public void checkInValidISBN() {
        ValidateISBN validateISBN = new ValidateISBN();
        boolean result = validateISBN.checkISBN(140449117);

        assertThat(result).isEqualTo(false);
    }
}
