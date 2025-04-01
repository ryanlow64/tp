package seedu.address.logic.parser.property;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.property.FindPropertyCommand;
import seedu.address.logic.parser.FindCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.commons.PropertyNameContainsKeywordsPredicate;
import seedu.address.model.property.Property;

/**
 * Parses input arguments and creates a new FindPropertyCommand object.
 */
public class FindPropertyCommandParser extends FindCommandParser<Property> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPropertyCommand
     * and returns a FindPropertyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPropertyCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPropertyCommand.MESSAGE_USAGE));
        }

        // Split the input into keywords based on space
        String[] propertyKeywords = trimmedArgs.split("\\s+");

        // Return a new FindPropertyCommand with a predicate that checks for keywords in the property name
        return new FindPropertyCommand(new PropertyNameContainsKeywordsPredicate(Arrays.asList(propertyKeywords)));
    }
}
