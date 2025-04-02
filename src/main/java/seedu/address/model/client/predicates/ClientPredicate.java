package seedu.address.model.client.predicates;

import java.util.function.Predicate;

import seedu.address.model.client.Client;

/**
 * Generic class for predicates that are used to filter clients.
 */
public abstract class ClientPredicate<T> implements Predicate<Client> {
    protected final T value;

    public ClientPredicate(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ClientPredicate otherPredicate)) {
            return false;
        }

        return value.equals(otherPredicate.value);
    }
}
