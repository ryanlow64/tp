package seedu.address.model.deal.predicates;

import seedu.address.model.commons.Price;
import seedu.address.model.deal.Deal;

/**
 * Tests that a {@code Deal}'s {@code Price} is below the specified {@code Price}.
 */
public class DealPriceBelowPredicate extends DealPredicate<Price> {

    public DealPriceBelowPredicate(Price price) {
        super(price);
    }

    @Override
    public boolean test(Deal deal) {
        return deal.getPrice().isLessThan(value);
    }
}
