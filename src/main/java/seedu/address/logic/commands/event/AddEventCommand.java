package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ID;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
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
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the address book. "
            + "Parameters: "
            + PREFIX_EVENT_START + "EVENT_DATE_TIME "
            + PREFIX_EVENT_TYPE + "EVENT_TYPE "
            + PREFIX_CLIENT_ID + "CLIENT_ID "
            + PREFIX_PROPERTY_ID + "PROPERTY_ID "
            + PREFIX_EVENT_NOTE + "NOTE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENT_START + "30-04-2025 1700 "
            + PREFIX_EVENT_TYPE + "meeting "
            + PREFIX_CLIENT_ID + "2 "
            + PREFIX_PROPERTY_ID + "1 "
            + PREFIX_EVENT_NOTE + "NA";

    public static final String MESSAGE_SUCCESS = "New event added: %s";
    public static final String MESSAGE_EVENT_CONFLICT = "Event conflicts with existing event.";
    public static final String MESSAGE_INVALID_PROPERTY_ID = "Invalid property ID.";
    public static final String MESSAGE_INVALID_CLIENT_ID = "Invalid client ID.";
    public static final String MESSAGE_EVENT_IN_PAST = "Event cannot be before 01-01-2025 0000.";

    private static final Logger logger = LogsCenter.getLogger(AddEventCommand.class);

    private final LocalDateTime dateTime;
    private final EventType eventType;
    private final Index clientId;
    private final Index propertyId;
    private final Note note;

    /**
     * Creates an AddCommand to add the specified {@code Event}.
     *
     * @param dateTime The date and time of the event.
     * @param eventType The type of the event.
     * @param clientId The {@code Index} of the associated client in the filtered list.
     * @param propertyId The {@code Index} of the associated property in the filtered list.
     * @param note Additional notes about the event.
     */
    public AddEventCommand(LocalDateTime dateTime, EventType eventType, Index clientId, Index propertyId, Note note) {
        requireNonNull(dateTime);
        requireNonNull(eventType);
        requireNonNull(propertyId);
        requireNonNull(clientId);
        requireNonNull(note);
        this.dateTime = dateTime;
        this.eventType = eventType;
        this.clientId = clientId;
        this.propertyId = propertyId;
        this.note = note;
        logger.info(String.format("AddEventCommand initialized with: %s, %s, %d, %d, %s",
                dateTime, eventType, clientId.getZeroBased(), propertyId.getZeroBased(), note));
    }

    /**
     * Adds the command word to the command word list.
     */
    public static void addCommandWord() {
        Prefix[] prefixes = {
            PREFIX_EVENT_START,
            PREFIX_EVENT_TYPE,
            PREFIX_CLIENT_ID,
            PREFIX_PROPERTY_ID,
            PREFIX_EVENT_NOTE
        };
        initialiseCommandWord(COMMAND_WORD, prefixes);
    }

    /**
     * Executes the {@code AddEventCommand} and adds the new event to the model if it is valid.
     *
     * @throws CommandException if the constraints for adding events are not satisfied.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing AddEventCommand");

        // check if event is way too far back
        if (dateTime.isBefore(LocalDateTime.of(2025, 1, 1, 0, 0))) {
            logger.warning("Date & time before 2025: " + dateTime);
            throw new CommandException(MESSAGE_EVENT_IN_PAST);
        }
        logger.fine("Date & time: " + dateTime);

        // check if event conflicts with existing events
        for (Event event : model.getFilteredEventList()) {
            if (event.getDateTime().equals(dateTime)) {
                logger.warning("Conflict with existing event");
                throw new CommandException(MESSAGE_EVENT_CONFLICT);
            }
        }

        // Fetch clients by index
        List<Client> clientList = model.getFilteredClientList();
        int clientIdZeroBased = clientId.getZeroBased();
        if (clientIdZeroBased >= clientList.size()) {
            logger.warning("Invalid client ID: " + clientIdZeroBased);
            throw new CommandException(MESSAGE_INVALID_CLIENT_ID);
        }
        Client client = clientList.get(clientIdZeroBased);
        ClientName clientName = client.getFullName();
        logger.fine("Client ID: " + clientIdZeroBased);

        // Get the property by ID
        List<Property> propertyList = model.getFilteredPropertyList();
        int propertyIdZeroBased = propertyId.getZeroBased();
        if (propertyIdZeroBased >= propertyList.size()) {
            logger.warning("Invalid property ID: " + propertyIdZeroBased);
            throw new CommandException(MESSAGE_INVALID_PROPERTY_ID);
        }
        Property property = propertyList.get(propertyIdZeroBased);
        PropertyName propertyName = property.getFullName();
        logger.fine("Property ID: " + propertyIdZeroBased);

        Event toAdd = new Event(dateTime, eventType, clientName, propertyName, note);
        model.addEvent(toAdd);
        String eventDescription = Messages.formatEvent(toAdd);
        logger.info("Added event: " + eventDescription);

        return new CommandResult(String.format(MESSAGE_SUCCESS, eventDescription));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddEventCommand otherAddCommand)) {
            return false;
        }

        return this.dateTime.equals(otherAddCommand.dateTime)
                && this.eventType.equals(otherAddCommand.eventType)
                && this.clientId.equals(otherAddCommand.clientId)
                && this.propertyId.equals(otherAddCommand.propertyId)
                && this.note.equals(otherAddCommand.note);
    }
}
