package seedu.address.logic.parser.property;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.property.FindPropertyCommand;
import seedu.address.model.commons.PropertyNameContainsKeywordsPredicate;

public class FindPropertyCommandParserTest {

    private FindPropertyCommandParser parser = new FindPropertyCommandParser();

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
        assertParseSuccess(parser, "Maple Orchid", expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Maple \n \t Orchid  \t", expectedCommand);
    }
}
