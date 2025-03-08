package seedu.address.logic.parser;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public abstract class AddCommandParser<T> implements Parser<AddCommand<T>> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     */
    public abstract AddCommand<T> parse(String args) throws ParseException;
}
