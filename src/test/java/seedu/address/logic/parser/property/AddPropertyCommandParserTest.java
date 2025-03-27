package seedu.address.logic.parser.property;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_MAPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_MAPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OWNER_MAPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_MAPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROPERTY_NAME_MAPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SIZE_MAPLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SIZE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.property.AddPropertyCommand;
import seedu.address.model.client.ClientName;
import seedu.address.model.property.Description;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyName;
import seedu.address.model.property.Size;
import seedu.address.testutil.PropertyBuilder;

public class AddPropertyCommandParserTest {

    private AddPropertyCommandParser parser = new AddPropertyCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Property expectedProperty = new PropertyBuilder()
                .withPropertyName(VALID_PROPERTY_NAME_MAPLE)
                .withAddress(VALID_ADDRESS_MAPLE)
                .withPrice(VALID_PRICE_MAPLE)
                .withSize(VALID_SIZE_MAPLE)
                .withDescription(VALID_DESCRIPTION_MAPLE)
                .withOwner(VALID_OWNER_MAPLE)
                .build();

        String userInput = " " + PREFIX_PROPERTY_NAME + VALID_PROPERTY_NAME_MAPLE + " "
                + PREFIX_ADDRESS + VALID_ADDRESS_MAPLE + " "
                + PREFIX_PRICE + VALID_PRICE_MAPLE + " "
                + PREFIX_SIZE + VALID_SIZE_MAPLE + " "
                + PREFIX_DESCRIPTION + VALID_DESCRIPTION_MAPLE + " "
                + PREFIX_CLIENT_NAME + VALID_OWNER_MAPLE;

        assertParseSuccess(parser, userInput, new AddPropertyCommand(expectedProperty));
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPropertyCommand.MESSAGE_USAGE);

        // missing property name prefix
        assertParseFailure(parser, VALID_PROPERTY_NAME_MAPLE + PREFIX_ADDRESS + VALID_ADDRESS_MAPLE
                + PREFIX_PRICE + VALID_PRICE_MAPLE + PREFIX_CLIENT_NAME + VALID_OWNER_MAPLE, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid property name
        String userInput = " " + PREFIX_PROPERTY_NAME + "Maple&"
                + " " + PREFIX_ADDRESS + VALID_ADDRESS_MAPLE
                + " " + PREFIX_PRICE + VALID_PRICE_MAPLE;
        assertParseFailure(parser, userInput, PropertyName.MESSAGE_CONSTRAINTS);

        // invalid price
        userInput = " " + PREFIX_PROPERTY_NAME + VALID_PROPERTY_NAME_MAPLE
                + " " + PREFIX_ADDRESS + VALID_ADDRESS_MAPLE
                + " " + PREFIX_PRICE + "-500";
        assertParseFailure(parser, userInput, "Price must be an integer and should not contain any "
                + "special characters!");

        // invalid owner
        userInput = " " + PREFIX_PROPERTY_NAME + VALID_PROPERTY_NAME_MAPLE
                + " " + PREFIX_ADDRESS + VALID_ADDRESS_MAPLE
                + " " + PREFIX_PRICE + VALID_PRICE_MAPLE
                + " " + PREFIX_CLIENT_NAME + "James&";
        assertParseFailure(parser, userInput, ClientName.MESSAGE_CONSTRAINTS);

        // invalid size
        userInput = " " + PREFIX_PROPERTY_NAME + VALID_PROPERTY_NAME_MAPLE
                + " " + PREFIX_ADDRESS + VALID_ADDRESS_MAPLE
                + " " + PREFIX_PRICE + VALID_PRICE_MAPLE
                + " " + PREFIX_SIZE + "99";
        assertParseFailure(parser, userInput, Size.MESSAGE_CONSTRAINTS);

        // invalid description
        userInput = " " + PREFIX_PROPERTY_NAME + VALID_PROPERTY_NAME_MAPLE
                + " " + PREFIX_ADDRESS + VALID_ADDRESS_MAPLE
                + " " + PREFIX_PRICE + VALID_PRICE_MAPLE
                + " " + PREFIX_DESCRIPTION + "x".repeat(60); // too long
        assertParseFailure(parser, userInput, Description.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String userInput = PREAMBLE_NON_EMPTY + " pn/" + VALID_PROPERTY_NAME_MAPLE
                + " a/" + VALID_ADDRESS_MAPLE + " p/" + VALID_PRICE_MAPLE + " cn/" + VALID_OWNER_MAPLE;

        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPropertyCommand.MESSAGE_USAGE));
    }
}
