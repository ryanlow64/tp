package seedu.address.model.event;

import java.time.LocalDateTime;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.client.ClientName;
import seedu.address.model.property.PropertyName;

/**
 * Represents an Event in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 * TODO: Implement this class
 * TODO: make all fields final
 */
public class Event {
    private EventType eventType;
    private PropertyName propertyName; // store property name instead of property index
    private ClientName clientName; // store client name instead of client index
    private LocalDateTime dateTime;
    private Note note; // make Note compulsory as it will be used as the title of the event card

    public Event(EventType eventType, PropertyName propertyName, ClientName clientName, LocalDateTime dateTime, Note note) {
        this.eventType = eventType;
        this.propertyName = propertyName;
        this.clientName = clientName;
        this.dateTime = dateTime;
        this.note = note;
    }

    public EventType getEventType() {
        return eventType;
    }

    public PropertyName getPropertyName() {
        return propertyName;
    }

    public ClientName getClientName() {
        return clientName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Note getNote() {
        return note;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Event)) {
            return false;
        }
        Event event = (Event) other;
        return this.eventType.equals(event.eventType)
                && this.propertyName.equals(event.propertyName)
                && this.clientName.equals(event.clientName)
                && this.dateTime.equals(event.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventType, propertyName, clientName, dateTime, note);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("eventType", eventType)
                .add("propertyName", propertyName)
                .add("clientName", clientName)
                .add("dateTime", dateTime)
                .add("note", note)
                .toString();
    }
}
