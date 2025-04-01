package seedu.address.logic.parser.event;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ABOUT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_AFTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_BEFORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_WITH;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.event.FindEventCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.FindCommandParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.ClientName;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventType;
import seedu.address.model.event.predicates.EventAboutPropertyPredicate;
import seedu.address.model.event.predicates.EventAfterDateTimePredicate;
import seedu.address.model.event.predicates.EventBeforeDateTimePredicate;
import seedu.address.model.event.predicates.EventOfTypePredicate;
import seedu.address.model.event.predicates.EventWithClientPredicate;
import seedu.address.model.property.PropertyName;

/**
 * Parses input arguments and creates a new FindEventCommand object
 */
public class FindEventCommandParser extends FindCommandParser<Event> {

    private static final Logger logger = LogsCenter.getLogger(FindEventCommandParser.class);
    private static final String BLANK = "BLANK";

    /**
     * Parses the given {@code String} of arguments in the context of the FindEventCommand
     * and returns a FindEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindEventCommand parse(String args) throws ParseException {
        logger.info("Parsing arguments for FindEventCommand: " + args);

        Prefix[] prefixes = Command.COMMAND_WORDS.get(FindEventCommand.COMMAND_WORD).toArray(Prefix[]::new);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, prefixes);
        List<Prefix> prefixesUsed = argMultimap.getPrefixes();

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            logger.warning("Missing arguments");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_ABOUT, PREFIX_EVENT_WITH, PREFIX_EVENT_TYPE);
        logger.fine("No repeated arguments supplied");

        checkPrefixesUsedAreValid(prefixesUsed);

        ClientName clientName = ParserUtil.parseClientName(argMultimap.getValue(PREFIX_EVENT_WITH).orElse(BLANK));
        PropertyName propertyName = ParserUtil.parsePropertyName(argMultimap.getValue(PREFIX_EVENT_ABOUT)
            .orElse(BLANK));

        LocalDateTime beforeDateTime = null;
        try {
            beforeDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_EVENT_BEFORE).orElse(BLANK));
        } catch (ParseException e) {
            if (prefixesUsed.contains(PREFIX_EVENT_BEFORE)) {
                logger.warning("Invalid date format for before date");
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
            }
        }

        LocalDateTime afterDateTime = null;
        try {
            afterDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_EVENT_AFTER).orElse(BLANK));
        } catch (ParseException e) {
            if (prefixesUsed.contains(PREFIX_EVENT_AFTER)) {
                logger.warning("Invalid date format for after date");
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
            }
        }

        EventType eventType = null;
        try {
            eventType = ParserUtil.parseEventType(argMultimap.getValue(PREFIX_EVENT_TYPE).orElse(BLANK));
        } catch (ParseException e) {
            if (prefixesUsed.contains(PREFIX_EVENT_TYPE)) {
                logger.warning("Invalid event type");
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
            }
        }

        LinkedHashMap<Prefix, Predicate<Event>> prefixPredicateMap = new LinkedHashMap<>();
        for (Prefix prefix : prefixesUsed) {
            if (prefix.equals(PREFIX_EVENT_WITH)) {
                prefixPredicateMap.put(prefix, new EventWithClientPredicate(clientName));
            } else if (prefix.equals(PREFIX_EVENT_ABOUT)) {
                prefixPredicateMap.put(prefix, new EventAboutPropertyPredicate(propertyName));
            } else if (prefix.equals(PREFIX_EVENT_TYPE)) {
                prefixPredicateMap.put(prefix, new EventOfTypePredicate(eventType));
            } else if (prefix.equals(PREFIX_EVENT_BEFORE)) {
                prefixPredicateMap.put(prefix, new EventBeforeDateTimePredicate(beforeDateTime));
            } else if (prefix.equals(PREFIX_EVENT_AFTER)) {
                prefixPredicateMap.put(prefix, new EventAfterDateTimePredicate(afterDateTime));
            }
        }

        Predicate<Event> combinedPredicate = getCombinedPredicate(prefixPredicateMap);
        return new FindEventCommand(combinedPredicate);
    }
}
