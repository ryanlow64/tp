package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.client.ClientName;
import seedu.address.model.property.PropertyName;

public class EventTest {
    private final LocalDateTime dateTime1 = LocalDateTime.of(2025, 4, 30, 17, 0);
    private final LocalDateTime dateTime2 = LocalDateTime.of(2025, 9, 11, 9, 0);

    private final ClientName clientName1 = new ClientName("Alice");
    private final ClientName clientName2 = new ClientName("Bob");

    private final PropertyName propertyName1 = new PropertyName("Villa");
    private final PropertyName propertyName2 = new PropertyName("Ocean View");

    private final Note note1 = new Note("This is a note");
    private final Note note2 = new Note("thequickbrownfoxjumpsoverthelazydog");

    @Test
    public void constructor_nullFields_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new Event(null, EventType.MEETING, clientName1, propertyName1, note1));
        assertThrows(NullPointerException.class, () ->
                new Event(dateTime1, null, clientName1, propertyName1, note1));
        assertThrows(NullPointerException.class, () ->
                new Event(dateTime1, EventType.MEETING, null, propertyName1, note1));
        assertThrows(NullPointerException.class, () ->
                new Event(dateTime1, EventType.MEETING, clientName1, null, note1));
        assertThrows(NullPointerException.class, () ->
                new Event(dateTime1, EventType.MEETING, clientName1, propertyName1, null));
    }

    @Test
    public void equals() {
        Event event1 = new Event(dateTime1, EventType.MEETING, clientName1, propertyName1, note1);
        Event event2 = new Event(dateTime1, EventType.MEETING, clientName1, propertyName1, note1);
        Event eventDifferentNote = new Event(dateTime1, EventType.MEETING, clientName1, propertyName1, note2);

        assertTrue(event1.equals(event2));
        assertFalse(event1.equals(eventDifferentNote));
        assertTrue(event1.isSameEvent(eventDifferentNote));

        Event eventDifferentDate = new Event(dateTime2, EventType.MEETING, clientName1, propertyName1, note1);
        assertFalse(event1.equals(eventDifferentDate));

        Event eventDifferentType = new Event(dateTime1, EventType.OTHERS, clientName1, propertyName1, note1);
        assertFalse(event1.equals(eventDifferentType));

        Event eventDifferentClient = new Event(dateTime1, EventType.MEETING, clientName2, propertyName1, note1);
        assertFalse(event1.equals(eventDifferentClient));

        Event eventDifferentProperty = new Event(dateTime1, EventType.MEETING, clientName1, propertyName2, note1);
        assertFalse(event1.equals(eventDifferentProperty));
    }

    @Test
    public void compareTo_dateTimeOrdering() {
        Event earlierEvent = new Event(dateTime1, EventType.MEETING, clientName1, propertyName1, note1);
        Event laterEvent = new Event(dateTime2, EventType.MEETING, clientName1, propertyName1, note1);

        assertTrue(earlierEvent.compareTo(laterEvent) < 0);
        assertTrue(laterEvent.compareTo(earlierEvent) > 0);
    }

    @Test
    public void toStringMethod() {
        Event event = new Event(dateTime1, EventType.MEETING, clientName1, propertyName1, note1);
        String expected = Event.class.getCanonicalName() + "{dateTime=" + dateTime1
                + ", eventType=" + EventType.MEETING + ", clientName=" + clientName1.fullName
                + ", propertyName=" + propertyName1.fullName + ", note=" + note1 + "}";
        assertEquals(expected, event.toString());
    }
}
