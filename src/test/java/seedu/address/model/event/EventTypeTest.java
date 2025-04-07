package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class EventTypeTest {

    @Test
    public void enum_values_success() {
        assertEquals(4, EventType.values().length);

        // Check enum values
        assertNotNull(EventType.CONFERENCE);
        assertNotNull(EventType.MEETING);
        assertNotNull(EventType.OTHERS);
        assertNotNull(EventType.WORKSHOP);
    }

    @Test
    public void toFormattedString() {
        assertEquals("Conference", EventType.CONFERENCE.toFormattedString());
        assertEquals("Meeting", EventType.MEETING.toFormattedString());
        assertEquals("Others", EventType.OTHERS.toFormattedString());
        assertEquals("Workshop", EventType.WORKSHOP.toFormattedString());
    }
}
