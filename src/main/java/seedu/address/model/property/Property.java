package seedu.address.model.property;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.commons.Address;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Property in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.

 */
public class Property {
    private final PropertyName propertyName;
    private final Address address;
    private final Optional<Price> price;
    private final Optional<Size> size;
    private final Optional<String> description;

    /**
     * Every field must be present and not null.
     */
    public Property(PropertyName propertyName, Address address, Optional<Price> price, Optional<Size> size, Optional<String> description) {
        requireAllNonNull(propertyName, address, size, description);
        this.propertyName = propertyName;
        this.address = address;
        this.price = price;
        this.size = size;
        this.description = description;
    }

    public PropertyName getPropertyName() {
        return propertyName;
    }

    public Address getAddress() {
        return address;
    }

    public Optional<Price> getPrice() {
        return price;
    }

    public Optional<Size> getSize() {
        return size;
    }

    public Optional<String> getDescription() {
        return description;
    }

    /**
     * Returns true if both properties have the same propertyName.
     * This defines a weaker notion of equality between two properties.
     */
    public boolean isSameProperty(Property otherProperty) {
        if (otherProperty == this) {
            return true;
        }

        return otherProperty != null
                && otherProperty.getPropertyName().equals(getPropertyName());
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
        if (!(other instanceof Property otherProperty)) {
            return false;
        }

        return propertyName.equals(otherProperty.propertyName)
                && address.equals(otherProperty.address)
                && price.equals(otherProperty.price)
                && size.equals(otherProperty.size)
                && description.equals(otherProperty.description);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(propertyName, address, price, size, description);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("propertyName", propertyName)
                .add("address", address)
                .add("price", price)
                .add("size", size)
                .add("description", description)
                .toString();
    }
}
