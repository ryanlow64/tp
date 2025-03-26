package seedu.address.model.deal.predicates;

import static java.util.Objects.requireNonNull;

import seedu.address.model.deal.Deal;
import seedu.address.model.deal.DealStatus;

/**
 * Tests that a {@code Deal}'s status matches the provided status.
 */
public class DealStatusPredicate extends DealPredicate<DealStatus> {

    /**
     * Constructs a {@code DealStatusPredicate}.
     *
     * @param status The status to filter deals by.
     */
    public DealStatusPredicate(DealStatus status) {
        super(status);
        requireNonNull(status);
    }

    @Override
    public boolean test(Deal deal) {
        return deal.getStatus().equals(value);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }
} 