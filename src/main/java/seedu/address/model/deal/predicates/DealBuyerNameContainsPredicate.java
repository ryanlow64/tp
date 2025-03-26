package seedu.address.model.deal.predicates;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.model.deal.Deal;

/**
 * Tests that a {@code Deal}'s buyer name contains any of the keywords.
 */
public class DealBuyerNameContainsPredicate extends DealPredicate<List<String>> {

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
        return value.stream()
                .anyMatch(keyword -> 
                    deal.getBuyer().fullName.toLowerCase()
                        .contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }
} 