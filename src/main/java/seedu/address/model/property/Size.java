package seedu.address.model.property;

/**
 * Represents a Property's size in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSize(String)}
 * TODO: Implement this class
 * TODO: make all fields final
 */
public class Size {

    public final Integer value;

    public Size(int value) {
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid name.
     * TODO: Implement this method
     */
    public static boolean isValidSize(String test) {
        return true;
    }
}
