package seedu.address.logic.parser.deal;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.deal.AddDealCommand;
import seedu.address.model.client.ClientName;
import seedu.address.model.commons.Price;
import seedu.address.model.deal.DealStatus;
import seedu.address.model.property.PropertyName;

public class AddDealCommandParserTest {
    // Define test constants
    private static final String VALID_PROPERTY_NAME_DESC = " " + PREFIX_PROPERTY_NAME + "Sunset Villa";
    private static final String INVALID_PROPERTY_NAME_DESC = " " + PREFIX_PROPERTY_NAME + ""; // empty property name
    private static final String VALID_BUYER_NAME_DESC = " " + PREFIX_BUYER + "John Doe";
    private static final String INVALID_BUYER_NAME_DESC = " " + PREFIX_BUYER + ""; // empty buyer name
    private static final String VALID_SELLER_NAME_DESC = " " + PREFIX_SELLER + "Jane Smith";
    private static final String INVALID_SELLER_NAME_DESC = " " + PREFIX_SELLER + ""; // empty seller name
    private static final String VALID_PRICE_DESC = " " + PREFIX_PRICE + "25";
    private static final String INVALID_PRICE_DESC = " " + PREFIX_PRICE + "abc"; // price must be numeric
    private static final String VALID_STATUS_DESC = " " + PREFIX_STATUS + "PENDING";
    private static final String INVALID_STATUS_DESC = " " + PREFIX_STATUS + "invalid"; // status is a predefined value

    private final AddDealCommandParser parser = new AddDealCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // All fields present
        PropertyName propertyName = new PropertyName("Sunset Villa");
        ClientName buyer = new ClientName("John Doe");
        ClientName seller = new ClientName("Jane Smith");
        Price price = new Price(25L);
        DealStatus status = DealStatus.PENDING;
        AddDealCommand expectedCommand = new AddDealCommand(propertyName, buyer, seller, price, status);

        // Test with all fields including status
        assertParseSuccess(parser, VALID_PROPERTY_NAME_DESC + VALID_BUYER_NAME_DESC + VALID_SELLER_NAME_DESC
                + VALID_PRICE_DESC + VALID_STATUS_DESC, expectedCommand);

        // Status is optional - default is PENDING
        assertParseSuccess(parser, VALID_PROPERTY_NAME_DESC + VALID_BUYER_NAME_DESC + VALID_SELLER_NAME_DESC
                + VALID_PRICE_DESC, expectedCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDealCommand.MESSAGE_USAGE);

        // Missing property name
        assertParseFailure(parser, VALID_BUYER_NAME_DESC + VALID_SELLER_NAME_DESC + VALID_PRICE_DESC,
                expectedMessage);

        // Missing buyer name
        assertParseFailure(parser, VALID_PROPERTY_NAME_DESC + VALID_SELLER_NAME_DESC + VALID_PRICE_DESC,
                expectedMessage);

        // Missing seller name
        assertParseFailure(parser, VALID_PROPERTY_NAME_DESC + VALID_BUYER_NAME_DESC + VALID_PRICE_DESC,
                expectedMessage);

        // Missing price
        assertParseFailure(parser, VALID_PROPERTY_NAME_DESC + VALID_BUYER_NAME_DESC + VALID_SELLER_NAME_DESC,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid property name
        assertParseFailure(parser, INVALID_PROPERTY_NAME_DESC + VALID_BUYER_NAME_DESC + VALID_SELLER_NAME_DESC
                + VALID_PRICE_DESC, PropertyName.MESSAGE_CONSTRAINTS);

        // Invalid buyer name
        assertParseFailure(parser, VALID_PROPERTY_NAME_DESC + INVALID_BUYER_NAME_DESC + VALID_SELLER_NAME_DESC
                + VALID_PRICE_DESC, ClientName.MESSAGE_CONSTRAINTS);

        // Invalid seller name
        assertParseFailure(parser, VALID_PROPERTY_NAME_DESC + VALID_BUYER_NAME_DESC + INVALID_SELLER_NAME_DESC
                + VALID_PRICE_DESC, ClientName.MESSAGE_CONSTRAINTS);

        // Invalid status
        assertParseFailure(parser, VALID_PROPERTY_NAME_DESC + VALID_BUYER_NAME_DESC + VALID_SELLER_NAME_DESC
                + VALID_PRICE_DESC + INVALID_STATUS_DESC,
                "Invalid status: Must be one of 'PENDING', 'CLOSED', 'IN_NEGOTIATION'.");
    }
}
