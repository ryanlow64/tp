package seedu.address.logic.parser.client;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEYWORDS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.client.FindClientCommand;
import seedu.address.model.client.predicates.ClientNameContainsKeywordsPredicate;

public class FindClientCommandParserTest {

    private FindClientCommandParser parser = new FindClientCommandParser();

    @BeforeAll
    public static void setUp() {
        FindClientCommand.addCommandWord();
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            FindClientCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindClientCommand expectedFindCommand =
                new FindClientCommand(new ClientNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        System.out.println(expectedFindCommand);
        assertParseSuccess(parser, " " + PREFIX_KEYWORDS + "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + PREFIX_KEYWORDS + "\n Alice \n \t Bob  \t", expectedFindCommand);
    }

}
