package seedu.address.model.deal.predicates;

import static java.util.Objects.requireNonNull;

import java.util.stream.Stream;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.client.ClientName;
import seedu.address.model.deal.Deal;

/**
 * Tests that a {@code Deal}'s buyer name contains any of the keywords.
 */
public class DealBuyerNameContainsPredicate extends DealPredicate<ClientName> {

    /**
     * Constructs a {@code DealBuyerNameContainsPredicate}.
     *
     * @param buyerName The name of the buyer to be used as a filter.
     */
    public DealBuyerNameContainsPredicate(ClientName buyerName) {
        super(buyerName);
        requireNonNull(buyerName);
    }

    @Override
    public boolean test(Deal deal) {
        String[] keywords = value.fullName.split("\\s+");
        return Stream.of(keywords)
            .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(deal.getBuyer().fullName, keyword));
    }
}
