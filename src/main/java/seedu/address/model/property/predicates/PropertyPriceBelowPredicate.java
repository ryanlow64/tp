package seedu.address.model.property.predicates;

import seedu.address.model.commons.Price;
import seedu.address.model.property.Property;

/**
 * Tests that a {@code Deal}'s {@code Price} is below the specified {@code Price}.
 */
public class PropertyPriceBelowPredicate extends PropertyPredicate<Price> {

    public PropertyPriceBelowPredicate(Price price) {
        super(price);
    }

    @Override
    public boolean test(Property property) {
        return property.getPrice().isLessThan(value);
    }
}
