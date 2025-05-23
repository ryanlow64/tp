package seedu.address.model.deal.predicates;

import java.util.function.Predicate;

import seedu.address.model.deal.Deal;

/**
 * Generic class for predicates that are used to filter deals.
 */
public abstract class DealPredicate<T> implements Predicate<Deal> {

    protected final T value;

    /**
     * Constructs a DealPredicate with the specified value.
     *
     * @param value The value to filter deals by
     */
    public DealPredicate(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DealPredicate<?> otherPredicate)) {
            return false;
        }

        return value.equals(otherPredicate.value);
    }
}
