package seedu.address.model.property.predicates;

import java.util.function.Predicate;

import seedu.address.model.property.Property;

/**
 * Generic class for predicates that are used to filter clients.
 */
public abstract class PropertyPredicate<T> implements Predicate<Property> {
    protected final T value;

    public PropertyPredicate(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PropertyPredicate otherPredicate)) {
            return false;
        }

        return value.equals(otherPredicate.value);
    }
}
