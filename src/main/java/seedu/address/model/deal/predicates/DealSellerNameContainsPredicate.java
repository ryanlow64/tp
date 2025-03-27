package seedu.address.model.deal.predicates;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.model.deal.Deal;

/**
 * Tests that a {@code Deal}'s seller name contains any of the keywords.
 */
public class DealSellerNameContainsPredicate extends DealPredicate<List<String>> {

    private static final Logger logger = Logger.getLogger(DealSellerNameContainsPredicate.class.getName());

    /**
     * Constructs a {@code DealSellerNameContainsPredicate}.
     *
     * @param keywords The list of keywords to match against deal's seller name.
     */
    public DealSellerNameContainsPredicate(List<String> keywords) {
        super(keywords);
        requireNonNull(keywords);
    }

    @Override
    public boolean test(Deal deal) {
        String sellerName = deal.getSeller().fullName.toLowerCase();
        logger.fine("Testing deal with seller name: " + sellerName);

        boolean result = value.stream()
                .anyMatch(keyword ->
                    sellerName.contains(keyword.toLowerCase()));

        logger.fine("Deal with seller name '" + sellerName + "' " + (result ? "matches" : "does not match")
                + " seller name keywords: " + value);
        return result;
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }
}
