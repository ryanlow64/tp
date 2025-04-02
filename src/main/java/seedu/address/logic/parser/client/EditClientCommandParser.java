package seedu.address.logic.parser.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.client.EditClientCommand;
import seedu.address.logic.commands.client.EditClientCommand.EditClientDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.EditCommandParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Client;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditClientCommandParser extends EditCommandParser<Client> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public EditClientCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_CLIENT_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditClientCommand.MESSAGE_USAGE),
                pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CLIENT_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);

        EditClientDescriptor editClientDescriptor = new EditClientDescriptor();

        if (argMultimap.getValue(PREFIX_CLIENT_NAME).isPresent()) {
            editClientDescriptor.setClientName(ParserUtil.parseClientName(argMultimap.getValue(PREFIX_CLIENT_NAME)
                .get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editClientDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editClientDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editClientDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }

        if (!editClientDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditClientCommand.MESSAGE_NOT_EDITED);
        }

        return new EditClientCommand(index, editClientDescriptor);
    }
}
