package seedu.address.model.property;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Property's size in REconnect.
 * Guarantees: immutable; is valid as declared in {@link #isValidSize(String)}
 */
public class Size {

    public static final String MESSAGE_CONSTRAINTS =
            "Size should only contain integers from [100, 99999] (in square feet)";

    public static final String VALIDATION_REGEX = "([1-9]\\d{2,4})";

    public final String value;

    /**
     * Constructs a {@code Size}.
     *
     * @param size A valid size or empty string.
     */
    public Size(String size) {
        if (size == null || size.isEmpty() || size.equals("N/A") || size.equals("-")) {
            this.value = "-";
        } else {
            checkArgument(isValidSize(size), MESSAGE_CONSTRAINTS);
            this.value = size;
        }
    }

    /**
     * Returns true if the given size is more than this size.
     */
    public boolean isBiggerThan(Size other) {
        if (this.value.equals("-") || other.value.equals("-")) {
            return false;
        }
        return Integer.parseInt(this.value) > Integer.parseInt(other.value);
    }

    /**
     * Returns true if the given size is less than this size.
     */
    public boolean isSmallerThan(Size other) {
        if (this.value.equals("-") || other.value.equals("-")) {
            return false;
        }
        return Integer.parseInt(this.value) < Integer.parseInt(other.value);
    }

    /**
     * Returns true if a given string is a valid size.
     */
    public static boolean isValidSize(String test) {
        return test.isEmpty() || test.equals("N/A") || test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the size if present, otherwise returns "-".
     */
    @Override
    public String toString() {
        return value;
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

        return value.equals(otherSize.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
