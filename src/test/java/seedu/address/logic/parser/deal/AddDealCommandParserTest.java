package seedu.address.logic.parser.deal;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.deal.AddDealCommand;
import seedu.address.model.commons.Price;
import seedu.address.model.deal.DealStatus;

public class AddDealCommandParserTest {
    // Define test constants
    private static final String VALID_PROPERTY_ID_DESC = " " + PREFIX_PROPERTY_ID + "1";
    private static final String INVALID_PROPERTY_ID_DESC = " " + PREFIX_PROPERTY_ID + "0"; // index cannot be 0
    private static final String VALID_BUYER_ID_DESC = " " + PREFIX_BUYER + "1";
    private static final String INVALID_BUYER_ID_DESC = " " + PREFIX_BUYER + "0"; // index cannot be 0
    private static final String VALID_PRICE_DESC = " " + PREFIX_PRICE + "250"; // 3 digits required
    private static final String INVALID_PRICE_DESC = " " + PREFIX_PRICE + "abc"; // price must be numeric
    private static final String VALID_STATUS_DESC = " " + PREFIX_STATUS + "PENDING";
    private static final String INVALID_STATUS_DESC = " " + PREFIX_STATUS + "invalid"; // status is a predefined value

    private final AddDealCommandParser parser = new AddDealCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // All fields present
        Index propertyId = Index.fromOneBased(1);
        Index buyerId = Index.fromOneBased(1);
        Price price = new Price(250L);
        DealStatus status = DealStatus.PENDING;
        AddDealCommand expectedCommand = new AddDealCommand(propertyId, buyerId, price, status);

        // Test with all fields including status
        assertParseSuccess(parser, VALID_PROPERTY_ID_DESC + VALID_BUYER_ID_DESC
                + VALID_PRICE_DESC + VALID_STATUS_DESC, expectedCommand);

        // Status is optional - default is PENDING
        assertParseSuccess(parser, VALID_PROPERTY_ID_DESC + VALID_BUYER_ID_DESC
                + VALID_PRICE_DESC, expectedCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDealCommand.MESSAGE_USAGE);

        // Missing property ID
        assertParseFailure(parser, VALID_BUYER_ID_DESC + VALID_PRICE_DESC,
                expectedMessage);

        // Missing buyer ID
        assertParseFailure(parser, VALID_PROPERTY_ID_DESC + VALID_PRICE_DESC,
                expectedMessage);

        // Missing price
        assertParseFailure(parser, VALID_PROPERTY_ID_DESC + VALID_BUYER_ID_DESC,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid property ID
        assertParseFailure(parser, INVALID_PROPERTY_ID_DESC + VALID_BUYER_ID_DESC
                + VALID_PRICE_DESC, "Invalid property ID: Index is not a non-zero unsigned integer.");

        // Invalid buyer ID
        assertParseFailure(parser, VALID_PROPERTY_ID_DESC + INVALID_BUYER_ID_DESC
                + VALID_PRICE_DESC, "Invalid buyer ID: Index is not a non-zero unsigned integer.");

        // Invalid price
        assertParseFailure(parser, VALID_PROPERTY_ID_DESC + VALID_BUYER_ID_DESC
                + INVALID_PRICE_DESC, Price.MESSAGE_CONSTRAINTS);

        // Invalid status
        assertParseFailure(parser, VALID_PROPERTY_ID_DESC + VALID_BUYER_ID_DESC
                + VALID_PRICE_DESC + INVALID_STATUS_DESC,
                "Invalid status: Must be one of 'PENDING', 'CLOSED', 'IN_NEGOTIATION'.");
    }
}
