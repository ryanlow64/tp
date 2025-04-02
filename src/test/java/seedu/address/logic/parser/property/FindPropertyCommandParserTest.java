package seedu.address.logic.parser.property;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEYWORDS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OWNER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE_ABOVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE_BELOW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SIZE_ABOVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SIZE_BELOW;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.property.FindPropertyCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.commons.Price;
import seedu.address.model.property.Size;
import seedu.address.model.property.predicates.PropertyAddressContainsPredicate;
import seedu.address.model.property.predicates.PropertyNameContainsKeywordsPredicate;
import seedu.address.model.property.predicates.PropertyOwnerContainsKeywordsPredicate;
import seedu.address.model.property.predicates.PropertyPriceAbovePredicate;
import seedu.address.model.property.predicates.PropertyPriceBelowPredicate;
import seedu.address.model.property.predicates.PropertySizeAbovePredicate;
import seedu.address.model.property.predicates.PropertySizeBelowPredicate;

public class FindPropertyCommandParserTest {

    private FindPropertyCommandParser parser = new FindPropertyCommandParser();

    @BeforeAll
    public static void setUp() {
        FindPropertyCommand.addCommandWord();
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPropertyCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindPropertyCommand() {
        // no leading and trailing whitespaces
        FindPropertyCommand expectedCommand =
                new FindPropertyCommand(new PropertyNameContainsKeywordsPredicate(Arrays.asList("Maple", "Orchid")));
        assertParseSuccess(parser, String.format(" %s Maple Orchid", PREFIX_KEYWORDS), expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, String.format(" %s \n Maple \n \t Orchid  \t", PREFIX_KEYWORDS), expectedCommand);
    }

    @Test
    public void parse_validArgs_addressReturnsFindPropertyCommand() {
        String address = "123 Orchard Rd";
        FindPropertyCommand expectedCommand =
                new FindPropertyCommand(new PropertyAddressContainsPredicate(address));
        assertParseSuccess(parser, String.format(" %s %s", PREFIX_ADDRESS, address), expectedCommand);
    }

    @Test
    public void parse_validArgs_ownerReturnsFindPropertyCommand() {
        String owner = "John Doe";
        FindPropertyCommand expectedCommand =
                new FindPropertyCommand(new PropertyOwnerContainsKeywordsPredicate(Arrays.asList("John", "Doe")));
        assertParseSuccess(parser, String.format(" %s %s", PREFIX_OWNER, owner), expectedCommand);
    }

    @Test
    public void parse_validArgs_priceBelowReturnsFindPropertyCommand() {
        String priceBelowString = "1000";
        Price priceBelow = new Price(1000L);
        FindPropertyCommand expectedCommand =
                new FindPropertyCommand(new PropertyPriceBelowPredicate(priceBelow));
        assertParseSuccess(parser, String.format(" %s %s", PREFIX_PRICE_BELOW, priceBelowString), expectedCommand);
    }

    @Test
    public void parse_validArgs_priceAboveReturnsFindPropertyCommand() {
        String priceAboveString = "5000";
        Price priceAbove = new Price(5000L);
        FindPropertyCommand expectedCommand =
                new FindPropertyCommand(new PropertyPriceAbovePredicate(priceAbove));
        assertParseSuccess(parser, String.format(" %s %s", PREFIX_PRICE_ABOVE, priceAboveString), expectedCommand);
    }

    @Test
    public void parse_validArgs_sizeBelowReturnsFindPropertyCommand() throws Exception {
        String sizeBelowString = "200";
        Size sizeBelow = ParserUtil.parseSize(sizeBelowString).orElseThrow();
        FindPropertyCommand expectedCommand =
                new FindPropertyCommand(new PropertySizeBelowPredicate(sizeBelow));
        assertParseSuccess(parser, String.format(" %s %s", PREFIX_SIZE_BELOW, sizeBelowString), expectedCommand);
    }

    @Test
    public void parse_validArgs_sizeAboveReturnsFindPropertyCommand() throws Exception {
        String sizeAboveString = "300";
        Size sizeAbove = ParserUtil.parseSize(sizeAboveString).orElseThrow();
        FindPropertyCommand expectedCommand =
                new FindPropertyCommand(new PropertySizeAbovePredicate(sizeAbove));
        assertParseSuccess(parser, String.format(" %s %s", PREFIX_SIZE_ABOVE, sizeAboveString), expectedCommand);
    }

    // Tests for empty values throwing ParseException

    @Test
    public void parse_emptyOwner_throwsParseException() {
        assertParseFailure(parser, String.format(" %s ", PREFIX_OWNER), "No owner name provided");
    }

    @Test
    public void parse_emptyAddress_throwsParseException() {
        assertParseFailure(parser, String.format(" %s ", PREFIX_ADDRESS), "No address provided");
    }

    @Test
    public void parse_emptyPriceBelow_throwsParseException() {
        assertParseFailure(parser, String.format(" %s ", PREFIX_PRICE_BELOW), Price.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_emptyPriceAbove_throwsParseException() {
        assertParseFailure(parser, String.format(" %s ", PREFIX_PRICE_ABOVE), Price.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_emptySizeBelow_throwsParseException() {
        assertParseFailure(parser, String.format(" %s ", PREFIX_SIZE_BELOW), "No size below provided");
    }

    @Test
    public void parse_emptySizeAbove_throwsParseException() {
        assertParseFailure(parser, String.format(" %s ", PREFIX_SIZE_ABOVE), "No size above provided");
    }
}
