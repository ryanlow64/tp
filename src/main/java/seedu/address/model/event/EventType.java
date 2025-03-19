package seedu.address.model.event;

/**
 * Represents the available types of events in the address book.
 */
public enum EventType {
    CONFERENCE, MEETING, OTHERS, WORKSHOP;

    public static final String MESSAGE_CONSTRAINTS =
            "Event types should only be \"conference\", \"meeting\", \"workshop\", \"others\"";

    /**
     * Returns a formatted string representation of the enum with only the first letter capitalized.
     */
    public String toFormattedString() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }
}
