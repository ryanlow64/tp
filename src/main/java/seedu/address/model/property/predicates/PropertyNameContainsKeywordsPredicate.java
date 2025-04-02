package seedu.address.model.property.predicates;

import java.util.List;

import seedu.address.model.commons.NameContainsKeywordsPredicate;
import seedu.address.model.property.Property;

/**
 * Tests that a {@code Property}'s {@code Name} matches any of the keywords given.
 */
public class PropertyNameContainsKeywordsPredicate extends NameContainsKeywordsPredicate<Property> {

    /**
     * Constructs a {@code PropertyNameContainsKeywordsPredicate}.
     *
     * @param keywords A list of keywords to search for in the property name.
     */
    public PropertyNameContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }
}
