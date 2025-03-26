package seedu.address.model.deal.predicates;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.model.deal.Deal;

/**
 * Tests that a {@code Deal}'s buyer name contains any of the keywords.
 */
public class DealBuyerNameContainsPredicate extends DealPredicate<List<String>> {

    private static final Logger logger = Logger.getLogger(DealBuyerNameContainsPredicate.class.getName());

    /**
     * Constructs a {@code DealBuyerNameContainsPredicate}.
     *
     * @param keywords The list of keywords to match against deal's buyer name.
     */
    public DealBuyerNameContainsPredicate(List<String> keywords) {
        super(keywords);
        requireNonNull(keywords);
    }

    @Override
    public boolean test(Deal deal) {
        String buyerName = deal.getBuyer().fullName.toLowerCase();
        logger.fine("Testing deal with buyer name: " + buyerName);

        boolean result = value.stream()
                .anyMatch(keyword ->
                    buyerName.contains(keyword.toLowerCase()));

        logger.fine("Deal with buyer name '" + buyerName + "' " + (result ? "matches" : "does not match")
                + " buyer name keywords: " + value);
        return result;
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }
}
