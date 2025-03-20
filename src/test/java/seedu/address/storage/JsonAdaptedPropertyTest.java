package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedProperty.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProperties.MAPLE;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.commons.Address;
import seedu.address.model.commons.Price;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyName;
import seedu.address.model.property.Size;

public class JsonAdaptedPropertyTest {
    private static final String INVALID_PROPERTY_NAME = " ";
    private static final String INVALID_ADDRESS = " ";
    private static final Long INVALID_PRICE = 0L;
    private static final String INVALID_SIZE = "5000a";
    private static final String INVALID_DESCRIPTION = " ";

    private static final String VALID_PROPERTY_NAME = MAPLE.getPropertyName().toString();
    private static final String VALID_ADDRESS = MAPLE.getAddress().toString();
    private static final Long VALID_PRICE = MAPLE.getPrice().value;
    private static final String VALID_SIZE = MAPLE.getSize().map(s -> s.value).orElse("");
    private static final String VALID_DESCRIPTION =
            MAPLE.getDescription().map(d -> d.description.get()).orElse("");

    @Test
    public void toModelType_validPropertyDetails_returnsProperty() throws Exception {
        JsonAdaptedProperty jsonAdaptedProperty = new JsonAdaptedProperty(MAPLE);
        assertEquals(MAPLE, jsonAdaptedProperty.toModelType());
    }

    @Test
    public void toModelType_invalidPropertyName_throwsIllegalValueException() {
        JsonAdaptedProperty jsonAdaptedProperty =
                new JsonAdaptedProperty(INVALID_PROPERTY_NAME, VALID_ADDRESS, VALID_PRICE, VALID_SIZE,
                        VALID_DESCRIPTION);
        String expectedMessage = PropertyName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedProperty::toModelType);
    }

    @Test
    public void toModelType_nullPropertyName_throwsIllegalValueException() {
        JsonAdaptedProperty jsonAdaptedProperty =
                new JsonAdaptedProperty(null, VALID_ADDRESS, VALID_PRICE, VALID_SIZE, VALID_DESCRIPTION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PropertyName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedProperty::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedProperty jsonAdaptedProperty =
                new JsonAdaptedProperty(VALID_PROPERTY_NAME, INVALID_ADDRESS, VALID_PRICE, VALID_SIZE,
                        VALID_DESCRIPTION);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedProperty::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedProperty jsonAdaptedProperty =
                new JsonAdaptedProperty(VALID_PROPERTY_NAME, null, VALID_PRICE, VALID_SIZE, VALID_DESCRIPTION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedProperty::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        JsonAdaptedProperty jsonAdaptedProperty =
                new JsonAdaptedProperty(VALID_PROPERTY_NAME, VALID_ADDRESS, INVALID_PRICE, VALID_SIZE,
                        VALID_DESCRIPTION);
        String expectedMessage = Price.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedProperty::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        JsonAdaptedProperty jsonAdaptedProperty =
                new JsonAdaptedProperty(VALID_PROPERTY_NAME, VALID_ADDRESS, null, VALID_SIZE, VALID_DESCRIPTION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedProperty::toModelType);
    }

    @Test
    public void toModelType_invalidSize_throwsIllegalValueException() {
        JsonAdaptedProperty jsonAdaptedProperty =
                new JsonAdaptedProperty(VALID_PROPERTY_NAME, VALID_ADDRESS, VALID_PRICE, INVALID_SIZE,
                        VALID_DESCRIPTION);
        String expectedMessage = Size.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalArgumentException.class, expectedMessage, jsonAdaptedProperty::toModelType);
    }

    @Test
    public void toModelType_nullSize_returnsPropertyWithEmptySize() throws Exception {
        JsonAdaptedProperty jsonAdaptedProperty =
                new JsonAdaptedProperty(VALID_PROPERTY_NAME, VALID_ADDRESS, VALID_PRICE, null, VALID_DESCRIPTION);
        Property property = jsonAdaptedProperty.toModelType();
        assertEquals(Optional.empty(), property.getSize());
    }

    @Test
    public void toModelType_nullDescription_returnsPropertyWithEmptyDescription() throws Exception {
        JsonAdaptedProperty jsonAdaptedProperty =
                new JsonAdaptedProperty(VALID_PROPERTY_NAME, VALID_ADDRESS, VALID_PRICE, VALID_SIZE, null);
        Property property = jsonAdaptedProperty.toModelType();
        assertEquals(Optional.empty(), property.getDescription());
    }
}
