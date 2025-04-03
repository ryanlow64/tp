package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_EVENT_IN_PAST;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ID;

import java.time.LocalDateTime;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.EditEventCommand;
import seedu.address.logic.commands.event.EditEventCommand.EditEventDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.EditCommandParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;


/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditEventCommandParser extends EditCommandParser<Event> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public EditEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_EVENT_START, PREFIX_EVENT_TYPE, PREFIX_CLIENT_ID, PREFIX_PROPERTY_ID, PREFIX_EVENT_NOTE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE),
                    pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_EVENT_START, PREFIX_EVENT_TYPE, PREFIX_CLIENT_ID, PREFIX_PROPERTY_ID, PREFIX_EVENT_NOTE);

        EditEventDescriptor editEventDescriptor = new EditEventDescriptor();

        if (argMultimap.getValue(PREFIX_EVENT_START).isPresent()) {
            LocalDateTime dateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_EVENT_START).get());
            if (dateTime.isBefore(LocalDateTime.of(2025, 1, 1, 0, 0))) {
                throw new ParseException(MESSAGE_EVENT_IN_PAST);
            }
            editEventDescriptor.setDateTime(dateTime);
        }
        if (argMultimap.getValue(PREFIX_EVENT_TYPE).isPresent()) {
            editEventDescriptor.setEventType(ParserUtil.parseEventType(argMultimap.getValue(PREFIX_EVENT_TYPE).get()));
        }
        if (argMultimap.getValue(PREFIX_CLIENT_ID).isPresent()) {
            editEventDescriptor.setClientId(ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CLIENT_ID).get()));
        }
        if (argMultimap.getValue(PREFIX_PROPERTY_ID).isPresent()) {
            editEventDescriptor.setPropertyId(ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PROPERTY_ID).get()));
        }
        if (argMultimap.getValue(PREFIX_EVENT_NOTE).isPresent()) {
            editEventDescriptor.setNote(ParserUtil.parseNote(argMultimap.getValue(PREFIX_EVENT_NOTE).get()));
        }

        if (!editEventDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditEventCommand.MESSAGE_NOT_EDITED);
        }

        return new EditEventCommand(index, editEventDescriptor);
    }
}
