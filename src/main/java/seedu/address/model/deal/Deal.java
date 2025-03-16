package seedu.address.model.deal;

import seedu.address.model.client.ClientName;
import seedu.address.model.commons.Price;
import seedu.address.model.property.PropertyName;

/**
 * Represents a Deal in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 * TODO: Implement this class
 * TODO: make all fields final
 */
public class Deal {

    private PropertyName propertyName;
    private ClientName buyer;
    private ClientName seller;
    private Price price;
    private DealStatus status; // default status is PENDING

    /**
     * TODO: Modify this.
     */
    public boolean isSameDeal(Deal otherDeal) {
        return false;
    }

    public PropertyName getPropertyName() {
        return propertyName;
    }

    public ClientName getBuyer() {
        return buyer;
    }

    public ClientName getSeller() {
        return seller;
    }

    public Price getPrice() {
        return price;
    }

    public DealStatus getStatus() {
        return status;
    }
}
