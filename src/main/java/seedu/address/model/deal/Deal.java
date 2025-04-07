package seedu.address.model.deal;

import static java.util.Objects.requireNonNull;

import seedu.address.model.client.ClientName;
import seedu.address.model.commons.Price;
import seedu.address.model.property.PropertyName;

/**
 * Represents a Deal in REconnect.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Deal {

    private final PropertyName propertyName;
    private final ClientName buyer;
    private final ClientName seller;
    private final Price price;
    private final DealStatus status; // default status is PENDING

    /**
     * Creates a new Deal with the specified details.
     * Status is set to PENDING by default.
     *
     * @param propertyName The name of the property involved in the deal
     * @param buyer The name of the buyer client
     * @param seller The name of the seller client
     * @param price The price of the deal
     */
    public Deal(PropertyName propertyName, ClientName buyer, ClientName seller, Price price) {
        this(propertyName, buyer, seller, price, DealStatus.PENDING);
    }

    /**
     * Creates a new Deal with the specified details and status.
     *
     * @param propertyName The name of the property involved in the deal
     * @param buyer The name of the buyer client
     * @param seller The name of the seller client
     * @param price The price of the deal
     * @param status The status of the deal
     */
    public Deal(PropertyName propertyName, ClientName buyer, ClientName seller, Price price, DealStatus status) {
        requireNonNull(propertyName);
        requireNonNull(buyer);
        requireNonNull(seller);
        requireNonNull(price);
        requireNonNull(status);
        this.propertyName = propertyName;
        this.buyer = buyer;
        this.seller = seller;
        this.price = price;
        this.status = status;
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

    /**
     * Returns true if both deals have the same property, buyer, and seller.
     * This defines a weaker notion of equality between two deals.
     *
     * @param otherDeal The other deal to compare with
     * @return true if both deals have the same property, buyer, and seller
     */
    public boolean isSameDeal(Deal otherDeal) {
        if (otherDeal == this) {
            return true;
        }

        return otherDeal != null
                && otherDeal.getPropertyName().equals(getPropertyName())
                && otherDeal.getBuyer().equals(getBuyer())
                && otherDeal.getSeller().equals(getSeller());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Deal otherDeal)) {
            return false;
        }

        return propertyName.equals(otherDeal.propertyName)
                && buyer.equals(otherDeal.buyer)
                && seller.equals(otherDeal.seller)
                && price.equals(otherDeal.price)
                && status.equals(otherDeal.status);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Property: ")
                .append(propertyName)
                .append(" Buyer: ")
                .append(buyer)
                .append(" Seller: ")
                .append(seller)
                .append(" Price: ")
                .append(price.value)
                .append(" Status: ")
                .append(status);
        return builder.toString();
    }
}
