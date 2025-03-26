package seedu.address.model.event.predicates;

import static java.util.Objects.requireNonNull;

import seedu.address.model.client.ClientName;
import seedu.address.model.event.Event;

/**
 * Tests that a {@code Event}'s {@code Client} matches the client given.
 */
public class EventWithClientPredicate extends EventPredicate {

    private final ClientName clientName;

    /**
     * Constructs a {@code EventWithClientPredicate}.
     *
     * @param clientName The client name to test against.
     */
    public EventWithClientPredicate(ClientName clientName) {
        requireNonNull(clientName);
        this.clientName = clientName;
    }

    @Override
    public boolean test(Event event) {
        return event.getClientName().equals(clientName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EventWithClientPredicate otherPredicate)) {
            return false;
        }

        return clientName.equals(otherPredicate.clientName);
    }
}
