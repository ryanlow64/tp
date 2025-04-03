package seedu.address.model.property.predicates;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.property.Property;

/**
 * Tests that a {@code Client}'s {@code Address} contains the specified address.
 */
public class PropertyAddressContainsPredicate extends PropertyPredicate<String> {

    /**
     * Constructs a {@code ClientAddressContainsPredicate}.
     *
     * @param address The address to be used as a filter.
     */
    public PropertyAddressContainsPredicate(String address) {
        super(address);
    }

    @Override
    public boolean test(Property property) {
        return StringUtil.containsWordIgnoreCase(property.getAddress().toString(), value);
    }
}

