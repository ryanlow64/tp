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

public class EventWithClientPredicateTest {

    private final ClientName targetClient = new ClientName("Alice");
    private final EventWithClientPredicate predicate = new EventWithClientPredicate(targetClient);

    private Event createEvent(String client) {
        return new Event(
                LocalDateTime.of(2025, 5, 20, 14, 0),
                EventType.MEETING,
                new ClientName(client),
                new PropertyName("Maple Villa"),
                new Note("aquickbrownfoxjumpsoverthelazydog")
        );
    }

    @Test
    public void test_clientNameContainsKeyword_returnsTrue() {
        Event event = createEvent("Alice Schwarzenegger");
        assertTrue(predicate.test(event));
    }

    @Test
    public void test_clientNameDoesNotContainKeyword_returnsFalse() {
        Event event = createEvent("Bob Alicia");
        assertFalse(predicate.test(event));
    }
}
