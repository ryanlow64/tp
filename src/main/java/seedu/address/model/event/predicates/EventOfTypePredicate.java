package seedu.address.model.event.predicates;

import seedu.address.model.event.Event;
import seedu.address.model.event.EventType;

/**
 * Tests that an {@code Event}'s {@code EventType} matches the {@code EventType} given.
 */
public class EventOfTypePredicate extends EventPredicate {

    private final EventType eventType;

    public EventOfTypePredicate(EventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public boolean test(Event event) {
        return event.getEventType().equals(eventType);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EventOfTypePredicate otherPredicate)) {
            return false;
        }

        return eventType.equals(otherPredicate.eventType);
    }
}
