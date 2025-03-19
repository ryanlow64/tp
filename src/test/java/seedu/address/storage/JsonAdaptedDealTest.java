package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedDeal.INVALID_NAME_MESSAGE;
import static seedu.address.storage.JsonAdaptedDeal.INVALID_PRICE_MESSAGE;
import static seedu.address.storage.JsonAdaptedDeal.INVALID_STATUS_MESSAGE;
import static seedu.address.storage.JsonAdaptedDeal.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.client.ClientName;
import seedu.address.model.commons.Price;
import seedu.address.model.deal.Deal;
import seedu.address.model.deal.DealStatus;
import seedu.address.model.property.PropertyName;

public class JsonAdaptedDealTest {
    private static final String INVALID_PROPERTY_NAME = "";
    private static final String INVALID_BUYER_NAME = "";
    private static final String INVALID_SELLER_NAME = "";
    private static final Long INVALID_PRICE = -100L;
    private static final String INVALID_STATUS = "UNKNOWN";

    private static final String VALID_PROPERTY_NAME = "Sunset Villa";
    private static final String VALID_BUYER_NAME = "John Doe";
    private static final String VALID_SELLER_NAME = "Jane Smith";
    private static final Long VALID_PRICE = 500L;
    private static final String VALID_STATUS = "PENDING";

    @Test
    public void toModelType_validDealDetails_returnsDeal() throws Exception {
        Deal deal = new Deal(
            new PropertyName(VALID_PROPERTY_NAME),
            new ClientName(VALID_BUYER_NAME),
            new ClientName(VALID_SELLER_NAME),
            new Price(VALID_PRICE),
            DealStatus.valueOf(VALID_STATUS)
        );
        JsonAdaptedDeal jsonDeal = new JsonAdaptedDeal(deal);
        assertEquals(deal, jsonDeal.toModelType());
    }

    @Test
    public void toModelType_invalidPropertyName_throwsIllegalValueException() {
        JsonAdaptedDeal deal = new JsonAdaptedDeal(
            INVALID_PROPERTY_NAME, VALID_BUYER_NAME, VALID_SELLER_NAME, VALID_PRICE, VALID_STATUS);
        String expectedMessage = INVALID_NAME_MESSAGE;
        assertThrows(IllegalValueException.class, expectedMessage, deal::toModelType);
    }

    @Test
    public void toModelType_nullPropertyName_throwsIllegalValueException() {
        JsonAdaptedDeal deal = new JsonAdaptedDeal(
            null, VALID_BUYER_NAME, VALID_SELLER_NAME, VALID_PRICE, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Property Name");
        assertThrows(IllegalValueException.class, expectedMessage, deal::toModelType);
    }

    @Test
    public void toModelType_invalidBuyerName_throwsIllegalValueException() {
        JsonAdaptedDeal deal = new JsonAdaptedDeal(
            VALID_PROPERTY_NAME, INVALID_BUYER_NAME, VALID_SELLER_NAME, VALID_PRICE, VALID_STATUS);
        String expectedMessage = INVALID_NAME_MESSAGE;
        assertThrows(IllegalValueException.class, expectedMessage, deal::toModelType);
    }

    @Test
    public void toModelType_nullBuyerName_throwsIllegalValueException() {
        JsonAdaptedDeal deal = new JsonAdaptedDeal(
            VALID_PROPERTY_NAME, null, VALID_SELLER_NAME, VALID_PRICE, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Buyer");
        assertThrows(IllegalValueException.class, expectedMessage, deal::toModelType);
    }

    @Test
    public void toModelType_invalidSellerName_throwsIllegalValueException() {
        JsonAdaptedDeal deal = new JsonAdaptedDeal(
            VALID_PROPERTY_NAME, VALID_BUYER_NAME, INVALID_SELLER_NAME, VALID_PRICE, VALID_STATUS);
        String expectedMessage = INVALID_NAME_MESSAGE;
        assertThrows(IllegalValueException.class, expectedMessage, deal::toModelType);
    }

    @Test
    public void toModelType_nullSellerName_throwsIllegalValueException() {
        JsonAdaptedDeal deal = new JsonAdaptedDeal(
            VALID_PROPERTY_NAME, VALID_BUYER_NAME, null, VALID_PRICE, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Seller");
        assertThrows(IllegalValueException.class, expectedMessage, deal::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        JsonAdaptedDeal deal = new JsonAdaptedDeal(
            VALID_PROPERTY_NAME, VALID_BUYER_NAME, VALID_SELLER_NAME, INVALID_PRICE, VALID_STATUS);
        String expectedMessage = INVALID_PRICE_MESSAGE;
        assertThrows(IllegalValueException.class, expectedMessage, deal::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        JsonAdaptedDeal deal = new JsonAdaptedDeal(
            VALID_PROPERTY_NAME, VALID_BUYER_NAME, VALID_SELLER_NAME, null, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Price");
        assertThrows(IllegalValueException.class, expectedMessage, deal::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedDeal deal = new JsonAdaptedDeal(
            VALID_PROPERTY_NAME, VALID_BUYER_NAME, VALID_SELLER_NAME, VALID_PRICE, INVALID_STATUS);
        String expectedMessage = INVALID_STATUS_MESSAGE;
        assertThrows(IllegalValueException.class, expectedMessage, deal::toModelType);
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        JsonAdaptedDeal deal = new JsonAdaptedDeal(
            VALID_PROPERTY_NAME, VALID_BUYER_NAME, VALID_SELLER_NAME, VALID_PRICE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Status");
        assertThrows(IllegalValueException.class, expectedMessage, deal::toModelType);
    }
}
