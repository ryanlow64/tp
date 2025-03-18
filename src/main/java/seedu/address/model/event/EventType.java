package seedu.address.model.event;

public enum EventType {
    CONFERENCE, MEETING, OTHERS, WORKSHOP;

    public static final String MESSAGE_CONSTRAINTS =
            "Event types should only be \"conference\", \"meeting\", \"workshop\", \"others\"";
}
