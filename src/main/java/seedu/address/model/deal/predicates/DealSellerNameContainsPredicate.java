package seedu.address.model.deal.predicates;

import static java.util.Objects.requireNonNull;

import java.util.stream.Stream;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.client.ClientName;
import seedu.address.model.deal.Deal;

/**
 * Tests that a {@code Deal}'s seller name contains any of the keywords.
 */
public class DealSellerNameContainsPredicate extends DealPredicate<ClientName> {

    /**
     * Constructs a {@code DealSellerNameContainsPredicate}.
     *
     * @param sellerName The name of the seller to be used as a filter.
     */
    public DealSellerNameContainsPredicate(ClientName sellerName) {
        super(sellerName);
        requireNonNull(sellerName);
    }

    @Override
    public boolean test(Deal deal) {
        String[] keywords = value.fullName.split("\\s+");
        return Stream.of(keywords)
            .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(deal.getSeller().fullName, keyword));
    }
}
