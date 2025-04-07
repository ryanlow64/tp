package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PropertyNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PropertyName(null));
    }

    @Test
    public void constructor_invalidPropertyName_throwsIllegalArgumentException() {
        String invalidPropertyName = "";
        assertThrows(IllegalArgumentException.class, () -> new PropertyName(invalidPropertyName));
    }

    @Test
    public void isValidPropertyName() {
        // null propertyName
        assertThrows(NullPointerException.class, () -> PropertyName.isValidPropertyName(null));

        // invalid propertyName
        assertFalse(PropertyName.isValidPropertyName("")); // empty string
        assertFalse(PropertyName.isValidPropertyName(" ")); // spaces only
        assertFalse(PropertyName.isValidPropertyName("^")); // only non-alphanumeric characters
        assertFalse(PropertyName.isValidPropertyName("peter*")); // contains non-alphanumeric characters
        assertFalse(PropertyName.isValidPropertyName("peter jack$")); // contains non-alphanumeric characters
        assertFalse(PropertyName.isValidPropertyName("Capital Tower 1238732883981293910903901293310912912")); //too long

        // valid propertyName
        assertTrue(PropertyName.isValidPropertyName("peter jack")); // alphabets only
        assertTrue(PropertyName.isValidPropertyName("12345")); // numbers only
        assertTrue(PropertyName.isValidPropertyName("peter the 2nd")); // alphanumeric characters
        assertTrue(PropertyName.isValidPropertyName("Capital Tan")); // with capital letters
        assertTrue(PropertyName.isValidPropertyName("Capital @ Tower")); // with capital letters and special character '@'
        assertTrue(PropertyName.isValidPropertyName("Capital Tower &")); // with special character '&'
        assertTrue(PropertyName.isValidPropertyName("Capital @ Tower &&123")); // with both special characters and numbers
        assertTrue(PropertyName.isValidPropertyName("David Roger Jackson Ray Jr 2nd")); // long clientNames
    }

    @Test
    public void equals() {
        PropertyName propertyName = new PropertyName("Valid PropertyName");

        // same values -> returns true
        assertTrue(propertyName.equals(new PropertyName("Valid PropertyName")));

        // same object -> returns true
        assertTrue(propertyName.equals(propertyName));

        // null -> returns false
        assertFalse(propertyName.equals(null));

        // different types -> returns false
        assertFalse(propertyName.equals(5.0f));

        // different values -> returns false
        assertFalse(propertyName.equals(new PropertyName("Other Valid PropertyName")));
    }
}
