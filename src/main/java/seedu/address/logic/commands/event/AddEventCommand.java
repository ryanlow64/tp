package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ID;

import java.time.LocalDateTime;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.client.ClientName;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventType;
import seedu.address.model.event.Note;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyName;

/**
 * Adds an event to the address book.
 */
public class AddEventCommand extends Command {
    public static final String COMMAND_WORD = "add_event";
    public static final String MESSAGE_USAGE = new StringBuilder(COMMAND_WORD).append(" ")
            .append(PREFIX_EVENT_TYPE).append("EVENT_TYPE ")
            .append(PREFIX_PROPERTY_ID).append("PROPERTY_ID ")
            .append(PREFIX_CLIENT_ID).append("CLIENT_ID ")
            .append(PREFIX_EVENT_START).append("EVENT_DATE_TIME ")
            .append(PREFIX_EVENT_NOTE).append("NOTE")
            .append(System.lineSeparator())
            .append("Example: ").append(COMMAND_WORD).append(" ")
            .append(PREFIX_EVENT_TYPE).append("meeting ")
            .append(PREFIX_PROPERTY_ID).append("1 ")
            .append(PREFIX_CLIENT_ID).append("2 ")
            .append(PREFIX_EVENT_START).append("30-03-2025 1730 ")
            .append(PREFIX_EVENT_NOTE).append("N/A")
            .toString();

    public static final String MESSAGE_SUCCESS = "New event added: %s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book.";
    public static final String MESSAGE_INVALID_PROPERTY_ID = "Invalid property ID.";
    public static final String MESSAGE_INVALID_CLIENT_ID = "Invalid client ID.";

    private final EventType eventType;
    private final Index propertyId;
    private final Index clientId;
    private final LocalDateTime dateTime;
    private final Note note;

    /**
     * Creates an AddCommand to add the specified {@code Event}.
     */
    public AddEventCommand(EventType eventType, Index propertyId, Index clientId, LocalDateTime dateTime, Note note) {
        requireNonNull(eventType);
        requireNonNull(propertyId);
        requireNonNull(clientId);
        requireNonNull(dateTime);
        requireNonNull(note);
        this.eventType = eventType;
        this.propertyId = propertyId;
        this.clientId = clientId;
        this.dateTime = dateTime;
        this.note = note;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Get the property by ID
        List<Property> propertyList = model.getFilteredPropertyList();
        if (propertyId.getZeroBased() >= propertyList.size()) {
            throw new CommandException(MESSAGE_INVALID_PROPERTY_ID);
        }
        Property property = propertyList.get(propertyId.getZeroBased());
        PropertyName propertyName = property.getPropertyName();

        // Fetch clients by index
        List<Client> clientList = model.getFilteredClientList();
        // Check if client ID is valid
        if (clientId.getZeroBased() >= clientList.size()) {
            throw new CommandException(MESSAGE_INVALID_CLIENT_ID);
        }
        Client client = clientList.get(clientId.getZeroBased());
        ClientName clientName = client.getClientName();

        // check if event is in the future
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new CommandException("Event cannot be in the past.");
        }

        // check if event conflicts with existing events
        for (Event event : model.getFilteredEventList()) {
            if (event.getDateTime().equals(dateTime)) {
                throw new CommandException("Event conflicts with existing event.");
            }
        }

        Event toAdd = new Event(eventType, propertyName, clientName, dateTime, note);

        if (model.hasEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        model.addEvent(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.formatEvent(toAdd)));
    }
}
