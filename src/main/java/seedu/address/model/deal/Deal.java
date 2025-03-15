package seedu.address.model.deal;

import seedu.address.commons.core.index.Index;
import seedu.address.model.commons.Price;

/**
 * Represents a Deal in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Deal {

    private final Index propertyId;
    private final Index buyerId;
    private final Index sellerId;
    private final Price price;
    private final DealStatus status;

    /**
     * Creates a new Deal with the specified details.
     * Status is set to PENDING by default.
     *
     * @param propertyId The ID of the property involved in the deal
     * @param buyerId The ID of the buyer client
     * @param sellerId The ID of the seller client
     * @param price The price of the deal
     */
    public Deal(Index propertyId, Index buyerId, Index sellerId, Price price) {
        this(propertyId, buyerId, sellerId, price, DealStatus.PENDING);
    }

    /**
     * Creates a new Deal with the specified details and status.
     *
     * @param propertyId The ID of the property involved in the deal
     * @param buyerId The ID of the buyer client
     * @param sellerId The ID of the seller client
     * @param price The price of the deal
     * @param status The status of the deal
     */
    public Deal(Index propertyId, Index buyerId, Index sellerId, Price price, DealStatus status) {
        this.propertyId = propertyId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.price = price;
        this.status = status;
    }

    public Index getPropertyId() {
        return propertyId;
    }

    public Index getBuyerId() {
        return buyerId;
    }

    public Index getSellerId() {
        return sellerId;
    }

    public Price getPrice() {
        return price;
    }

    public DealStatus getStatus() {
        return status;
    }

    /**
     * Returns true if both deals have the same property, buyer, and seller.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Deal)) {
            return false;
        }

        Deal otherDeal = (Deal) other;
        return propertyId.equals(otherDeal.propertyId)
                && buyerId.equals(otherDeal.buyerId)
                && sellerId.equals(otherDeal.sellerId);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Property ID: ")
                .append(propertyId.getOneBased())
                .append(" Buyer ID: ")
                .append(buyerId.getOneBased())
                .append(" Seller ID: ")
                .append(sellerId.getOneBased())
                .append(" Price: ")
                .append(price.value)
                .append(" Status: ")
                .append(status);
        return builder.toString();
    }
}
