package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.client.ClientName;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.property.PropertyName;

public class UniqueEventListTest {

    private final UniqueEventList uniqueEventList = new UniqueEventList();

    private final LocalDateTime dateTime1 = LocalDateTime.of(2025, 4, 30, 17, 0);
    private final LocalDateTime dateTime2 = LocalDateTime.of(2025, 9, 11, 9, 0);
    private final EventType eventType = EventType.MEETING;
    private final ClientName clientName = new ClientName("Alice");
    private final PropertyName propertyName = new PropertyName("Maple Villa");
    private final Note note = new Note("thequickbrownfoxjumpsoverthelazydog");

    @Test
    public void contains_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.contains(null));
    }

    @Test
    public void contains_eventInList_returnsTrue() {
        Event event = new Event(dateTime1, eventType, clientName, propertyName, note);
        uniqueEventList.add(event);
        assertTrue(uniqueEventList.contains(event));
    }

    @Test
    public void add_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.add(null));
    }

    @Test
    public void add_duplicateEvent_throwsDuplicateEventException() {
        Event event = new Event(dateTime1, eventType, clientName, propertyName, note);
        uniqueEventList.add(event);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.add(event));
    }

    @Test
    public void add_validEvent_success() {
        Event event = new Event(dateTime1, eventType, clientName, propertyName, note);
        uniqueEventList.add(event);

        UniqueEventList expectedList = new UniqueEventList();
        expectedList.add(event);

        assertEquals(expectedList, uniqueEventList);
    }

    @Test
    public void setEvent_targetEventNotInList_throwsEventNotFoundException() {
        Event event = new Event(dateTime1, eventType, clientName, propertyName, note);
        Event editedEvent = new Event(dateTime2, eventType, clientName, propertyName, note);
        assertThrows(EventNotFoundException.class, () -> uniqueEventList.setEvent(event, editedEvent));
    }

    @Test
    public void setEvent_editedEventHasDuplicateIdentity_throwsDuplicateEventException() {
        Event event1 = new Event(dateTime1, eventType, clientName, propertyName, note);
        Event event2 = new Event(dateTime2, eventType, clientName, propertyName, note);
        uniqueEventList.add(event1);
        uniqueEventList.add(event2);
        Event duplicateEvent = new Event(dateTime1, eventType, clientName, propertyName, note);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.setEvent(event2, duplicateEvent));
    }

    @Test
    public void setEvent_validReplacement_success() {
        Event event = new Event(dateTime1, eventType, clientName, propertyName, note);
        uniqueEventList.add(event);
        Event editedEvent = new Event(dateTime1, eventType, clientName, propertyName, new Note("Different note"));
        uniqueEventList.setEvent(event, editedEvent);
        UniqueEventList expectedList = new UniqueEventList();
        expectedList.add(editedEvent);
        assertEquals(expectedList, uniqueEventList);
    }

    @Test
    public void remove_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.remove(null));
    }

    @Test
    public void remove_eventNotInList_throwsEventNotFoundException() {
        Event event = new Event(dateTime1, eventType, clientName, propertyName, note);
        assertThrows(EventNotFoundException.class, () -> uniqueEventList.remove(event));
    }

    @Test
    public void remove_existingEvent_removesEvent() {
        Event event = new Event(dateTime1, eventType, clientName, propertyName, note);
        uniqueEventList.add(event);
        uniqueEventList.remove(event);
        assertEquals(Collections.emptyList(), uniqueEventList.asUnmodifiableObservableList());
    }

    @Test
    public void setEvents_listWithDuplicateEvents_throwsDuplicateEventException() {
        Event event1 = new Event(dateTime1, eventType, clientName, propertyName, note);
        Event duplicateEvent = new Event(dateTime1, eventType, clientName, propertyName, note);
        List<Event> duplicateList = Arrays.asList(event1, duplicateEvent);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.setEvents(duplicateList));
    }
}
