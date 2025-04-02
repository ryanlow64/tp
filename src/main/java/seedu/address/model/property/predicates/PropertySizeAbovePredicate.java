package seedu.address.model.property.predicates;

import seedu.address.model.property.Property;
import seedu.address.model.property.Size;

/**
 * Tests that a {@code Deal}'s {@code Size} is above the specified {@code Size}.
 */
public class PropertySizeAbovePredicate extends PropertyPredicate<Size> {

    public PropertySizeAbovePredicate(Size size) {
        super(size);
    }

    @Override
    public boolean test(Property property) {
        return property.getSize().map(size -> size.isBiggerThan(value))
                .orElse(false);
    }
}
