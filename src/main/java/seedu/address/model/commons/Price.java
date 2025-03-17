package seedu.address.model.commons;

/**
 * Represents a Property's price in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */
public class Price {

    public static final String MESSAGE_CONSTRAINTS =
            "Price should only contain numbers from (0, 999.99] (in S$ millions)";

    public static final String VALIDATION_REGEX = "(?!0$)\\d{1,3}(\\.\\d{1,2})?";

    public final Long value;

    /**
     * Constructs a {@code Price}.
     *
     * @param price A valid price value
     */
    public Price(Long price) {
        this.value = price;
    }

    /**
     * Constructs a {@code Price}.
     *
     * @param price A valid price value
     */
    public Price(Integer price) {
        this.value = Long.valueOf(price);
    }

    /**
     * Constructs a {@code Price}.
     *
     * @param price A valid price string
     */
    public Price(String price) {
        if (!isValidPrice(price)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        try {
            this.value = Long.parseLong(price);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidPrice(String test) {
        if (test == null) {
            return false;
        }
        return !test.isBlank() && test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the price if present, otherwise returns "N/A".
     */
    @Override
    public String toString() {
        return value.toString();
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

        return value.equals(otherPrice.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
