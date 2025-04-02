package seedu.address.model.property.predicates;

import seedu.address.model.property.Property;
import seedu.address.model.property.Size;

/**
 * Tests that a {@code Deal}'s {@code Size} is below the specified {@code Size}.
 */
public class PropertySizeBelowPredicate extends PropertyPredicate<Size> {

    public PropertySizeBelowPredicate(Size size) {
        super(size);
    }

    @Override
    public boolean test(Property property) {
        return property.getSize().map(size -> size.isSmallerThan(value))
                .orElse(false);
    }
}
