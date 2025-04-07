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

public class EventAboutPropertyPredicateTest {

    private final PropertyName targetProperty = new PropertyName("Maple Villa");
    private final EventAboutPropertyPredicate predicate = new EventAboutPropertyPredicate(targetProperty);

    private Event createEvent(String property) {
        return new Event(
                LocalDateTime.of(2025, 4, 30, 17, 0),
                EventType.MEETING,
                new ClientName("Alice"),
                new PropertyName(property),
                new Note("aquickbrownfoxjumpsoverthelazydog")
        );
    }

    @Test
    public void test_eventWithMatchingProperty_returnsTrue() {
        Event event1 = createEvent("Maple Grand Villa");
        assertTrue(predicate.test(event1));
        Event event2 = createEvent("Villa");
        assertTrue(predicate.test(event2));
        Event event3 = createEvent("mAplE gRAnD viLLA");
        assertTrue(predicate.test(event3));
    }

    @Test
    public void test_eventWithNonMatchingProperty_returnsFalse() {
        Event event = createEvent("White House");
        assertFalse(predicate.test(event));
    }
}
