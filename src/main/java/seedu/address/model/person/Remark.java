package seedu.address.model.person;

import java.util.Objects;

/**
 * Represents a Person's remark in the address book.
 * The remark can be left empty.
 */
public class Remark {
    public final String value;

    public Remark(String remark) {
        this.value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
