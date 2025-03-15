package seedu.address.logic.parser.property;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.commands.property.AddPropertyCommand;
import seedu.address.logic.parser.AddCommandParser;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.commons.Address;
import seedu.address.model.property.*;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddPropertyCommandParser extends AddCommandParser<Property> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPropertyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PROPERTY_NAME, PREFIX_ADDRESS, PREFIX_PRICE, PREFIX_SIZE,
                        PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_PROPERTY_NAME, PREFIX_ADDRESS, PREFIX_PRICE, PREFIX_SIZE,
                PREFIX_DESCRIPTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPropertyCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PROPERTY_NAME, PREFIX_ADDRESS, PREFIX_PRICE, PREFIX_SIZE,
                PREFIX_DESCRIPTION);
        PropertyName propertyName = ParserUtil.parsePropertyName(argMultimap.getValue(PREFIX_PROPERTY_NAME).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Optional<Price> price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get());
        Optional<Size> size = ParserUtil.parseSize(argMultimap.getValue(PREFIX_SIZE).get());
        Optional<String> description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Property property = new Property(propertyName, address, price, size, description);

        return new AddPropertyCommand(property);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
