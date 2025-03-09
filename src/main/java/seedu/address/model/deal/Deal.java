package seedu.address.model.deal;

import seedu.address.commons.core.index.Index;
import seedu.address.model.commons.Price;

/**
 * Represents a Deal in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 * TODO: Implement this class
 * TODO: make all fields final
 */
public class Deal {

    private Index propertyID;
    private Index buyerID;
    private Index sellerID;
    private Price price;
    private DealStatus status; // default status is PENDING

}
