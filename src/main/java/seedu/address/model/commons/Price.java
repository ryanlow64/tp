package seedu.address.model.commons;

/**
 * Represents a Property's price.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */
public class Price {

    public static final String MESSAGE_CONSTRAINTS =
            "Price must be a positive number and be under 9 quintillion.";

    public final Long value;

    /**
     * Constructs a {@code Price}.
     *
     * @param value A valid price value.
     */
    public Price(long value) {
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidPrice(String test) {
        try {
            long price = Long.parseLong(test);
            return price > 0 && price < 9000000000000000000L;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Price)) {
            return false;
        }

        Price otherPrice = (Price) other;
        return value.equals(otherPrice.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
