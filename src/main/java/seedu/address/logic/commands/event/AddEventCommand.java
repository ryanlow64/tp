package seedu.address.logic.commands.event;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_NAME;

/**
 * Adds an event to the address book.
 */
public class AddEventCommand extends AddCommand<Event> {
    public static final String COMMAND_WORD = "add_event";
    public static final String MESSAGE_USAGE = new StringBuilder(COMMAND_WORD).append(" ")
            .append(PREFIX_EVENT_TYPE).append("EVENT_TYPE ")
            .append(PREFIX_PROPERTY_NAME).append("PROPERTY_NAME ")
            .append(PREFIX_CLIENT_NAME).append("CLIENT_NAME ")
            .append(PREFIX_EVENT_START).append("EVENT_DATE_TIME ")
            .append(PREFIX_EVENT_NOTE).append("NOTE")
            .append(System.lineSeparator())
            .append("Example: ").append(COMMAND_WORD).append(" ")
            .append(PREFIX_EVENT_TYPE).append("meeting ")
            .append(PREFIX_PROPERTY_NAME).append("Unnamed Residence ")
            .append(PREFIX_CLIENT_NAME).append("John Doe ")
            .append(PREFIX_EVENT_START).append("30-04-2025 1742 ")
            .append(PREFIX_EVENT_NOTE).append("nil")
            .toString();

    public static final String MESSAGE_SUCCESS = "New event added: %s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book.";

    /**
     * Creates an AddCommand to add the specified {@code Event}.
     */
    public AddEventCommand(Event event) {
        super(event);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        model.addEvent(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.formatEvent(toAdd)));
    }
}
