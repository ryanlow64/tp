package seedu.address.model.property.predicates;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.property.Property;

/**
 * Tests that a {@code Client}'s {@code Name} matches any of the keywords given.
 */
public class PropertyOwnerContainsKeywordsPredicate extends PropertyPredicate<List<String>> {

    /**
     * Constructs a {@code ClientNameContainsKeywordsPredicate}.
     *
     * @param keywords The keywords to be used as a filter.
     */
    public PropertyOwnerContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    /**
     * Tests if the property owner name contains any of the keywords.
     *
     * @param property The property to be tested.
     * @return True if the property owner name contains any of the keywords, false otherwise.
     */
    @Override
    public boolean test(Property property) {
        return value.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(property.getOwner().fullName, keyword));
    }
}
