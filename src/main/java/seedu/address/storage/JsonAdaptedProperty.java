package seedu.address.storage;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.commons.Address;
import seedu.address.model.property.Description;
import seedu.address.model.property.Price;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyName;
import seedu.address.model.property.Size;

/**
 * Jackson-friendly version of {@link Property}.
 */
public class JsonAdaptedProperty {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Property's %s field is missing!";

    private final String propertyName;
    private final String address;
    private final String price;
    private final String size;
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedProperty} with the given property details.
     */
    @JsonCreator
    public JsonAdaptedProperty(@JsonProperty("propertyName") String propertyName,
                               @JsonProperty("address") String address,
                               @JsonProperty("price") String price, @JsonProperty("size") String size,
                               @JsonProperty("description") String description) {
        this.propertyName = propertyName;
        this.address = address;
        this.price = price;
        this.size = size;
        this.description = description;
    }

    /**
     * Converts a given {@code Property} into this class for Jackson use.
     */
    public JsonAdaptedProperty(Property source) {
        propertyName = source.getPropertyName().fullName;
        address = source.getAddress().value;
        price = source.getPrice().map(p -> p.price.get()).orElse(null);
        size = source.getSize().map(s -> s.size.get()).orElse(null);
        description = source.getDescription().map(d -> d.description.get()).orElse(null);
    }

    /**
     * Converts this Jackson-friendly adapted property object into the model's {@code Property} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted property.
     */
    public Property toModelType() throws IllegalValueException {
        if (propertyName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PropertyName.class.getSimpleName()));
        }
        if (!PropertyName.isValidPropertyName(propertyName)) {
            throw new IllegalValueException(PropertyName.MESSAGE_CONSTRAINTS);
        }
        final PropertyName modelPropertyName = new PropertyName(propertyName);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Optional<Price> modelPrice = (price == null || price.isEmpty())
                ? Optional.empty() : Optional.of(new Price(price));

        final Optional<Size> modelSize = (size == null || size.isEmpty())
                ? Optional.empty() : Optional.of(new Size(size));

        final Optional<Description> modelDescription = (description == null || description.isEmpty())
                ? Optional.empty() : Optional.of(new Description(description));

        return new Property(modelPropertyName, modelAddress, modelPrice, modelSize, modelDescription);
    }
}
