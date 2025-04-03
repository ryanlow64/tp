package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.EVENT1;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.client.ClientName;
import seedu.address.model.property.PropertyName;

public class JsonAdaptedEventTest {
    private static final String VALID_DATETIME = EVENT1.getDateTime().toString();
    private static final String VALID_EVENT_TYPE = EVENT1.getEventType().toString();
    private static final String VALID_PROPERTY_NAME = EVENT1.getPropertyName().toString();
    private static final String VALID_CLIENT_NAME = EVENT1.getClientName().toString();
    private static final String VALID_NOTE = EVENT1.getNote().toString();

    private static final String INVALID_DATETIME = "arnold schwarzenegger";
    private static final String INVALID_EVENT_TYPE = "sleep";
    private static final String INVALID_CLIENT_NAME = "Arnold Schw@rzenegger";
    private static final String INVALID_PROPERTY_NAME = "condo!";
    private static final String INVALID_NOTE = "";

    @Test
    public void toModelType_validEventDetails_returnsEvent() throws Exception {
        JsonAdaptedEvent event = new JsonAdaptedEvent(EVENT1);
        assertEquals(EVENT1, event.toModelType());
    }

    @Test
    public void toModelType_nullDateTime_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
                null, VALID_EVENT_TYPE, VALID_PROPERTY_NAME, VALID_CLIENT_NAME, VALID_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Date & Time");
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidDateTime_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
                INVALID_DATETIME, VALID_EVENT_TYPE, VALID_PROPERTY_NAME, VALID_CLIENT_NAME, VALID_NOTE);
        String expectedMessage = "Event's Date & Time field is corrupted.";
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEventType_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
                VALID_DATETIME, null, VALID_PROPERTY_NAME, VALID_CLIENT_NAME, VALID_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Type");
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidEventType_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
                VALID_DATETIME, INVALID_EVENT_TYPE, VALID_PROPERTY_NAME, VALID_CLIENT_NAME, VALID_NOTE);
        String expectedMessage = "Event's Type field is corrupted.";
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullClientName_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
                VALID_DATETIME, VALID_EVENT_TYPE, VALID_PROPERTY_NAME, null, VALID_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Client Name");
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidClientName_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
                VALID_DATETIME, VALID_EVENT_TYPE, VALID_PROPERTY_NAME, INVALID_CLIENT_NAME, VALID_NOTE);
        String expectedMessage = ClientName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullPropertyName_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
                VALID_DATETIME, VALID_EVENT_TYPE, null, VALID_CLIENT_NAME, VALID_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Property Name");
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidPropertyName_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
                VALID_DATETIME, VALID_EVENT_TYPE, INVALID_PROPERTY_NAME, VALID_CLIENT_NAME, VALID_NOTE);
        String expectedMessage = PropertyName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullNote_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
                VALID_DATETIME, VALID_EVENT_TYPE, VALID_PROPERTY_NAME, VALID_CLIENT_NAME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Note");
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidNote_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
                VALID_DATETIME, VALID_EVENT_TYPE, VALID_PROPERTY_NAME, VALID_CLIENT_NAME, INVALID_NOTE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Note");
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }
}
