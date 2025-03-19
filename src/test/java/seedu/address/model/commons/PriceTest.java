package seedu.address.model.commons;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PriceTest {

    @Test
    public void constructor_validPrice_success() {
        // Valid prices
        Price validPrice1 = new Price(2400L);
        Price validPrice2 = new Price(12345L);

        assertTrue(validPrice1.value != null);
        assertTrue(validPrice2.value != null);
    }

    @Test
    public void isValidPrice() {
        // invalid prices
        assertFalse(Price.isValidPrice(-100L)); // negative price
        assertFalse(Price.isValidPrice(12L)); // too short price
        assertFalse(Price.isValidPrice(10000000L)); // too long price
    }

    @Test
    public void equals() {
        Price price1 = new Price(2400L);
        Price price2 = new Price(2400L);
        Price price3 = new Price(1200L);

        // same values -> returns true
        assertTrue(price1.equals(price2));

        // same object -> returns true
        assertTrue(price1.equals(price1));

        // null -> returns false
        assertFalse(price1.equals(null));

        // different types -> returns false
        assertFalse(price1.equals(5.0f));

        // different values -> returns false
        assertFalse(price1.equals(price3));
    }

    @Test
    public void toStringMethod() {
        Price price = new Price(2400L);
        assertTrue(price.toString().equals("2400"));
    }
}
