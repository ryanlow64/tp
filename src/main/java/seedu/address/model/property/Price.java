package seedu.address.model.property;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Optional;

/**
 * Represents a Property's price in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */
public class Price {

    public static final String MESSAGE_CONSTRAINTS =
            "Price should only contain numbers from (0, 999.99] (in S$ millions)";

    public static final String VALIDATION_REGEX = "(?!0$)\\d{1,3}(\\.\\d{1,2})?";

    public final Optional<String> price;

    /**
     * Constructs a {@code Price}.
     *
     * @param price A valid price or empty string.
     */
    public Price(String price) {
        String trimmedPrice = price.trim();

        if (trimmedPrice.isBlank()) {
            this.price = Optional.empty();
        } else {
            checkArgument(isValidPrice(trimmedPrice), MESSAGE_CONSTRAINTS);
            this.price = Optional.of(trimmedPrice);
        }
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidPrice(String test) {
        return test.isBlank() || test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the price if present, otherwise returns "N/A".
     */
    @Override
    public String toString() {
        return price.orElse("N/A");
    }

    /**
     * Returns the optional price.
     */
    public Optional<String> getPrice() {
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
