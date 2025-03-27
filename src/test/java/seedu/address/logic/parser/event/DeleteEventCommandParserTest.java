package seedu.address.logic.parser.event;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.DeleteEventCommand;
import seedu.address.logic.parser.DeleteCommandParserTest;
import seedu.address.model.event.Event;

class DeleteEventCommandParserTest extends DeleteCommandParserTest<Event> {
    private final DeleteEventCommandParser parser = new DeleteEventCommandParser();

    @Test
    public void parse_validIndex_success() {
        assertParseSuccess(parser, "1", new DeleteEventCommand(INDEX_FIRST));
        assertParseSuccess(parser, "42", new DeleteEventCommand(Index.fromOneBased(42)));
    }

    @Test
    public void parse_invalidIndex_failure() {
        assertParseFailure(parser, "NaN", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteEventCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteEventCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraArgs_failure() {
        assertParseFailure(parser, "42 42", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteEventCommand.MESSAGE_USAGE));
    }
}
