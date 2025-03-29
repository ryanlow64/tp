package seedu.address.testutil;

import seedu.address.model.client.ClientName;
import seedu.address.model.commons.Price;
import seedu.address.model.deal.Deal;
import seedu.address.model.deal.DealStatus;
import seedu.address.model.property.PropertyName;

/**
 * A utility class to help with building Deal objects.
 */
public class DealBuilder {
    public static final String DEFAULT_PROPERTY_NAME = "Maple Villa Condominium";
    public static final String DEFAULT_BUYER = "Amy Bee";
    public static final String DEFAULT_SELLER = "John Doe";
    public static final long DEFAULT_PRICE = 100L;
    public static final DealStatus DEFAULT_STATUS = DealStatus.PENDING;

    private PropertyName propertyName;
    private ClientName buyer;
    private ClientName seller;
    private Price price;
    private DealStatus status;

    /**
     * Creates a {@code DealBuilder} with the default details.
     */
    public DealBuilder() {
        propertyName = new PropertyName(DEFAULT_PROPERTY_NAME);
        buyer = new ClientName(DEFAULT_BUYER);
        seller = new ClientName(DEFAULT_SELLER);
        price = new Price(DEFAULT_PRICE);
        status = DEFAULT_STATUS;
    }

    /**
     * Initializes the DealBuilder with the data of {@code dealToCopy}.
     */
    public DealBuilder(Deal dealToCopy) {
        propertyName = dealToCopy.getPropertyName();
        buyer = dealToCopy.getBuyer();
        seller = dealToCopy.getSeller();
        price = dealToCopy.getPrice();
        status = dealToCopy.getStatus();
    }

    /**
     * Sets the {@code PropertyName} of the {@code Deal} that we are building.
     */
    public DealBuilder withPropertyName(String propertyName) {
        this.propertyName = new PropertyName(propertyName);
        return this;
    }

    /**
     * Sets the buyer {@code ClientName} of the {@code Deal} that we are building.
     */
    public DealBuilder withBuyer(String buyer) {
        this.buyer = new ClientName(buyer);
        return this;
    }

    /**
     * Sets the seller {@code ClientName} of the {@code Deal} that we are building.
     */
    public DealBuilder withSeller(String seller) {
        this.seller = new ClientName(seller);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Deal} that we are building.
     */
    public DealBuilder withPrice(long price) {
        this.price = new Price(price);
        return this;
    }

    /**
     * Sets the {@code DealStatus} of the {@code Deal} that we are building.
     */
    public DealBuilder withStatus(DealStatus status) {
        this.status = status;
        return this;
    }

    public Deal build() {
        return new Deal(propertyName, buyer, seller, price, status);
    }
}
