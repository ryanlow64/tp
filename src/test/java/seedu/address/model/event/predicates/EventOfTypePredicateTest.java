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

public class EventOfTypePredicateTest {

    private final EventType targetType = EventType.CONFERENCE;
    private final EventOfTypePredicate predicate = new EventOfTypePredicate(targetType);

    private Event createEvent(EventType eventType) {
        return new Event(
                LocalDateTime.of(2025, 4, 30, 17, 0),
                eventType,
                new ClientName("Alice"),
                new PropertyName("Maple Villa"),
                new Note("aquickbrownfoxjumpsoverthelazydog")
        );
    }

    @Test
    public void test_eventOfMatchingType_returnsTrue() {
        Event event = createEvent(EventType.CONFERENCE);
        assertTrue(predicate.test(event));
    }

    @Test
    public void test_eventOfNonMatchingType_returnsFalse() {
        Event event = createEvent(EventType.OTHERS);
        assertFalse(predicate.test(event));
    }
}
