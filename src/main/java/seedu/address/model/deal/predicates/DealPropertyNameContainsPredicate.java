package seedu.address.model.deal.predicates;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.model.deal.Deal;


/**
 * Tests that a {@code Deal}'s {@code PropertyName} contains any of the keywords.
 */
public class DealPropertyNameContainsPredicate extends DealPredicate<List<String>> {

    private static final Logger logger = Logger.getLogger(DealPropertyNameContainsPredicate.class.getName());

    /**
     * Constructs a {@code DealPropertyNameContainsPredicate}.
     *
     * @param keywords The list of keywords to match against deal's property name.
     */
    public DealPropertyNameContainsPredicate(List<String> keywords) {
        super(keywords);
        requireNonNull(keywords);
    }

    @Override
    public boolean test(Deal deal) {
        String propertyName = deal.getPropertyName().fullName.toLowerCase();
        logger.fine("Testing deal with property name: " + propertyName);

        boolean result = value.stream()
                .anyMatch(keyword ->
                    propertyName.contains(keyword.toLowerCase()));

        logger.fine("Deal with property name '" + propertyName + "' " + (result ? "matches" : "does not match")
                + " property name keywords: " + value);
        return result;
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }
}
