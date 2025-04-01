package seedu.address.model.client;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.commons.Address;
import seedu.address.model.commons.Nameable;

/**
 * Represents a Client in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Client implements Nameable<Client> {

    // Identity fields
    private final ClientName clientName;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;

    /**
     * Every field must be present and not null.
     */
    public Client(ClientName clientName, Phone phone, Email email, Address address) {
        requireAllNonNull(clientName, phone, email, address);
        this.clientName = clientName;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    @Override
    public ClientName getFullName() {
        return clientName;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns true if both persons have the same clientName.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameClient(Client otherClient) {
        if (otherClient == this) {
            return true;
        }

        return otherClient != null
                && otherClient.getFullName().equals(getFullName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Client)) {
            return false;
        }

        Client otherClient = (Client) other;
        return clientName.equals(otherClient.clientName)
                && phone.equals(otherClient.phone)
                && email.equals(otherClient.email)
                && address.equals(otherClient.address);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(clientName, phone, email, address);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("clientName", clientName)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .toString();
    }

}
