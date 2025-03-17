package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.commons.Price;
import seedu.address.model.deal.Deal;
import seedu.address.model.deal.DealStatus;

/**
 * Jackson-friendly version of {@link Deal}.
 */
public class JsonAdaptedDeal {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Deal's %s field is missing!";
    public static final String INVALID_INDEX_MESSAGE = "Index is not a non-zero unsigned integer.";
    public static final String INVALID_PRICE_MESSAGE = "Price must be a positive number and be under 9 quintillion.";
    public static final String INVALID_STATUS_MESSAGE = "Status must be one of 'PENDING', 'CLOSED', 'IN_NEGOTIATION'.";

    private final String propertyId;
    private final String buyerId;
    private final String sellerId;
    private final String price;
    private final String status;

    /**
     * Constructs a {@code JsonAdaptedDeal} with the given deal details.
     */
    @JsonCreator
    public JsonAdaptedDeal(@JsonProperty("propertyId") String propertyId,
                          @JsonProperty("buyerId") String buyerId,
                          @JsonProperty("sellerId") String sellerId,
                          @JsonProperty("price") String price,
                          @JsonProperty("status") String status) {
        this.propertyId = propertyId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.price = price;
        this.status = status;
    }

    /**
     * Converts a given {@code Deal} into this class for Jackson use.
     */
    public JsonAdaptedDeal(Deal source) {
        propertyId = String.valueOf(source.getPropertyId().getOneBased());
        buyerId = String.valueOf(source.getBuyerId().getOneBased());
        sellerId = String.valueOf(source.getSellerId().getOneBased());
        price = String.valueOf(source.getPrice().value);
        status = source.getStatus().name();
    }

    /**
     * Converts this Jackson-friendly adapted deal object into the model's {@code Deal} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted deal.
     */
    public Deal toModelType() throws IllegalValueException {
        // Property ID
        if (propertyId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Property ID"));
        }
        Index modelPropertyId;
        try {
            modelPropertyId = Index.fromOneBased(Integer.parseInt(propertyId));
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new IllegalValueException(INVALID_INDEX_MESSAGE);
        }

        // Buyer ID
        if (buyerId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Buyer ID"));
        }
        Index modelBuyerId;
        try {
            modelBuyerId = Index.fromOneBased(Integer.parseInt(buyerId));
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new IllegalValueException(INVALID_INDEX_MESSAGE);
        }

        // Seller ID
        if (sellerId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Seller ID"));
        }
        Index modelSellerId;
        try {
            modelSellerId = Index.fromOneBased(Integer.parseInt(sellerId));
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new IllegalValueException(INVALID_INDEX_MESSAGE);
        }

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

        return new Deal(modelPropertyId, modelBuyerId, modelSellerId, modelPrice, modelStatus);
    }
}
