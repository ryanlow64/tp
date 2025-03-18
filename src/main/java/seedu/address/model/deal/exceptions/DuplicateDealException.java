package seedu.address.model.deal.exceptions;

/**
 * Signals that the operation will result in duplicate Deals (Deals are considered duplicates if they have the same
 * identity).
 */
public class DuplicateDealException extends RuntimeException {
    public DuplicateDealException() {
        super("Operation would result in duplicate deals");
    }
}
