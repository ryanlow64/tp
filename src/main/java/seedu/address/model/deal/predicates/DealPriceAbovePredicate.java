package seedu.address.model.deal.predicates;

import seedu.address.model.commons.Price;
import seedu.address.model.deal.Deal;

/**
 * Tests that a {@code Deal}'s {@code Price} is above the specified {@code Price}.
 */
public class DealPriceAbovePredicate extends DealPredicate<Price> {

    public DealPriceAbovePredicate(Price price) {
        super(price);
    }

    @Override
    public boolean test(Deal deal) {
        return deal.getPrice().isMoreThan(value);
    }
}
