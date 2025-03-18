package seedu.address.model.property;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Optional;

/**
 * Represents a Property's description in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class Description {

    public static final String MESSAGE_CONSTRAINTS =
            "Description should be between 1 and 50 characters.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = ".{1,50}";

    public final Optional<String> description;

    /**
     * Constructs a {@code Description}.
     *
     * @param description A valid description or empty string.
     */
    public Description(String description) {
        if (description == null || description.isBlank()) {
            this.description = Optional.empty();
        } else {
            checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
            this.description = Optional.of(description);
        }
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return test.isBlank() || test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the description if present, otherwise returns "N/A".
     */
    @Override
    public String toString() {
        return description.orElse("N/A");
    }

    /**
     * Returns the optional description.
     */
    public Optional<String> getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Description otherDescription)) {
            return false;
        }

        return description.equals(otherDescription.description);
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }
}
