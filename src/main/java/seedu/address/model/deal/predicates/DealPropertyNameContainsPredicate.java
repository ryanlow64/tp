package seedu.address.model.deal.predicates;

import static java.util.Objects.requireNonNull;

import java.util.stream.Stream;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.deal.Deal;
import seedu.address.model.property.PropertyName;

/**
 * Tests that a {@code Deal}'s {@code PropertyName} contains any of the keywords.
 */
public class DealPropertyNameContainsPredicate extends DealPredicate<PropertyName> {

    /**
     * Constructs a {@code DealPropertyNameContainsPredicate}.
     *
     * @param propertyName The name of the property to be used as a filter.
     */
    public DealPropertyNameContainsPredicate(PropertyName propertyName) {
        super(propertyName);
        requireNonNull(propertyName);
    }

    @Override
    public boolean test(Deal deal) {
        String[] keywords = value.fullName.split("\\s+");
        return Stream.of(keywords)
            .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(deal.getPropertyName().fullName, keyword));
    }
}
