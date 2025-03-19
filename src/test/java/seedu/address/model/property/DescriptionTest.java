package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class DescriptionTest {

    @Test
    public void constructor_validDescription_success() {
        Description desc1 = new Description("Spacious home with 3 bedrooms");
        Description desc2 = new Description("Nice view, quiet neighborhood");

        assertEquals("Spacious home with 3 bedrooms", desc1.toString());
        assertEquals("Nice view, quiet neighborhood", desc2.toString());
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "A".repeat(51);
        assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription));
    }

    @Test
    public void constructor_emptyDescription_setsToNA() {
        Description desc = new Description("");
        assertEquals("N/A", desc.toString());
    }

    @Test
    public void isValidDescription_invalidDescription_returnsFalse() {
        String invalidDescription = "A".repeat(51);
        assertEquals(false, Description.isValidDescription(invalidDescription));
    }
}
