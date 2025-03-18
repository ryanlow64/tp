package seedu.address.logic.parser.event;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_NAME;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.address.logic.commands.event.AddEventCommand;
import seedu.address.logic.parser.AddCommandParser;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.ClientName;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventType;
import seedu.address.model.event.Note;
import seedu.address.model.property.PropertyName;

/**
 * Parses input arguments and creates a new AddEventCommand object.
 */
public class AddEventCommandParser extends AddCommandParser<Event> {
    @Override
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EVENT_TYPE, PREFIX_PROPERTY_NAME,
                PREFIX_CLIENT_NAME, PREFIX_EVENT_START, PREFIX_EVENT_NOTE);

        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_TYPE, PREFIX_PROPERTY_NAME,
                PREFIX_CLIENT_NAME, PREFIX_EVENT_START, PREFIX_EVENT_NOTE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        EventType eventType = ParserUtil.parseEventType(argMultimap.getValue(PREFIX_EVENT_TYPE).get());
        PropertyName propertyName = ParserUtil.parsePropertyName(argMultimap.getValue(PREFIX_PROPERTY_NAME).get());
        ClientName clientName = ParserUtil.parseClientName(argMultimap.getValue(PREFIX_CLIENT_NAME).get());
        LocalDateTime dateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_EVENT_START).get());
        Note note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_EVENT_NOTE).get());

        Event event = new Event(eventType, propertyName, clientName, dateTime, note);
        return new AddEventCommand(event);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
