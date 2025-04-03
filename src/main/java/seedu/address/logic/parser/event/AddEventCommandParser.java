package seedu.address.logic.parser.event;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ID;

import java.time.LocalDateTime;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.AddEventCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventType;
import seedu.address.model.event.Note;

/**
 * Parses input arguments and creates a new AddEventCommand object.
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {
    private static final Logger logger = LogsCenter.getLogger(AddEventCommandParser.class);

    @Override
    public AddEventCommand parse(String args) throws ParseException {
        logger.info("Parsing arguments for AddEventCommand: " + args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_EVENT_START, PREFIX_EVENT_TYPE, PREFIX_CLIENT_ID, PREFIX_PROPERTY_ID, PREFIX_EVENT_NOTE);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_START, PREFIX_EVENT_TYPE,
                PREFIX_CLIENT_ID, PREFIX_PROPERTY_ID, PREFIX_EVENT_NOTE)
                || !argMultimap.getPreamble().isEmpty()) {
            logger.warning("Missing arguments");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_START, PREFIX_EVENT_TYPE, PREFIX_CLIENT_ID,
                PREFIX_PROPERTY_ID, PREFIX_EVENT_NOTE);
        logger.fine("No repeated arguments supplied");

        // Parse date/time
        LocalDateTime dateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_EVENT_START).get());
        if (dateTime.isBefore(LocalDateTime.of(2025, 1, 1, 0, 0))) {
            logger.warning("Event date is in the past");
            throw new ParseException(AddEventCommand.MESSAGE_EVENT_IN_PAST);
        }
        logger.fine("Date & time: " + dateTime);

        // Parse event type
        EventType eventType = ParserUtil.parseEventType(argMultimap.getValue(PREFIX_EVENT_TYPE).get());
        logger.fine("Event type: " + eventType);

        // Parse client ID
        Index clientId;
        try {
            clientId = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CLIENT_ID).get());
        } catch (ParseException pe) {
            logger.warning("Invalid client ID");
            throw new ParseException(String.format("Invalid client ID: %s", pe.getMessage()));
        }
        logger.fine("Client ID: " + clientId);

        // Parse property ID
        Index propertyId;
        try {
            propertyId = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PROPERTY_ID).get());
        } catch (ParseException pe) {
            logger.warning("Invalid property ID");
            throw new ParseException(String.format("Invalid property ID: %s", pe.getMessage()));
        }
        logger.fine("Property ID: " + propertyId);

        // Parse note
        Note note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_EVENT_NOTE).get());
        logger.fine("Note: " + note);

        logger.info("AddEventCommand created");
        return new AddEventCommand(dateTime, eventType, clientId, propertyId, note);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
