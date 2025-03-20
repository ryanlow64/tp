package seedu.address.logic.parser.deal;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEAL_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.deal.UpdateDealCommand;
import seedu.address.model.commons.Price;
import seedu.address.model.deal.DealStatus;
import seedu.address.model.property.PropertyName;

public class UpdateDealCommandParserTest {
    private static final String VALID_PROPERTY_NAME = "Maple Villa";
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
                " " + PREFIX_PROPERTY_NAME + VALID_PROPERTY_NAME,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidDealId_failure() {
        // non-numeric deal ID
        assertParseFailure(parser,
                " " + PREFIX_DEAL_ID + "abc",
                String.format("Invalid deal ID: %s", "Index is not a non-zero unsigned integer."));
    }

    @Test
    public void parse_emptyFields_failure() {
        // Just a deal ID but no fields to update
        assertParseFailure(parser,
                " " + PREFIX_DEAL_ID + "1",
                UpdateDealCommand.MESSAGE_NO_CHANGES);
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid price
        assertParseFailure(parser,
                " " + PREFIX_DEAL_ID + "1 " + PREFIX_PRICE + INVALID_PRICE,
                Price.MESSAGE_CONSTRAINTS);

        // Invalid status
        assertParseFailure(parser,
                " " + PREFIX_DEAL_ID + "1 " + PREFIX_STATUS + INVALID_STATUS,
                "Invalid status: Must be one of 'PENDING', 'CLOSED', 'IN_NEGOTIATION'.");
    }

    @Test
    public void parse_validStatusOnly_success() {
        // Only status is specified
        Index dealIndex = INDEX_FIRST;
        String userInput = " " + PREFIX_DEAL_ID + "1 " + PREFIX_STATUS + VALID_STATUS;

        UpdateDealCommand expectedCommand = new UpdateDealCommand(dealIndex,
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.of(DealStatus.CLOSED));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validPropertyNameOnly_success() {
        // Only property name is specified
        Index dealIndex = INDEX_FIRST;
        String userInput = " " + PREFIX_DEAL_ID + "1 " + PREFIX_PROPERTY_NAME + VALID_PROPERTY_NAME;
        UpdateDealCommand expectedCommand = new UpdateDealCommand(dealIndex,
                Optional.of(new PropertyName(VALID_PROPERTY_NAME)),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validPriceOnly_success() {
        // Only price is specified
        Index dealIndex = INDEX_FIRST;
        String userInput = " " + PREFIX_DEAL_ID + "1 " + PREFIX_PRICE + VALID_PRICE;

        UpdateDealCommand expectedCommand = new UpdateDealCommand(dealIndex,
                Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.of(new Price(Long.parseLong(VALID_PRICE))), Optional.empty());

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validBuyerSellerIdOnly_success() {
        // Only buyer and seller IDs are specified
        Index dealIndex = INDEX_FIRST;
        Index buyerIndex = INDEX_FIRST;
        Index sellerIndex = INDEX_SECOND;

        String userInput = " " + PREFIX_DEAL_ID + "1 " + PREFIX_BUYER + "1 " + PREFIX_SELLER + "2";

        UpdateDealCommand expectedCommand = new UpdateDealCommand(dealIndex,
                Optional.empty(), Optional.of(buyerIndex), Optional.of(sellerIndex),
                Optional.empty(), Optional.empty());

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validMultipleFields_success() {
        // Multiple fields are specified
        Index dealIndex = INDEX_FIRST;
        Index buyerIndex = INDEX_FIRST;

        String userInput = " " + PREFIX_DEAL_ID + "1 " + PREFIX_PROPERTY_NAME + VALID_PROPERTY_NAME + " "
                + PREFIX_BUYER + "1 " + PREFIX_PRICE + VALID_PRICE + " " + PREFIX_STATUS + VALID_STATUS;

        UpdateDealCommand expectedCommand = new UpdateDealCommand(dealIndex,
                Optional.of(new PropertyName(VALID_PROPERTY_NAME)),
                Optional.of(buyerIndex), Optional.empty(),
                Optional.of(new Price(Long.parseLong(VALID_PRICE))),
                Optional.of(DealStatus.CLOSED));

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
