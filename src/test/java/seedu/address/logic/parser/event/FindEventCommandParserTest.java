package seedu.address.logic.parser.event;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.event.FindEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class FindEventCommandParserTest {

    private final FindEventCommandParser parser = new FindEventCommandParser();

    @BeforeAll
    public static void setUp() {
        FindEventCommand.addCommandWord();
    }

    @Test
    public void parse_emptyInput_throwsParseException() {
        String input = "   ";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_validInput_returnsFindEventCommand() throws Exception {
        String input = "with/Alice OR_about/PropertyX OR_etype/meeting OR_before/01-04-2025 1000";
        FindEventCommand command = parser.parse(FindEventCommand.COMMAND_WORD + " " + input);
        assertNotNull(command);
    }

    @Test
    public void parse_duplicatePrefix_throwsParseException() {
        // Duplicate 'with' prefix provided.
        String input = "with/Alice with/Bob";
        assertThrows(ParseException.class, () -> parser.parse(FindEventCommand.COMMAND_WORD + " " + input));
    }

    @Test
    public void parse_invalidBeforeDate_throwsParseException() {
        // Invalid date format for 'before'
        String input = "with/Alice before/invalidDate";
        assertThrows(ParseException.class, () -> parser.parse(FindEventCommand.COMMAND_WORD + " " + input));
    }

    @Test
    public void parse_invalidAfterDate_throwsParseException() {
        // Invalid date format for 'after'
        String input = "with/Alice after/invalidDate";
        assertThrows(ParseException.class, () -> parser.parse(FindEventCommand.COMMAND_WORD + " " + input));
    }

    @Test
    public void parse_invalidEventType_throwsParseException() {
        // Invalid event type provided
        String input = "with/Alice etype/invalidType";
        assertThrows(ParseException.class, () -> parser.parse(FindEventCommand.COMMAND_WORD + " " + input));
    }
}
