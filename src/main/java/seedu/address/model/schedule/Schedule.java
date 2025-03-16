package seedu.address.model.schedule;

import java.time.LocalDateTime;

import seedu.address.model.client.ClientName;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyName;

/**
 * Represents a Schedule in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 * TODO: Implement this class
 * TODO: make all fields final
 */
public class Schedule {

    private PropertyName propertyName; // store property name instead of property index
    private ClientName clientName; // store client name instead of client index
    private LocalDateTime dateTime;
    private Note note; // make Note compulsory as it will be used as the title of the schedule card

    /**
     * TODO: Implement this method
     */
    public boolean isSameSchedule(Schedule otherSchedule) {
        return false;
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
}
