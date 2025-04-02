package seedu.address.model.client.predicates;
import seedu.address.model.client.Client;

/**
 * Tests that a {@code Client}'s {@code Phone} contains the specified phone.
 */
public class ClientPhoneContainsPredicate extends ClientPredicate<String> {

    /**
     * Constructs a {@code ClientPhoneContainsPredicate}.
     *
     * @param phone The phone to be used as a filter.
     */
    public ClientPhoneContainsPredicate(String phone) {
        super(phone);
    }

    @Override
    public boolean test(Client client) {
        return client.getPhone().toString().contains(value);
    }
}
