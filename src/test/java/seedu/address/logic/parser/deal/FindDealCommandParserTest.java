package seedu.address.logic.parser.deal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.deal.FindDealCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class FindDealCommandParserTest {

    private FindDealCommandParser parser = new FindDealCommandParser();

    /**
     * Helper method to verify that a FindDealCommand is correctly created from parsing user input.
     */
    private void assertFindCommandSuccess(String userInput) {
        try {
            FindDealCommand command = parser.parse(userInput);
            // Verify the command is correctly created
            assertTrue(command instanceof FindDealCommand);
            assertEquals("find_deal", FindDealCommand.COMMAND_WORD);
        } catch (ParseException pe) {
            fail("ParseException should not be thrown for valid input: " + pe.getMessage());
        }
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                "Invalid command format! \n"
                + "find_deal: Finds all deals that match the specified criteria "
                + "and displays them as a list with index numbers.\n"
                + "Parameters: "
                + "[prop/PROPERTY_NAME] "
                + "[buyer/BUYER_NAME] "
                + "[seller/SELLER_NAME] "
                + "[status/STATUS]\n"
                + "Example: find_deal prop/Villa status/PENDING");
    }

    @Test
    public void parse_validArgs_returnsFindDealCommand() {
        // Property name only
        assertFindCommandSuccess(" " + PREFIX_PROPERTY_NAME + "Villa");

        // Buyer name only
        assertFindCommandSuccess(" " + PREFIX_BUYER + "John");

        // Seller name only
        assertFindCommandSuccess(" " + PREFIX_SELLER + "Jane");

        // Status only
        assertFindCommandSuccess(" " + PREFIX_STATUS + "PENDING");

        // Multiple keywords in property name
        assertFindCommandSuccess(" " + PREFIX_PROPERTY_NAME + "Villa Condo");

        // Multiple keywords in buyer name
        assertFindCommandSuccess(" " + PREFIX_BUYER + "John Doe");

        // Multiple keywords in seller name
        assertFindCommandSuccess(" " + PREFIX_SELLER + "Jane Smith");
    }

    @Test
    public void parse_multipleArgs_returnsFindDealCommand() {
        // Property name and status
        assertFindCommandSuccess(" " + PREFIX_PROPERTY_NAME + "Villa " + PREFIX_STATUS + "PENDING");

        // Property name, buyer name, and seller name
        assertFindCommandSuccess(" " + PREFIX_PROPERTY_NAME + "Villa " + PREFIX_BUYER
                + "John " + PREFIX_SELLER + "Jane");
    }

    @Test
    public void parse_invalidStatusArg_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_STATUS + "INVALID",
                "Invalid status: Must be one of 'OPEN', 'PENDING', 'CLOSED' (case insensitive).");
    }

    @Test
    public void parse_repeatedArgs_throwsParseException() {
        // Repeated property name
        assertParseFailure(parser, " " + PREFIX_PROPERTY_NAME + "Villa "
                + PREFIX_PROPERTY_NAME + "Condo",
                "Multiple values specified for the following single-valued field(s): prop/");

        // Repeated buyer name
        assertParseFailure(parser, " " + PREFIX_BUYER + "John "
                + PREFIX_BUYER + "Alice",
                "Multiple values specified for the following single-valued field(s): buyer/");

        // Repeated seller name
        assertParseFailure(parser, " " + PREFIX_SELLER + "Jane "
                + PREFIX_SELLER + "Bob",
                "Multiple values specified for the following single-valued field(s): seller/");

        // Repeated status
        assertParseFailure(parser, " " + PREFIX_STATUS + "PENDING "
                + PREFIX_STATUS + "CLOSED",
                "Multiple values specified for the following single-valued field(s): status/");
    }
}
