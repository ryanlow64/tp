package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.client.ClientName;
import seedu.address.model.property.PropertyName;

/**
 * Represents an Event in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event implements Comparable<Event> {
    private final EventType eventType;
    private final PropertyName propertyName; // store property name instead of property index
    private final ClientName clientName; // store client name instead of client index
    private final LocalDateTime dateTime;
    private final Note note;

    /**
     * Creates a new {@code Event} object.
     *
     * @param eventType The type of the event.
     * @param propertyName The associated property's name.
     * @param clientName The associated client's name.
     * @param dateTime The date and time of the event.
     * @param note Additional notes about the event.
     */
    public Event(EventType eventType, PropertyName propertyName,
                 ClientName clientName, LocalDateTime dateTime, Note note) {
        requireAllNonNull(eventType, propertyName, clientName, dateTime, note);
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

    /**
     * Returns true if both events are considered the same.
     * An event is the same as another if they both have the same event type, property, client and date/time.
     */
    public boolean isSameEvent(Event otherEvent) {
        return otherEvent != null
                && this.eventType.equals(otherEvent.eventType)
                && this.propertyName.equals(otherEvent.propertyName)
                && this.clientName.equals(otherEvent.clientName)
                && this.dateTime.equals(otherEvent.dateTime);
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
