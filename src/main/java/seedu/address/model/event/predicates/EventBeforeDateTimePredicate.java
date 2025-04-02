package seedu.address.model.event.predicates;

import java.time.LocalDateTime;

import seedu.address.model.event.Event;

/**
 * Tests that an {@code Event}'s date and time is before the given date and time.
 */
public class EventBeforeDateTimePredicate extends EventPredicate<LocalDateTime> {

    public EventBeforeDateTimePredicate(LocalDateTime dateTime) {
        super(dateTime);
    }

    @Override
    public boolean test(Event event) {
        return event.getDateTime().isBefore(value);
    }
}
