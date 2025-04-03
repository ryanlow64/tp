package seedu.address.logic.parser.client;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEYWORDS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.client.FindClientCommand;
import seedu.address.model.client.Client;
import seedu.address.model.client.ClientName;
import seedu.address.model.client.Email;
import seedu.address.model.client.Phone;
import seedu.address.model.client.predicates.ClientAddressContainsPredicate;
import seedu.address.model.client.predicates.ClientEmailContainsPredicate;
import seedu.address.model.client.predicates.ClientNameContainsKeywordsPredicate;
import seedu.address.model.client.predicates.ClientPhoneContainsPredicate;
import seedu.address.model.commons.Address;

public class FindClientCommandParserTest {

    private final FindClientCommandParser parser = new FindClientCommandParser();

    @BeforeAll
    public static void setUp() {
        FindClientCommand.addCommandWord();
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindClientCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        FindClientCommand expectedFindCommand =
                new FindClientCommand(new ClientNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, " " + PREFIX_KEYWORDS + "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + PREFIX_KEYWORDS + "\n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validAllFields_returnsFindCommand() {
        // Test case with all valid prefixes provided.
        Predicate<Client> expectedPredicate = new ClientNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        expectedPredicate = expectedPredicate.and(new ClientPhoneContainsPredicate("1234567"));
        expectedPredicate = expectedPredicate.and(new ClientEmailContainsPredicate("alice@example.com"));
        expectedPredicate = expectedPredicate.and(new ClientAddressContainsPredicate("Blk 123"));

        Client testClient = new Client(
            new ClientName("Alice"),
            new Phone("12345679"),
            new Email("alice@example.com"),
            new Address("Blk 123")
        );
        assertTrue(expectedPredicate.test(testClient));
    }

    @Test
    public void parse_emptyAddressPrefix_throwsParseException() {
        // Test that supplying PREFIX_ADDRESS with an empty value throws an exception.
        assertParseFailure(parser, " " + PREFIX_KEYWORDS + "Alice Bob" + " " + PREFIX_ADDRESS.getOrPrefix() + "   ",
            "No address provided");
    }

    @Test
    public void parse_emptyEmailPrefix_throwsParseException() {
        // Test that supplying PREFIX_EMAIL with an empty value throws an exception.
        assertParseFailure(parser, " " + PREFIX_KEYWORDS + "Alice Bob" + " " + PREFIX_EMAIL.getOrPrefix() + "   ",
            "No email provided");
    }

    @Test
    public void parse_emptyPhonePrefix_throwsParseException() {
        // Test that supplying PREFIX_PHONE with an empty value throws an exception.
        assertParseFailure(parser, " " + PREFIX_KEYWORDS + "Alice Bob" + " " + PREFIX_PHONE.getOrPrefix() + "   ",
            "No phone number provided");
    }

    @Test
    public void parse_mixedConnectivePrefixes_throwsParseException() {
        // Mixing default prefix and its OR variant should throw an exception.
        String input = " " + PREFIX_KEYWORDS + "Alice" + " " + PREFIX_PHONE.getOrPrefix() + "123456780" + " "
            + PREFIX_ADDRESS.getAndPrefix() + "Blk 123";
        assertParseFailure(parser, input, "Cannot mix AND and OR connective prefixes");
    }
}
