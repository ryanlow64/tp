package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedDeal.INVALID_INDEX_MESSAGE;
import static seedu.address.storage.JsonAdaptedDeal.INVALID_PRICE_MESSAGE;
import static seedu.address.storage.JsonAdaptedDeal.INVALID_STATUS_MESSAGE;
import static seedu.address.storage.JsonAdaptedDeal.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.commons.Price;
import seedu.address.model.deal.Deal;
import seedu.address.model.deal.DealStatus;

public class JsonAdaptedDealTest {
    private static final String INVALID_PROPERTY_ID = "0";
    private static final String INVALID_BUYER_ID = "-1";
    private static final String INVALID_SELLER_ID = "abc";
    private static final String INVALID_PRICE = "-100";
    private static final String INVALID_STATUS = "UNKNOWN";

    private static final String VALID_PROPERTY_ID = "1";
    private static final String VALID_BUYER_ID = "2";
    private static final String VALID_SELLER_ID = "3";
    private static final String VALID_PRICE = "500000";
    private static final String VALID_STATUS = "PENDING";

    @Test
    public void toModelType_validDealDetails_returnsDeal() throws Exception {
        Deal deal = new Deal(
            Index.fromOneBased(Integer.parseInt(VALID_PROPERTY_ID)),
            Index.fromOneBased(Integer.parseInt(VALID_BUYER_ID)),
            Index.fromOneBased(Integer.parseInt(VALID_SELLER_ID)),
            new Price(Long.parseLong(VALID_PRICE)),
            DealStatus.valueOf(VALID_STATUS)
        );
        JsonAdaptedDeal jsonDeal = new JsonAdaptedDeal(deal);
        assertEquals(deal, jsonDeal.toModelType());
    }

    @Test
    public void toModelType_invalidPropertyId_throwsIllegalValueException() {
        JsonAdaptedDeal deal = new JsonAdaptedDeal(
            INVALID_PROPERTY_ID, VALID_BUYER_ID, VALID_SELLER_ID, VALID_PRICE, VALID_STATUS);
        String expectedMessage = INVALID_INDEX_MESSAGE;
        assertThrows(IllegalValueException.class, expectedMessage, deal::toModelType);
    }

    @Test
    public void toModelType_nullPropertyId_throwsIllegalValueException() {
        JsonAdaptedDeal deal = new JsonAdaptedDeal(
            null, VALID_BUYER_ID, VALID_SELLER_ID, VALID_PRICE, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Property ID");
        assertThrows(IllegalValueException.class, expectedMessage, deal::toModelType);
    }

    @Test
    public void toModelType_invalidBuyerId_throwsIllegalValueException() {
        JsonAdaptedDeal deal = new JsonAdaptedDeal(
            VALID_PROPERTY_ID, INVALID_BUYER_ID, VALID_SELLER_ID, VALID_PRICE, VALID_STATUS);
        String expectedMessage = INVALID_INDEX_MESSAGE;
        assertThrows(IllegalValueException.class, expectedMessage, deal::toModelType);
    }

    @Test
    public void toModelType_nullBuyerId_throwsIllegalValueException() {
        JsonAdaptedDeal deal = new JsonAdaptedDeal(
            VALID_PROPERTY_ID, null, VALID_SELLER_ID, VALID_PRICE, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Buyer ID");
        assertThrows(IllegalValueException.class, expectedMessage, deal::toModelType);
    }

    @Test
    public void toModelType_invalidSellerId_throwsIllegalValueException() {
        JsonAdaptedDeal deal = new JsonAdaptedDeal(
            VALID_PROPERTY_ID, VALID_BUYER_ID, INVALID_SELLER_ID, VALID_PRICE, VALID_STATUS);
        String expectedMessage = INVALID_INDEX_MESSAGE;
        assertThrows(IllegalValueException.class, expectedMessage, deal::toModelType);
    }

    @Test
    public void toModelType_nullSellerId_throwsIllegalValueException() {
        JsonAdaptedDeal deal = new JsonAdaptedDeal(
            VALID_PROPERTY_ID, VALID_BUYER_ID, null, VALID_PRICE, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Seller ID");
        assertThrows(IllegalValueException.class, expectedMessage, deal::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        JsonAdaptedDeal deal = new JsonAdaptedDeal(
            VALID_PROPERTY_ID, VALID_BUYER_ID, VALID_SELLER_ID, INVALID_PRICE, VALID_STATUS);
        String expectedMessage = INVALID_PRICE_MESSAGE;
        assertThrows(IllegalValueException.class, expectedMessage, deal::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        JsonAdaptedDeal deal = new JsonAdaptedDeal(
            VALID_PROPERTY_ID, VALID_BUYER_ID, VALID_SELLER_ID, null, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Price");
        assertThrows(IllegalValueException.class, expectedMessage, deal::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedDeal deal = new JsonAdaptedDeal(
            VALID_PROPERTY_ID, VALID_BUYER_ID, VALID_SELLER_ID, VALID_PRICE, INVALID_STATUS);
        String expectedMessage = INVALID_STATUS_MESSAGE;
        assertThrows(IllegalValueException.class, expectedMessage, deal::toModelType);
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        JsonAdaptedDeal deal = new JsonAdaptedDeal(
            VALID_PROPERTY_ID, VALID_BUYER_ID, VALID_SELLER_ID, VALID_PRICE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Status");
        assertThrows(IllegalValueException.class, expectedMessage, deal::toModelType);
    }
}
