package seedu.address.model.event.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.client.ClientName;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventType;
import seedu.address.model.event.Note;
import seedu.address.model.property.PropertyName;

public class EventBeforeDateTimePredicateTest {

    private final LocalDateTime cutoff = LocalDateTime.of(2025, 4, 30, 19, 0);
    private final EventAfterDateTimePredicate predicate = new EventAfterDateTimePredicate(cutoff);

    private Event createEvent(LocalDateTime dateTime) {
        return new Event(
                dateTime,
                EventType.MEETING,
                new ClientName("Alice"),
                new PropertyName("Maple Villa"),
                new Note("aquickbrownfoxjumpsoverthelazydog")
        );
    }

    @Test
    public void test_eventAfterCutoff_returnsFalse() {
        Event event = createEvent(LocalDateTime.of(2025, 9, 11, 19, 0));
        assertTrue(predicate.test(event));
    }

    @Test
    public void test_eventAtCutoff_returnsFalse() {
        Event event = createEvent(cutoff);
        assertFalse(predicate.test(event));
    }

    @Test
    public void test_eventBeforeCutoff_returnsTrue() {
        Event event = createEvent(LocalDateTime.of(2025, 4, 30, 17, 0));
        assertFalse(predicate.test(event));
    }
}
