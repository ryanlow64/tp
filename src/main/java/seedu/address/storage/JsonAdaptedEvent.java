package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.client.ClientName;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventType;
import seedu.address.model.event.Note;
import seedu.address.model.property.PropertyName;

/**
 * Jackson-friendly version of {@link Event}.
 */
public class JsonAdaptedEvent {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String dateTime;
    private final String eventType;
    private final String propertyName;
    private final String clientName;
    private final String note;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("dateTime") String dateTime,
                            @JsonProperty("eventType") String eventType,
                            @JsonProperty("propertyName") String propertyName,
                            @JsonProperty("clientName") String clientName,
                            @JsonProperty("note") String note) {
        this.dateTime = dateTime;
        this.eventType = eventType;
        this.propertyName = propertyName;
        this.clientName = clientName;
        this.note = note;
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        dateTime = source.getDateTime().toString();
        eventType = source.getEventType().toString();
        propertyName = source.getPropertyName().toString();
        clientName = source.getClientName().toString();
        note = source.getNote().toString();
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if any data constraints were violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        if (dateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Date & Time"));
        }
        LocalDateTime modelDateTime;
        try {
            modelDateTime = LocalDateTime.parse(dateTime);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException("Event's Date & Time field is corrupted.");
        }
        if (eventType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Type"));
        }
        EventType modelEventType;
        try {
            modelEventType = EventType.valueOf(eventType);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException("Event's Type field is corrupted.");
        }
        if (clientName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Client Name"));
        }
        if (!ClientName.isValidClientName(clientName)) {
            throw new IllegalValueException(ClientName.MESSAGE_CONSTRAINTS);
        }
        ClientName modelClientName = new ClientName(clientName);
        if (propertyName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Property Name"));
        }
        if (!PropertyName.isValidPropertyName(propertyName)) {
            throw new IllegalValueException(PropertyName.MESSAGE_CONSTRAINTS);
        }
        PropertyName modelPropertyName = new PropertyName(propertyName);
        if (note == null || note.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Note"));
        }
        Note modelNote = new Note(note);
        return new Event(modelDateTime, modelEventType, modelClientName, modelPropertyName, modelNote);
    }
}
