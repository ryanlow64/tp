package seedu.address.model.client.predicates;

import seedu.address.model.client.Client;

/**
 * Tests that a {@code Client}'s {@code Email} contains the specified email.
 */
public class ClientEmailContainsPredicate extends ClientPredicate<String> {

    /**
     * Constructs a {@code ClientEmailContainsPredicate}.
     *
     * @param email The email to be used as a filter.
     */
    public ClientEmailContainsPredicate(String email) {
        super(email);
    }

    @Override
    public boolean test(Client client) {
        return client.getEmail().toString().contains(value);
    }
}
