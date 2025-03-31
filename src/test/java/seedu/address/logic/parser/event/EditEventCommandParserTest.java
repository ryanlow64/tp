package seedu.address.logic.parser.event;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_DATETIME;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.event.EditEventCommand;
import seedu.address.logic.commands.event.EditEventCommand.EditEventDescriptor;
import seedu.address.model.event.EventType;
import seedu.address.model.event.Note;

public class EditEventCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE);
    private static final String EVENT_NOTE = "Meet At Cafe";
    private static final String EVENT_TYPE = "Meeting";
    private static final String EVENT_START = "06-06-2025 1300";

    private static final String EVENT_NOTE_FIELD = " " + PREFIX_EVENT_NOTE + EVENT_NOTE;
    private static final String EVENT_TYPE_FIELD = " " + PREFIX_EVENT_TYPE + EVENT_TYPE;
    private static final String EVENT_START_FIELD = " " + PREFIX_EVENT_START + EVENT_START;
    private static final String EVENT_CLIENT_ID_FIELD = " " + PREFIX_CLIENT_ID + "1";
    private static final String EVENT_PROPERTY_ID_FIELD = " " + PREFIX_PROPERTY_ID + "1";

    private EditEventCommandParser parser = new EditEventCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, EVENT_NOTE_FIELD, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditEventCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + EVENT_PROPERTY_ID_FIELD, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + EVENT_CLIENT_ID_FIELD, MESSAGE_INVALID_FORMAT);

        // invalid arguments as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid event type
        assertParseFailure(parser, "1 " + PREFIX_EVENT_TYPE + "Lunch", EventType.MESSAGE_CONSTRAINTS);
        // invalid note
        assertParseFailure(parser, "1 " + PREFIX_EVENT_NOTE, Note.MESSAGE_CONSTRAINTS);
        // invalid datetime
        assertParseFailure(parser, "1 " + PREFIX_EVENT_START + "1300 2025-09-09",
                String.format(MESSAGE_INVALID_DATETIME, "1300 2025-09-09"));
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + EVENT_CLIENT_ID_FIELD + EVENT_NOTE_FIELD
                + EVENT_PROPERTY_ID_FIELD + EVENT_START_FIELD + EVENT_TYPE_FIELD;

        EditEventDescriptor descriptor = new EditEventDescriptor();
        descriptor.setEventType(EventType.valueOf(EVENT_TYPE.toUpperCase()));
        descriptor.setClientId(Index.fromOneBased(1));
        descriptor.setPropertyId(Index.fromOneBased(1));
        descriptor.setDateTime(LocalDateTime.of(2025, 06, 06, 13, 00));
        descriptor.setNote(new Note(EVENT_NOTE));

        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + EVENT_START_FIELD + EVENT_NOTE_FIELD;

        EditEventDescriptor descriptor = new EditEventDescriptor();
        descriptor.setDateTime(LocalDateTime.of(2025, 06, 06, 13, 00));
        descriptor.setNote(new Note(EVENT_NOTE));

        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = INDEX_THIRD;

        // event type only
        String userInput = targetIndex.getOneBased() + EVENT_TYPE_FIELD;
        EditEventDescriptor descriptor = new EditEventDescriptor();
        descriptor.setEventType(EventType.valueOf(EVENT_TYPE.toUpperCase()));
        assertParseSuccess(parser, userInput, new EditEventCommand(targetIndex, descriptor));

        // note only
        userInput = targetIndex.getOneBased() + EVENT_NOTE_FIELD;
        descriptor = new EditEventDescriptor();
        descriptor.setNote(new Note(EVENT_NOTE));
        assertParseSuccess(parser, userInput, new EditEventCommand(targetIndex, descriptor));

        // datetime only
        userInput = targetIndex.getOneBased() + EVENT_START_FIELD;
        descriptor = new EditEventDescriptor();
        descriptor.setDateTime(LocalDateTime.of(2025, 06, 06, 13, 00));
        assertParseSuccess(parser, userInput, new EditEventCommand(targetIndex, descriptor));
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        Index targetIndex = INDEX_FIRST;

        String userInput = targetIndex.getOneBased() + EVENT_NOTE_FIELD + EVENT_NOTE_FIELD;
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_NOTE));

        userInput = targetIndex.getOneBased() + EVENT_START_FIELD + EVENT_START_FIELD;
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_START));
    }
}
