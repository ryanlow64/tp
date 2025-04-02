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

    private ClientName clientName;
    private PropertyName propertyName;
    private LocalDateTime beforeDateTime;
    private LocalDateTime afterDateTime;
    private EventType eventType;

    /**
     * Parses the given {@code String} of arguments in the context of the FindEventCommand
     * and returns a FindEventCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FindEventCommand parse(String args) throws ParseException {
        logger.info("Parsing arguments for FindEventCommand: " + args);

        Prefix[] prefixes = Command.COMMAND_WORDS.get(FindEventCommand.COMMAND_WORD).toArray(Prefix[]::new);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, prefixes);
        List<Prefix> prefixesUsed = argMultimap.getPrefixes();

        String trimmedArgs = args.trim();

        if (!argMultimap.getPreamble().isEmpty()) {
            logger.warning("Additional preamble");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
        }

        if (trimmedArgs.isEmpty()) {
            logger.warning("Missing arguments");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(prefixes);
        logger.fine("No repeated arguments supplied");

        checkPrefixesUsedAreValid(prefixesUsed);

        handleNames(argMultimap);
        handleDateTimes(argMultimap, prefixesUsed);
        handleEventType(argMultimap, prefixesUsed);

        LinkedHashMap<Prefix, Predicate<Event>> prefixPredicateMap = getPrefixPredicateMap(prefixesUsed);
        Predicate<Event> combinedPredicate = getCombinedPredicate(prefixPredicateMap);
        return new FindEventCommand(combinedPredicate);
    }

    @Override
    protected LinkedHashMap<Prefix, Predicate<Event>> getPrefixPredicateMap(List<Prefix> prefixesUsed) {
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
        return prefixPredicateMap;
    }

    private void handleNames(ArgumentMultimap argMultimap) throws ParseException {
        clientName = ParserUtil.parseClientName(argMultimap.getValue(PREFIX_EVENT_WITH).orElse(BLANK));
        propertyName = ParserUtil.parsePropertyName(argMultimap.getValue(PREFIX_EVENT_ABOUT)
            .orElse(BLANK));
    }

    private void handleEventType(ArgumentMultimap argMultimap, List<Prefix> prefixesUsed) throws ParseException {
        try {
            eventType = ParserUtil.parseEventType(argMultimap.getValue(PREFIX_EVENT_TYPE).orElse(BLANK));
        } catch (ParseException e) {
            if (prefixesUsed.contains(PREFIX_EVENT_TYPE)) {
                logger.warning("Invalid event type");
                throw new ParseException(e.getMessage());
            }
        }
    }

    private void handleDateTimes(ArgumentMultimap argMultimap, List<Prefix> prefixesUsed) throws ParseException {
        try {
            beforeDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_EVENT_BEFORE).orElse(BLANK));
        } catch (ParseException e) {
            if (prefixesUsed.contains(PREFIX_EVENT_BEFORE)) {
                logger.warning("Invalid date format for before date");
                throw new ParseException(e.getMessage());
            }
        }

        try {
            afterDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_EVENT_AFTER).orElse(BLANK));
        } catch (ParseException e) {
            if (prefixesUsed.contains(PREFIX_EVENT_AFTER)) {
                logger.warning("Invalid date format for after date");
                throw new ParseException(e.getMessage());
            }
        }
    }
}
