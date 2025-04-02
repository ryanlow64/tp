package seedu.address.model.event.predicates;

import java.time.LocalDateTime;

import seedu.address.model.event.Event;

/**
 * Tests that an {@code Event}'s date and time is after the given date and time.
 */
public class EventAfterDateTimePredicate extends EventPredicate<LocalDateTime> {

    public EventAfterDateTimePredicate(LocalDateTime dateTime) {
        super(dateTime);
    }

    @Override
    public boolean test(Event event) {
        return event.getDateTime().isAfter(value);
    }
}
