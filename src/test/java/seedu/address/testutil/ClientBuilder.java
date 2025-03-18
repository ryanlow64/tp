package seedu.address.testutil;

import seedu.address.model.client.Client;
import seedu.address.model.client.ClientName;
import seedu.address.model.client.Email;
import seedu.address.model.client.Phone;
import seedu.address.model.commons.Address;

/**
 * A utility class to help with building Client objects.
 */
public class ClientBuilder {

    public static final String DEFAULT_CLIENT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private ClientName clientName;
    private Phone phone;
    private Email email;
    private Address address;

    /**
     * Creates a {@code ClientBuilder} with the default details.
     */
    public ClientBuilder() {
        clientName = new ClientName(DEFAULT_CLIENT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
    }

    /**
     * Initializes the ClientBuilder with the data of {@code clientToCopy}.
     */
    public ClientBuilder(Client clientToCopy) {
        clientName = clientToCopy.getClientName();
        phone = clientToCopy.getPhone();
        email = clientToCopy.getEmail();
        address = clientToCopy.getAddress();
    }

    /**
     * Sets the {@code ClientName} of the {@code Client} that we are building.
     */
    public ClientBuilder withClientName(String clientName) {
        this.clientName = new ClientName(clientName);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Client} that we are building.
     */
    public ClientBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Client} that we are building.
     */
    public ClientBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Client} that we are building.
     */
    public ClientBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Client build() {
        return new Client(clientName, phone, email, address);
    }

}
