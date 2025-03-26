package seedu.address.model.event.predicates;

import seedu.address.model.event.Event;
import seedu.address.model.event.EventType;

/**
 * Tests that an {@code Event}'s {@code EventType} matches the {@code EventType} given.
 */
public class EventOfTypePredicate extends EventPredicate<EventType> {

    public EventOfTypePredicate(EventType eventType) {
        super(eventType);
    }

    @Override
    public boolean test(Event event) {
        return event.getEventType().equals(value);
    }
}
