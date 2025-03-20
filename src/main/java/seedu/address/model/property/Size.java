package seedu.address.model.property;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Optional;

/**
 * Represents a Property's size in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSize(String)}
 */
public class Size {

    public static final String MESSAGE_CONSTRAINTS =
            "Size should only contain integers from [100, 99999] (in square feet)";

    public static final String VALIDATION_REGEX = "([1-9]\\d{2,4})";

    public final Optional<String> size;

    /**
     * Constructs a {@code Size}.
     *
     * @param size A valid size or empty string.
     */
    public Size(String size) {
        if (size == null || size.isBlank()) {
            this.size = Optional.empty();
        } else {
            checkArgument(isValidSize(size), MESSAGE_CONSTRAINTS);
            this.size = Optional.of(size);
        }
    }

    /**
     * Returns true if a given string is a valid size.
     */
    public static boolean isValidSize(String test) {
        return test.isBlank() || test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the size if present, otherwise returns "N/A".
     */
    @Override
    public String toString() {
        return size.orElse("N/A");
    }

    /**
     * Returns the optional size.
     */
    public Optional<String> getSize() {
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
