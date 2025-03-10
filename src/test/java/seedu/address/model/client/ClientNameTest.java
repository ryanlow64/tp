package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ClientNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ClientName(null));
    }

    @Test
    public void constructor_invalidClientName_throwsIllegalArgumentException() {
        String invalidClientName = "";
        assertThrows(IllegalArgumentException.class, () -> new ClientName(invalidClientName));
    }

    @Test
    public void isValidClientName() {
        // null clientName
        assertThrows(NullPointerException.class, () -> ClientName.isValidClientName(null));

        // invalid clientName
        assertFalse(ClientName.isValidClientName("")); // empty string
        assertFalse(ClientName.isValidClientName(" ")); // spaces only
        assertFalse(ClientName.isValidClientName("^")); // only non-alphanumeric characters
        assertFalse(ClientName.isValidClientName("peter*")); // contains non-alphanumeric characters

        // valid clientName
        assertTrue(ClientName.isValidClientName("peter jack")); // alphabets only
        assertTrue(ClientName.isValidClientName("12345")); // numbers only
        assertTrue(ClientName.isValidClientName("peter the 2nd")); // alphanumeric characters
        assertTrue(ClientName.isValidClientName("Capital Tan")); // with capital letters
        assertTrue(ClientName.isValidClientName("David Roger Jackson Ray Jr 2nd")); // long clientNames
    }

    @Test
    public void equals() {
        ClientName clientName = new ClientName("Valid ClientName");

        // same values -> returns true
        assertTrue(clientName.equals(new ClientName("Valid ClientName")));

        // same object -> returns true
        assertTrue(clientName.equals(clientName));

        // null -> returns false
        assertFalse(clientName.equals(null));

        // different types -> returns false
        assertFalse(clientName.equals(5.0f));

        // different values -> returns false
        assertFalse(clientName.equals(new ClientName("Other Valid ClientName")));
    }
}
