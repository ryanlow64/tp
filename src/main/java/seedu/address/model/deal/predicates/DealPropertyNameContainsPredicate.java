package seedu.address.model.deal.predicates;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.model.deal.Deal;

/**
 * Tests that a {@code Deal}'s {@code PropertyName} contains any of the keywords.
 */
public class DealPropertyNameContainsPredicate extends DealPredicate<List<String>> {

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
        return value.stream()
                .anyMatch(keyword ->
                    deal.getPropertyName().fullName.toLowerCase()
                        .contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }
}
