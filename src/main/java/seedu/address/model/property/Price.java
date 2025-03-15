package seedu.address.model.property;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Property's price in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */
public class Price {

    public static final String MESSAGE_CONSTRAINTS =
            "Price should only contain numbers, and it should be between 1 and 3 digits long (in millions)";

    public static final String VALIDATION_REGEX = "\\d{1,3}";

    public final String price;

    public Price(String price) {
        checkArgument(isValidPrice(price), MESSAGE_CONSTRAINTS);
        this.price = price;
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidPrice(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return price;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Price otherPrice)) {
            return false;
        }

        return price.equals(otherPrice.price);
    }

    @Override
    public int hashCode() {
        return price.hashCode();
    }
}
