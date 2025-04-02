package seedu.address.model.deal.predicates;

import java.util.logging.Logger;

import seedu.address.model.deal.Deal;
import seedu.address.model.deal.DealStatus;

/**
 * Tests that a {@code Deal}'s status matches the provided status.
 */
public class DealStatusPredicate extends DealPredicate<DealStatus> {

    private static final Logger logger = Logger.getLogger(DealStatusPredicate.class.getName());

    /**
     * Constructs a {@code DealStatusPredicate}.
     *
     * @param status The status to filter deals by.
     */
    public DealStatusPredicate(DealStatus status) {
        super(status);
    }

    @Override
    public boolean test(Deal deal) {
        DealStatus dealStatus = deal.getStatus();
        logger.fine("Testing deal with status: " + dealStatus);

        boolean result = dealStatus.equals(value);

        logger.fine("Deal with status '" + dealStatus + "' " + (result ? "matches" : "does not match")
                + " target status: " + value);
        return result;
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }
}
