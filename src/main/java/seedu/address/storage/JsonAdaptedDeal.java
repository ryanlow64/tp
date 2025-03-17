package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.client.ClientName;
import seedu.address.model.commons.Price;
import seedu.address.model.deal.Deal;
import seedu.address.model.deal.DealStatus;
import seedu.address.model.property.PropertyName;

/**
 * Jackson-friendly version of {@link Deal}.
 */
public class JsonAdaptedDeal {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Deal's %s field is missing!";
    public static final String INVALID_PRICE_MESSAGE = "Price must be a positive number and be under 9 quintillion.";
    public static final String INVALID_STATUS_MESSAGE = "Status must be one of 'PENDING', 'CLOSED', 'IN_NEGOTIATION'.";
    public static final String INVALID_NAME_MESSAGE = "Name must be a valid string.";

    private final String propertyName;
    private final String buyer;
    private final String seller;
    private final String price;
    private final String status;

    /**
     * Constructs a {@code JsonAdaptedDeal} with the given deal details.
     */
    @JsonCreator
    public JsonAdaptedDeal(@JsonProperty("propertyName") String propertyName,
                          @JsonProperty("buyer") String buyer,
                          @JsonProperty("seller") String seller,
                          @JsonProperty("price") String price,
                          @JsonProperty("status") String status) {
        this.propertyName = propertyName;
        this.buyer = buyer;
        this.seller = seller;
        this.price = price;
        this.status = status;
    }

    /**
     * Converts a given {@code Deal} into this class for Jackson use.
     */
    public JsonAdaptedDeal(Deal source) {
        propertyName = source.getPropertyName().toString();
        buyer = source.getBuyer().toString();
        seller = source.getSeller().toString();
        price = String.valueOf(source.getPrice().value);
        status = source.getStatus().name();
    }

    /**
     * Converts this Jackson-friendly adapted deal object into the model's {@code Deal} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted deal.
     */
    public Deal toModelType() throws IllegalValueException {
        // Property Name
        if (propertyName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Property Name"));
        }
        if (!PropertyName.isValidPropertyName(propertyName)) {
            throw new IllegalValueException(INVALID_NAME_MESSAGE);
        }
        PropertyName modelPropertyName = new PropertyName(propertyName);

        // Buyer
        if (buyer == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Buyer"));
        }
        if (!ClientName.isValidClientName(buyer)) {
            throw new IllegalValueException(INVALID_NAME_MESSAGE);
        }
        ClientName modelBuyer = new ClientName(buyer);

        // Seller
        if (seller == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Seller"));
        }
        if (!ClientName.isValidClientName(seller)) {
            throw new IllegalValueException(INVALID_NAME_MESSAGE);
        }
        ClientName modelSeller = new ClientName(seller);

        // Price
        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Price"));
        }
        if (!Price.isValidPrice(price)) {
            throw new IllegalValueException(INVALID_PRICE_MESSAGE);
        }
        Price modelPrice = new Price(Long.parseLong(price));

        // Status
        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Status"));
        }
        DealStatus modelStatus;
        try {
            modelStatus = DealStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(INVALID_STATUS_MESSAGE);
        }

        return new Deal(modelPropertyName, modelBuyer, modelSeller, modelPrice, modelStatus);
    }
}
