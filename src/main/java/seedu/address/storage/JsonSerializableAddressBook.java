package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.client.Client;
import seedu.address.model.property.Property;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_CLIENT = "Clients list contains duplicate client(s).";
    public static final String MESSAGE_DUPLICATE_PROPERTY = "Properties list contains duplicate properties.";

    private final List<JsonAdaptedClient> clients = new ArrayList<>();
    private final List<JsonAdaptedProperty> properties = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given clients.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("clients") List<JsonAdaptedClient> clients,
                                       @JsonProperty("properties") List<JsonAdaptedProperty> properties) {
        this.clients.addAll(clients);
        this.properties.addAll(properties);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        clients.addAll(source.getClientList().stream().map(JsonAdaptedClient::new).toList());
        properties.addAll(source.getPropertyList().stream().map(JsonAdaptedProperty::new).toList());
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();

        for (JsonAdaptedClient jsonAdaptedClient : clients) {
            Client client = jsonAdaptedClient.toModelType();
            if (addressBook.hasClient(client)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CLIENT);
            }
            addressBook.addClient(client);
        }

        for (JsonAdaptedProperty jsonAdaptedProperty : properties) {
            Property property = jsonAdaptedProperty.toModelType();
            if (addressBook.hasProperty(property)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PROPERTY);
            }
            addressBook.addProperty(property);
        }

        return addressBook;
    }
}
