package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.commons.Name;

/**
 * Represents a Property's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPropertyName(String)}
 * TODO: Implement this class
 * TODO: make all fields final
 */
public class PropertyName implements Name<Property> {

    public static final String MESSAGE_CONSTRAINTS = "Property name must not be empty.";

    public final String fullName;

    /**
     * Constructs a {@code PropertyName}.
     *
     * @param name A valid name.
     */
    public PropertyName(String name) {
        requireNonNull(name);
        checkArgument(isValidPropertyName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidPropertyName(String test) {
        return test != null && !test.trim().isEmpty();
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PropertyName)) {
            return false;
        }

        PropertyName otherName = (PropertyName) other;
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }
}
