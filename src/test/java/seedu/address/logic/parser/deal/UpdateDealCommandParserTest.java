package seedu.address.logic.parser.deal;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.deal.UpdateDealCommand;
import seedu.address.model.commons.Price;
import seedu.address.model.deal.DealStatus;
import seedu.address.testutil.UpdateDealDescriptorBuilder;

public class UpdateDealCommandParserTest {
    private static final String VALID_PROPERTY_ID = "1";
    private static final String INVALID_PROPERTY_ID = "0"; // Index cannot be 0
    private static final String VALID_PRICE = "200"; // Valid price in millions
    private static final String INVALID_PRICE = "abc";
    private static final String VALID_STATUS = "CLOSED";
    private static final String INVALID_STATUS = "INVALID_STATUS";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateDealCommand.MESSAGE_USAGE);

    private static final Index INDEX_FIRST = Index.fromOneBased(1);
    private static final Index INDEX_SECOND = Index.fromOneBased(2);

    private UpdateDealCommandParser parser = new UpdateDealCommandParser();

    @Test
    public void parse_missingDealId_failure() {
        // No deal ID specified
        assertParseFailure(parser,
                " " + PREFIX_PROPERTY_ID + VALID_PROPERTY_ID,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidDealId_failure() {
        // non-numeric deal ID
        assertParseFailure(parser,
            " abc",
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_emptyFields_failure() {
        // Just a deal ID but no fields to update
        assertParseFailure(parser,
                " " + "1",
                UpdateDealCommand.MESSAGE_NO_CHANGES);
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid price
        assertParseFailure(parser,
                " 1 " + PREFIX_PRICE + INVALID_PRICE,
                Price.MESSAGE_CONSTRAINTS);

        // Invalid status
        assertParseFailure(parser,
                " 1 " + PREFIX_STATUS + INVALID_STATUS,
                "Invalid status: Must be one of 'OPEN', 'PENDING', 'CLOSED' (case insensitive).");

        // Invalid property ID (0)
        assertParseFailure(parser,
                " 1 " + PREFIX_PROPERTY_ID + INVALID_PROPERTY_ID,
                "Index is not a positive integer.");
    }

    @Test
    public void parse_validStatusOnly_success() {
        Index dealIndex = INDEX_FIRST;
        String userInput = " 1 " + PREFIX_STATUS + VALID_STATUS;
        UpdateDealCommand expectedCommand = new UpdateDealCommand(dealIndex,
                new UpdateDealDescriptorBuilder().withStatus(DealStatus.CLOSED).build());
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validPropertyIdOnly_success() {
        Index dealIndex = INDEX_FIRST;
        Index propertyIndex = INDEX_FIRST;
        String userInput = " 1 " + PREFIX_PROPERTY_ID + VALID_PROPERTY_ID;
        UpdateDealCommand expectedCommand = new UpdateDealCommand(dealIndex,
                new UpdateDealDescriptorBuilder().withPropertyId(propertyIndex).build());
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validPriceOnly_success() {
        Index dealIndex = INDEX_FIRST;
        String userInput = " 1 " + PREFIX_PRICE + VALID_PRICE;
        UpdateDealCommand expectedCommand = new UpdateDealCommand(dealIndex,
                new UpdateDealDescriptorBuilder().withPrice(Long.parseLong(VALID_PRICE)).build());
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validBuyerSellerIdOnly_success() {
        Index dealIndex = INDEX_FIRST;
        Index buyerIndex = INDEX_FIRST;
        Index sellerIndex = INDEX_SECOND;
        String userInput = " 1 " + PREFIX_BUYER + "1 " + PREFIX_SELLER + "2";
        UpdateDealCommand expectedCommand = new UpdateDealCommand(dealIndex,
                new UpdateDealDescriptorBuilder().withBuyer(buyerIndex).withSeller(sellerIndex).build());
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validMultipleFields_success() {
        Index dealIndex = INDEX_FIRST;
        Index buyerIndex = INDEX_FIRST;
        Index propertyIndex = INDEX_FIRST;
        String userInput = " 1 " + PREFIX_PROPERTY_ID + VALID_PROPERTY_ID + " "
                + PREFIX_BUYER + "1 " + PREFIX_PRICE + VALID_PRICE + " " + PREFIX_STATUS + VALID_STATUS;
        UpdateDealCommand expectedCommand = new UpdateDealCommand(dealIndex,
                new UpdateDealDescriptorBuilder()
                        .withPropertyId(propertyIndex)
                        .withBuyer(buyerIndex)
                        .withPrice(Long.parseLong(VALID_PRICE))
                        .withStatus(DealStatus.CLOSED)
                        .build());
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

