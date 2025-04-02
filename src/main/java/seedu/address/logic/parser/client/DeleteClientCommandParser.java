package seedu.address.logic.parser.client;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.client.DeleteClientCommand;
import seedu.address.logic.parser.DeleteCommandParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Client;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteClientCommandParser extends DeleteCommandParser<Client> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteClientCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteClientCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteClientCommand.MESSAGE_USAGE), pe);
        }
    }
}
