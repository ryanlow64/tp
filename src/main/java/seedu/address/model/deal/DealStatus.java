package seedu.address.model.deal;

/**
 * Represents the status of a deal in the REconnect application.
 */
public enum DealStatus {
    OPEN, PENDING, CLOSED;

    public static final String MESSAGE_CONSTRAINTS =
        "Deal status should only be one of \"OPEN\", \"PENDING\", or \"CLOSED\" (case insensitive).";
}
