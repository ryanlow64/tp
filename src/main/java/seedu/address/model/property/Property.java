package seedu.address.model.property;

import java.util.Optional;

import seedu.address.model.commons.Address;
import seedu.address.model.commons.Price;

/**
 * Represents a Property in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 * TODO: Implement this class
 * TODO: make all fields final
 */
public class Property {

    private PropertyName propertyName;
    private Address address;
    private Price price;
    private Optional<Size> size;
    private Optional<String> description;

    public PropertyName getPropertyName() {
        return propertyName;
    }

    public Address getAddress() {
        return address;
    }

    public Price getPrice() {
        return price;
    }

    public Optional<Size> getSize() {
        return size;
    }

    public Optional<String> getDescription() {
        return description;
    }
}
