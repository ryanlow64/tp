package seedu.address.logic.parser.event;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.event.AddEventCommand;
import seedu.address.logic.parser.AddCommandParserTest;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventType;
import seedu.address.model.event.Note;

class AddEventCommandParserTest extends AddCommandParserTest<Event> {
    private static final String VALID_EVENT_START = "30-04-2025 1700";
    private static final String VALID_EVENT_TYPE = "meeting";
    private static final String VALID_NOTE = "Client loves Arnold Schwarzenegger.";

    private static final String VALID_EVENT_TYPE_DESC = " " + PREFIX_EVENT_TYPE + VALID_EVENT_TYPE;
    private static final String VALID_PROPERTY_ID_DESC = " " + PREFIX_PROPERTY_ID + "42";
    private static final String VALID_CLIENT_ID_DESC = " " + PREFIX_CLIENT_ID + "42";
    private static final String VALID_EVENT_START_DESC = " " + PREFIX_EVENT_START + VALID_EVENT_START;
    private static final String VALID_EVENT_NOTE_DESC = " " + PREFIX_EVENT_NOTE + VALID_NOTE;

    private static final String INVALID_EVENT_TYPE = "sleep";
    private static final String INVALID_ID = "NaN";
    private static final String INVALID_EVENT_START = "arnold schwarzenegger";
    private static final String INVALID_NOTE = "    ";

    private static final String INVALID_EVENT_TYPE_DESC = " " + PREFIX_EVENT_TYPE + INVALID_EVENT_TYPE;
    private static final String INVALID_PROPERTY_ID_DESC = " " + PREFIX_PROPERTY_ID + INVALID_ID;
    private static final String INVALID_CLIENT_ID_DESC = " " + PREFIX_CLIENT_ID + INVALID_ID;
    private static final String INVALID_EVENT_START_DESC = " " + PREFIX_EVENT_START + INVALID_EVENT_START;
    private static final String INVALID_EVENT_NOTE_DESC = " " + PREFIX_EVENT_NOTE + INVALID_NOTE;

    private final AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        AddEventCommand expectedCommand = new AddEventCommand(
                LocalDateTime.of(2025, 4, 30, 17, 0),
                EventType.MEETING,
                Index.fromOneBased(42),
                Index.fromOneBased(42),
                new Note(VALID_NOTE)
        );

        assertParseSuccess(parser, " " + VALID_EVENT_TYPE_DESC + VALID_PROPERTY_ID_DESC + VALID_CLIENT_ID_DESC
                + VALID_EVENT_START_DESC + VALID_EVENT_NOTE_DESC, expectedCommand);
    }

    @Test
    public void parse_repeatedFields_failure() {
        // multiple event types
        assertParseFailure(parser,
                VALID_EVENT_TYPE_DESC + VALID_EVENT_TYPE_DESC + VALID_PROPERTY_ID_DESC
                        + VALID_CLIENT_ID_DESC + VALID_EVENT_START_DESC + VALID_EVENT_NOTE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_TYPE));

        // multiple property IDs
        assertParseFailure(parser,
                VALID_EVENT_TYPE_DESC + VALID_PROPERTY_ID_DESC + VALID_PROPERTY_ID_DESC
                        + VALID_CLIENT_ID_DESC + VALID_EVENT_START_DESC + VALID_EVENT_NOTE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PROPERTY_ID));

        // multiple client IDs
        assertParseFailure(parser,
                VALID_EVENT_TYPE_DESC + VALID_PROPERTY_ID_DESC + VALID_CLIENT_ID_DESC
                        + VALID_CLIENT_ID_DESC + VALID_EVENT_START_DESC + VALID_EVENT_NOTE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CLIENT_ID));

        // multiple datetimes
        assertParseFailure(parser,
                VALID_EVENT_TYPE_DESC + VALID_PROPERTY_ID_DESC + VALID_CLIENT_ID_DESC
                        + VALID_EVENT_START_DESC + VALID_EVENT_START_DESC + VALID_EVENT_NOTE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_START));

        // multiple notes
        assertParseFailure(parser,
                VALID_EVENT_TYPE_DESC + VALID_PROPERTY_ID_DESC + VALID_CLIENT_ID_DESC
                        + VALID_EVENT_START_DESC + VALID_EVENT_NOTE_DESC + VALID_EVENT_NOTE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_NOTE));
    }

    @Test
    public void parse_missingCompulsoryFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // missing event type
        assertParseFailure(parser,
                VALID_PROPERTY_ID_DESC + VALID_CLIENT_ID_DESC + VALID_EVENT_START_DESC + VALID_EVENT_NOTE_DESC,
                expectedMessage);

        // missing property ID
        assertParseFailure(parser,
                VALID_EVENT_TYPE_DESC + VALID_CLIENT_ID_DESC + VALID_EVENT_START_DESC + VALID_EVENT_NOTE_DESC,
                expectedMessage);

        // missing client ID
        assertParseFailure(parser,
                VALID_EVENT_TYPE_DESC + VALID_PROPERTY_ID_DESC + VALID_EVENT_START_DESC + VALID_EVENT_NOTE_DESC,
                expectedMessage);

        // missing datetime
        assertParseFailure(parser,
                VALID_EVENT_TYPE_DESC + VALID_PROPERTY_ID_DESC + VALID_CLIENT_ID_DESC + VALID_EVENT_NOTE_DESC,
                expectedMessage);

        // missing note
        assertParseFailure(parser,
                VALID_EVENT_TYPE_DESC + VALID_PROPERTY_ID_DESC + VALID_CLIENT_ID_DESC + VALID_EVENT_START_DESC,
                expectedMessage);

        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidValues_failure() {
        // invalid event type
        assertParseFailure(parser,
                INVALID_EVENT_TYPE_DESC + VALID_PROPERTY_ID_DESC + VALID_CLIENT_ID_DESC
                        + VALID_EVENT_START_DESC + VALID_EVENT_NOTE_DESC,
                EventType.MESSAGE_CONSTRAINTS);

        // invalid datetime
        assertParseFailure(parser,
                VALID_EVENT_TYPE_DESC + VALID_PROPERTY_ID_DESC + VALID_CLIENT_ID_DESC
                        + INVALID_EVENT_START_DESC + VALID_EVENT_NOTE_DESC,
                String.format(ParserUtil.MESSAGE_INVALID_DATETIME, INVALID_EVENT_START));

        // invalid property index
        assertParseFailure(parser,
                VALID_EVENT_TYPE_DESC + INVALID_PROPERTY_ID_DESC + VALID_CLIENT_ID_DESC
                        + VALID_EVENT_START_DESC + VALID_EVENT_NOTE_DESC,
                "Invalid property ID: " + ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid client index
        assertParseFailure(parser,
                VALID_EVENT_TYPE_DESC + VALID_PROPERTY_ID_DESC + INVALID_CLIENT_ID_DESC
                        + VALID_EVENT_START_DESC + VALID_EVENT_NOTE_DESC,
                "Invalid client ID: " + ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid note (empty)
        assertParseFailure(parser,
                VALID_EVENT_TYPE_DESC + VALID_PROPERTY_ID_DESC + VALID_CLIENT_ID_DESC
                        + VALID_EVENT_START_DESC + INVALID_EVENT_NOTE_DESC,
                Note.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_additionalPreamble_failure() {
        assertParseFailure(parser,
                "some garbage " + VALID_EVENT_TYPE_DESC + VALID_PROPERTY_ID_DESC
                        + VALID_CLIENT_ID_DESC + VALID_EVENT_START_DESC + VALID_EVENT_NOTE_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
    }
}
