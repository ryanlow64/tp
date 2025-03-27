package seedu.address.model.event.predicates;

import static java.util.Objects.requireNonNull;

import seedu.address.model.client.ClientName;
import seedu.address.model.event.Event;

/**
 * Tests that a {@code Event}'s {@code Client} matches the client given.
 */
public class EventWithClientPredicate extends EventPredicate<ClientName> {

    /**
     * Constructs a {@code EventWithClientPredicate}.
     *
     * @param clientName The client name to test against.
     */
    public EventWithClientPredicate(ClientName clientName) {
        super(clientName);
        requireNonNull(clientName);
    }

    @Override
    public boolean test(Event event) {
        return event.getClientName().equals(value);
    }
}
