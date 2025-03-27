package seedu.address.logic.parser.event;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ABOUT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_WITH;

import java.util.function.Predicate;

import seedu.address.logic.commands.event.FindEventCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.FindCommandParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.ClientName;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventType;
import seedu.address.model.event.predicates.EventAboutPropertyPredicate;
import seedu.address.model.event.predicates.EventOfTypePredicate;
import seedu.address.model.event.predicates.EventWithClientPredicate;
import seedu.address.model.property.PropertyName;

/**
 * Parses input arguments and creates a new FindEventCommand object
 */
public class FindEventCommandParser extends FindCommandParser<Event> {

    private static final String BLANK = "BLANK";

    /**
     * Parses the given {@code String} of arguments in the context of the FindEventCommand
     * and returns a FindEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_EVENT_ABOUT, PREFIX_EVENT_WITH, PREFIX_EVENT_TYPE);

        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_ABOUT, PREFIX_EVENT_WITH, PREFIX_EVENT_TYPE);
        ClientName clientName = ParserUtil.parseClientName(argMultimap.getValue(PREFIX_EVENT_WITH).orElse(BLANK));
        PropertyName propertyName = ParserUtil.parsePropertyName(argMultimap.getValue(PREFIX_EVENT_ABOUT)
            .orElse(BLANK));

        EventType eventType;
        try {
            eventType = ParserUtil.parseEventType(argMultimap.getValue(PREFIX_EVENT_TYPE).orElse(BLANK));
        } catch (ParseException e) {
            eventType = null;
        }

        EventWithClientPredicate eventWithClientPredicate = new EventWithClientPredicate(clientName);
        EventAboutPropertyPredicate eventAboutPropertyPredicate = new EventAboutPropertyPredicate(propertyName);
        EventOfTypePredicate eventOfTypePredicate = new EventOfTypePredicate(eventType);
        Predicate<Event> predicate = eventWithClientPredicate.or(eventAboutPropertyPredicate).or(eventOfTypePredicate);

        return new FindEventCommand(predicate);
    }
}
