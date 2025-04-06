package seedu.address.model.deal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class DealStatusTest {

    @Test
    public void enum_values_success() {
        // Ensure all expected statuses exist
        assertEquals(3, DealStatus.values().length);

        // Check specific enum values
        assertNotNull(DealStatus.OPEN);
        assertNotNull(DealStatus.PENDING);
        assertNotNull(DealStatus.CLOSED);
    }

    @Test
    public void message_constraints_exists() {
        // Ensure message constraints is properly defined
        assertNotNull(DealStatus.MESSAGE_CONSTRAINTS);
        assertEquals("Deal status should only be one of \"OPEN\", \"PENDING\", or \"CLOSED\" (case insensitive).",
                DealStatus.MESSAGE_CONSTRAINTS);
    }
}
