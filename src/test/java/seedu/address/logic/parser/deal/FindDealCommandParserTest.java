package seedu.address.logic.parser.deal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.deal.FindDealCommand;
import seedu.address.logic.parser.FindCommandParserTest;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.deal.Deal;
import seedu.address.model.deal.DealStatus;

public class FindDealCommandParserTest extends FindCommandParserTest<Deal> {

    private FindDealCommandParser parser = new FindDealCommandParser();

    @BeforeAll
    public static void setUp() {
        FindDealCommand.addCommandWord();
    }

    /**
     * Helper method to verify that the {@code FindDealCommand} instance created is correct.
     */
    private void assertFindCommandSuccess(String userInput) {
        try {
            FindDealCommand command = parser.parse(userInput);
            // Verify the command is correctly created
            assertNotNull(command);
            assertEquals("find_deal", FindDealCommand.COMMAND_WORD);
        } catch (ParseException pe) {
            fail("ParseException should not be thrown for valid input: " + pe.getMessage());
        }
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDealCommand.MESSAGE_USAGE));
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
        assertFindCommandSuccess(" " + PREFIX_PROPERTY_NAME + "Villa " + PREFIX_STATUS.getAndPrefix() + "PENDING");

        // Property name, buyer name, and seller name
        assertFindCommandSuccess(" " + PREFIX_PROPERTY_NAME + "Villa " + PREFIX_BUYER.getAndPrefix()
                + "John " + PREFIX_SELLER.getAndPrefix() + "Jane");
    }

    @Test
    public void parse_invalidStatusArg_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_STATUS + "INVALID",
                DealStatus.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_repeatedArgs_throwsParseException() {
        // Repeated property name
        assertParseFailure(parser, " " + PREFIX_PROPERTY_NAME + "Villa "
                + PREFIX_PROPERTY_NAME + "Condo",
                MESSAGE_DUPLICATE_FIELDS + PREFIX_PROPERTY_NAME);

        // Repeated buyer name
        assertParseFailure(parser, " " + PREFIX_BUYER + "John "
                + PREFIX_BUYER + "Alice",
                MESSAGE_DUPLICATE_FIELDS + PREFIX_BUYER);

        // Repeated seller name
        assertParseFailure(parser, " " + PREFIX_SELLER + "Jane "
                + PREFIX_SELLER + "Bob",
                MESSAGE_DUPLICATE_FIELDS + PREFIX_SELLER);

        // Repeated status
        assertParseFailure(parser, " " + PREFIX_STATUS + "PENDING "
                + PREFIX_STATUS + "CLOSED",
                MESSAGE_DUPLICATE_FIELDS + PREFIX_STATUS);
    }
}
