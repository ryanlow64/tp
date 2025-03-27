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
 * Guarantees:
 * - the event is not too far in the past.
 * - there are no conflicts with existing events.
 * - IDs reference valid existing property and client.
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
    public static final String MESSAGE_EVENT_CONFLICT = "Event conflicts with existing event.";
    public static final String MESSAGE_INVALID_PROPERTY_ID = "Invalid property ID.";
    public static final String MESSAGE_INVALID_CLIENT_ID = "Invalid client ID.";
    public static final String MESSAGE_EVENT_IN_PAST = "Event cannot be before 01-01-2025 0000.";

    private final EventType eventType;
    private final Index propertyId;
    private final Index clientId;
    private final LocalDateTime dateTime;
    private final Note note;

    /**
     * Creates an AddCommand to add the specified {@code Event}.
     *
     * @param eventType The type of the event.
     * @param propertyId The {@code Index} of the associated property in the filtered list.
     * @param clientId The {@code Index} of the associated client in the filtered list.
     * @param dateTime The date and time of the event.
     * @param note Additional notes about the event.
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

    /**
     * Executes the {@code AddEventCommand} and adds the new event to the model if it is valid.
     *
     * @throws CommandException if the constraints for adding events are not satisfied.
     */
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

        // check if event is way too far back
        if (dateTime.isBefore(LocalDateTime.of(2025, 1, 1, 0, 0))) {
            throw new CommandException(MESSAGE_EVENT_IN_PAST);
        }

        // check if event conflicts with existing events
        for (Event event : model.getFilteredEventList()) {
            if (event.getDateTime().equals(dateTime)) {
                throw new CommandException(MESSAGE_EVENT_CONFLICT);
            }
        }

        Event toAdd = new Event(eventType, propertyName, clientName, dateTime, note);

        model.addEvent(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.formatEvent(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddEventCommand otherAddCommand)) {
            return false;
        }

        return this.eventType.equals(otherAddCommand.eventType)
                && this.propertyId.equals(otherAddCommand.propertyId)
                && this.clientId.equals(otherAddCommand.clientId)
                && this.dateTime.equals(otherAddCommand.dateTime)
                && this.note.equals(otherAddCommand.note);
    }
}
