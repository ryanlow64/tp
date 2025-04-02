package seedu.address.logic.parser;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public abstract class EditCommandParser<T> implements Parser<EditCommand<T>> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public abstract EditCommand<T> parse(String args) throws ParseException;
}
