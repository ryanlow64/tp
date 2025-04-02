package seedu.address.logic.parser.property;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEYWORDS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.property.FindPropertyCommand;
import seedu.address.model.property.predicates.PropertyNameContainsKeywordsPredicate;

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
}
