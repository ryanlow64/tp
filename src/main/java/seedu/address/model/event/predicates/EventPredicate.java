package seedu.address.model.event.predicates;

import java.util.function.Predicate;

import seedu.address.model.event.Event;

/**
 * Generic class for predicates that are used to filter events.
 */
public abstract class EventPredicate<T> implements Predicate<Event> {

    protected final T value;

    public EventPredicate(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EventOfTypePredicate otherPredicate)) {
            return false;
        }

        return value.equals(otherPredicate.value);
    }
}
