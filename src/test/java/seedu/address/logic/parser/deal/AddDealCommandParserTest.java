package seedu.address.logic.parser.deal;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.deal.AddDealCommand;
import seedu.address.model.commons.Price;
import seedu.address.model.deal.DealStatus;

public class AddDealCommandParserTest {
    // Define test constants
    private static final String VALID_PROPERTY_ID_DESC = " pid/1";
    private static final String INVALID_PROPERTY_ID_DESC = " pid/0"; // property ID must be positive
    private static final String VALID_BUYER_ID_DESC = " buyer/1";
    private static final String INVALID_BUYER_ID_DESC = " buyer/0"; // buyer ID must be positive
    private static final String VALID_SELLER_ID_DESC = " seller/2";
    private static final String INVALID_SELLER_ID_DESC = " seller/0"; // seller ID must be positive
    private static final String VALID_PRICE_DESC = " price/500000";
    private static final String INVALID_PRICE_DESC = " price/abc"; // price must be numeric
    private static final String VALID_STATUS_DESC = " status/pending";
    private static final String INVALID_STATUS_DESC = " status/invalid"; // status must be one of the predefined values
    private final AddDealCommandParser parser = new AddDealCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // All fields present
        Index propertyId = Index.fromOneBased(1);
        Index buyerId = Index.fromOneBased(1);
        Index sellerId = Index.fromOneBased(2);
        Price price = new Price(500000);
        DealStatus status = DealStatus.PENDING;
        AddDealCommand expectedCommand = new AddDealCommand(propertyId, buyerId, sellerId, price, status);
        assertParseSuccess(parser, VALID_PROPERTY_ID_DESC + VALID_BUYER_ID_DESC + VALID_SELLER_ID_DESC
                + VALID_PRICE_DESC + VALID_STATUS_DESC, expectedCommand);
        // Status is optional - default is PENDING
        assertParseSuccess(parser, VALID_PROPERTY_ID_DESC + VALID_BUYER_ID_DESC + VALID_SELLER_ID_DESC
                + VALID_PRICE_DESC, expectedCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDealCommand.MESSAGE_USAGE);
        // Missing property ID
        assertParseFailure(parser, VALID_BUYER_ID_DESC + VALID_SELLER_ID_DESC + VALID_PRICE_DESC,
                expectedMessage);
        // Missing buyer ID
        assertParseFailure(parser, VALID_PROPERTY_ID_DESC + VALID_SELLER_ID_DESC + VALID_PRICE_DESC,
                expectedMessage);
        // Missing seller ID
        assertParseFailure(parser, VALID_PROPERTY_ID_DESC + VALID_BUYER_ID_DESC + VALID_PRICE_DESC,
                expectedMessage);
        // Missing price
        assertParseFailure(parser, VALID_PROPERTY_ID_DESC + VALID_BUYER_ID_DESC + VALID_SELLER_ID_DESC,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid property ID
        assertParseFailure(parser, INVALID_PROPERTY_ID_DESC + VALID_BUYER_ID_DESC + VALID_SELLER_ID_DESC
                + VALID_PRICE_DESC, "Index is not a non-zero unsigned integer.");
        // Invalid buyer ID
        assertParseFailure(parser, VALID_PROPERTY_ID_DESC + INVALID_BUYER_ID_DESC + VALID_SELLER_ID_DESC
                + VALID_PRICE_DESC, "Index is not a non-zero unsigned integer.");
        // Invalid seller ID
        assertParseFailure(parser, VALID_PROPERTY_ID_DESC + VALID_BUYER_ID_DESC + INVALID_SELLER_ID_DESC
                + VALID_PRICE_DESC, "Index is not a non-zero unsigned integer.");
        // Invalid price
        assertParseFailure(parser, VALID_PROPERTY_ID_DESC + VALID_BUYER_ID_DESC + VALID_SELLER_ID_DESC
                + INVALID_PRICE_DESC, "Invalid price: Price must be a positive number and be under 9 quintillion.");
        // Invalid status
        assertParseFailure(parser, VALID_PROPERTY_ID_DESC + VALID_BUYER_ID_DESC + VALID_SELLER_ID_DESC
                + VALID_PRICE_DESC + INVALID_STATUS_DESC,
                "Invalid status: Must be one of 'PENDING', 'CLOSED', 'IN_NEGOTIATION'.");
    }
}
