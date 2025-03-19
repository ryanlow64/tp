package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SizeTest {

    @Test
    public void constructor_validSize_success() {
        Size size1 = new Size("500");
        Size size2 = new Size("9999");

        assertEquals("500", size1.toString());
        assertEquals("9999", size2.toString());
    }

    @Test
    public void constructor_emptySize_setsToNA() {
        Size size = new Size("");
        assertEquals("N/A", size.toString());
    }

    @Test
    public void isValidSize_invalidSize_returnsFalse() {
        assertEquals(false, Size.isValidSize("99"));
        assertEquals(false, Size.isValidSize("abcd"));
    }
}
