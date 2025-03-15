package seedu.address.model.commons;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PriceTest {

    @Test
    public void isValidPrice() {
        // null price
        assertFalse(Price.isValidPrice(null));

        // invalid prices
        assertFalse(Price.isValidPrice("")); // empty string
        assertFalse(Price.isValidPrice(" ")); // spaces only
        assertFalse(Price.isValidPrice("price")); // non-numeric
        assertFalse(Price.isValidPrice("9a")); // contains non-numeric
        assertFalse(Price.isValidPrice("9 1")); // contains space
        assertFalse(Price.isValidPrice("0")); // zero
        assertFalse(Price.isValidPrice("-1")); // negative
        assertFalse(Price.isValidPrice("9000000000000000000")); // 9 quintillion (too large)
        assertFalse(Price.isValidPrice("10000000000000000000")); // 10 quintillion (too large)

        // valid prices
        assertTrue(Price.isValidPrice("1")); // minimum valid price
        assertTrue(Price.isValidPrice("123")); // small price
        assertTrue(Price.isValidPrice("1000000")); // million
        assertTrue(Price.isValidPrice("1000000000")); // billion
        assertTrue(Price.isValidPrice("1000000000000")); // trillion
        assertTrue(Price.isValidPrice("8999999999999999999")); // just under 9 quintillion
    }
} 