package seedu.address.logic.parser.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ABOUT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_WITH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.event.FindEventCommand;
import seedu.address.logic.parser.FindCommandParserTest;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.ClientName;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventType;

public class FindEventCommandParserTest extends FindCommandParserTest<Event> {

    private FindEventCommandParser parser = new FindEventCommandParser();

    /**
     * Helper method to verify that the {@code FindEventCommand} instance created is correct.
     */
    private void assertFindCommandSuccess(String userInput) {
        try {
            FindEventCommand command = parser.parse(userInput);
            assertTrue(command instanceof FindEventCommand);
            assertEquals("find_event", FindEventCommand.COMMAND_WORD);
        } catch (ParseException pe) {
            fail("ParseException should not be thrown for valid input: " + pe.getMessage());
        }
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindEventCommand() {
        assertFindCommandSuccess(" " + PREFIX_EVENT_ABOUT + "Villa");
        assertFindCommandSuccess(" " + PREFIX_EVENT_WITH + "John");
        assertFindCommandSuccess(" " + PREFIX_EVENT_TYPE + "meeting");

        assertFindCommandSuccess(" " + PREFIX_EVENT_ABOUT + "Villa Condo");
        assertFindCommandSuccess(" " + PREFIX_EVENT_WITH + "John Doe");
        assertFindCommandSuccess(" " + PREFIX_EVENT_ABOUT
                + "aquickbrownfoxjumpsoverthelazydogaquickbrownfoxjumpsoverthelazydog"
                + "    1234567890    aquickbrownfoxjumpsoverthedog");
    }

    @Test
    public void parse_multipleArgs_returnsFindEventCommand() {
        assertFindCommandSuccess(" " + PREFIX_EVENT_ABOUT + "Villa "
                + PREFIX_EVENT_WITH + "John "
                + PREFIX_EVENT_TYPE + "meeting");
    }

    @Test
    public void parse_repeatedArgs_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_EVENT_ABOUT + "Alice " + PREFIX_EVENT_ABOUT + "Bob",
                MESSAGE_DUPLICATE_FIELDS + PREFIX_EVENT_ABOUT);
        assertParseFailure(parser, " " + PREFIX_EVENT_WITH + "Alice " + PREFIX_EVENT_WITH + "Bob",
                MESSAGE_DUPLICATE_FIELDS + PREFIX_EVENT_WITH);
        assertParseFailure(parser, " " + PREFIX_EVENT_TYPE + "meeting " + PREFIX_EVENT_TYPE + "others",
                MESSAGE_DUPLICATE_FIELDS + PREFIX_EVENT_TYPE);
    }

    @Test
    public void parse_invalidEventTypeArg_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_EVENT_TYPE + "Arnold Schwarzenegger",
                EventType.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_nonAlphanumericArg_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_EVENT_WITH + "Arnold Schw@rzenegger",
                ClientName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_additionalPreamble_failure() {
        assertParseFailure(parser,
                "some garbage " + PREFIX_EVENT_WITH + "Arnold Schwarzenegger",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
    }
}
