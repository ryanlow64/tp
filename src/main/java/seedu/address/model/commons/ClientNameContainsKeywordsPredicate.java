package seedu.address.model.commons;

import java.util.List;

import seedu.address.model.client.Client;

/**
 * Tests that a {@code Client}'s {@code Name} matches any of the keywords given.
 */
public class ClientNameContainsKeywordsPredicate extends NameContainsKeywordsPredicate<Client> {

    /**
     * Constructs a {@code ClientNameContainsKeywordsPredicate}.
     *
     * @param keywords The keywords to be used as a filter.
     */
    public ClientNameContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }
}
