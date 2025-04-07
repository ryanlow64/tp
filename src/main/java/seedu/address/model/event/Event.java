package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.client.ClientName;
import seedu.address.model.property.PropertyName;

/**
 * Represents an Event in REconnect.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event implements Comparable<Event> {
    private final LocalDateTime dateTime;
    private final EventType eventType;
    private final ClientName clientName; // store client name instead of client index
    private final PropertyName propertyName; // store property name instead of property index
    private final Note note;

    /**
     * Creates a new {@code Event} object.
     *
     * @param dateTime The date and time of the event.
     * @param eventType The type of the event.
     * @param clientName The associated client's name.
     * @param propertyName The associated property's name.
     * @param note Additional notes about the event.
     */
    public Event(LocalDateTime dateTime, EventType eventType, ClientName clientName,
                 PropertyName propertyName, Note note) {
        requireAllNonNull(dateTime, eventType, clientName, propertyName, note);
        this.dateTime = dateTime;
        this.eventType = eventType;
        this.clientName = clientName;
        this.propertyName = propertyName;
        this.note = note;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public EventType getEventType() {
        return eventType;
    }

    public ClientName getClientName() {
        return clientName;
    }

    public PropertyName getPropertyName() {
        return propertyName;
    }

    public Note getNote() {
        return note;
    }

    /**
     * Returns true if both events are considered the same.
     * An event is the same as another if they both have the same date/time, event type, client and property.
     */
    public boolean isSameEvent(Event otherEvent) {
        return otherEvent != null
                && this.dateTime.equals(otherEvent.dateTime)
                && this.eventType.equals(otherEvent.eventType)
                && this.clientName.equals(otherEvent.clientName)
                && this.propertyName.equals(otherEvent.propertyName);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Event otherEvent) {
            return isSameEvent(otherEvent);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, eventType, clientName, propertyName, note);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("dateTime", dateTime)
                .add("eventType", eventType)
                .add("clientName", clientName)
                .add("propertyName", propertyName)
                .add("note", note)
                .toString();
    }

    /**
     * Compares this event with another event based on their dateTime.
     *
     * @param other The other event.
     * @return A negative integer, zero, or a positive integer
     *     based on whether this event is before, simultaneous with, or after the other event respectively.
     */
    @Override
    public int compareTo(Event other) {
        return this.dateTime.compareTo(other.dateTime);
    }
}
