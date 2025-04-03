package seedu.address.model.client.predicates;
import seedu.address.model.client.Client;

/**
 * Tests that a {@code Client}'s {@code Address} contains the specified address.
 */
public class ClientAddressContainsPredicate extends ClientPredicate<String> {

    /**
     * Constructs a {@code ClientAddressContainsPredicate}.
     *
     * @param address The address to be used as a filter.
     */
    public ClientAddressContainsPredicate(String address) {
        super(address);
    }

    @Override
    public boolean test(Client client) {
        return client.getAddress().toString().toLowerCase().contains(value.toLowerCase());
    }
}

