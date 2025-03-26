package seedu.address.model.event.predicates;

import java.util.function.Predicate;

import seedu.address.model.event.Event;

/**
 * Generic class for predicates that are used to filter events.
 */
public abstract class EventPredicate implements Predicate<Event> {
}
