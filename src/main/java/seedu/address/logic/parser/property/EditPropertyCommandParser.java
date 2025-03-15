package seedu.address.logic.parser.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.commons.core.index.Index;

import seedu.address.logic.commands.property.EditPropertyCommand;
import seedu.address.logic.commands.property.EditPropertyCommand.EditPropertyDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;

import seedu.address.logic.parser.EditCommandParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.property.Property;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditPropertyCommandParser extends EditCommandParser<Property> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditPropertyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PROPERTY_NAME, PREFIX_ADDRESS, PREFIX_PRICE, PREFIX_SIZE,
                        PREFIX_DESCRIPTION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPropertyCommand.MESSAGE_USAGE),
                    pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PROPERTY_NAME, PREFIX_ADDRESS, PREFIX_PRICE, PREFIX_SIZE,
                PREFIX_DESCRIPTION);

        EditPropertyDescriptor editPropertyDescriptor = new EditPropertyDescriptor();

        if (argMultimap.getValue(PREFIX_PROPERTY_NAME).isPresent()) {
            editPropertyDescriptor.setPropertyName(ParserUtil.parsePropertyName(argMultimap.getValue(PREFIX_PROPERTY_NAME)
                    .get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editPropertyDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_PRICE).isPresent()) {
            editPropertyDescriptor.setPrice(ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get()));
        }
        if (argMultimap.getValue(PREFIX_SIZE).isPresent()) {
            editPropertyDescriptor.setSize(ParserUtil.parseSize(argMultimap.getValue(PREFIX_SIZE).get()));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editPropertyDescriptor.setDescription(ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }

        if (!editPropertyDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditPropertyCommand.MESSAGE_NOT_EDITED);
        }

        return new EditPropertyCommand(index, editPropertyDescriptor);
    }
}