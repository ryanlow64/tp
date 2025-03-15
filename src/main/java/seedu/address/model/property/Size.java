package seedu.address.model.property;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Property's size in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSize(String)}
 */
public class Size {

    public static final String MESSAGE_CONSTRAINTS =
            "Size should only contain numbers, and it should be between 3 and 5 digits long (in Square Feet)";

    public static final String VALIDATION_REGEX = "\\d{3,}";

    public final String size;

    public Size(String size) {
        checkArgument(isValidSize(size), MESSAGE_CONSTRAINTS);
        this.size = size;
    }

    /**
     * Returns true if a given string is a valid size.
     */
    public static boolean isValidSize(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return size;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Size otherSize)) {
            return false;
        }

        return size.equals(otherSize.size);
    }

    @Override
    public int hashCode() {
        return size.hashCode();
    }
}
