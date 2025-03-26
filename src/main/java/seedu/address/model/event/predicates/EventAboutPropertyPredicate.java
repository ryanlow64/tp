package seedu.address.model.event.predicates;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.model.event.Event;
import seedu.address.model.property.PropertyName;

/**
 * Tests that a {@code Event}'s {@code PropertyName} matches the {@code PropertyName} given.
 */
public class EventAboutPropertyPredicate implements Predicate<Event> {

    private final PropertyName propertyName;

    /**
     * Constructs a {@code EventAboutPropertyPredicate}.
     *
     * @param propertyName The name of the property to be used as a filter.
     */
    public EventAboutPropertyPredicate(PropertyName propertyName) {
        requireNonNull(propertyName);
        this.propertyName = propertyName;
    }

    @Override
    public boolean test(Event event) {
        return event.getPropertyName().equals(propertyName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EventAboutPropertyPredicate otherPredicate)) {
            return false;
        }

        return propertyName.equals(otherPredicate.propertyName);
    }
}
