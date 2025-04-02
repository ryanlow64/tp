package seedu.address.model.property.predicates;

import seedu.address.model.commons.Price;
import seedu.address.model.property.Property;

/**
 * Tests that a {@code Deal}'s {@code Price} is above the specified {@code Price}.
 */
public class PropertyPriceAbovePredicate extends PropertyPredicate<Price> {

    public PropertyPriceAbovePredicate(Price price) {
        super(price);
    }

    @Override
    public boolean test(Property property) {
        return property.getPrice().isMoreThan(value);
    }
}
