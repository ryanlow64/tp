package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditDescriptor;
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
 * Edits the details of an existing event in the address book.
 */
public class EditEventCommand extends EditCommand<Event> {

    public static final String COMMAND_WORD = "edit_event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the event identified "
            + "by the index number used in the displayed client list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_EVENT_START + "EVENT_DATE_TIME] "
            + "[" + PREFIX_EVENT_TYPE + "EVENT_TYPE] "
            + "[" + PREFIX_CLIENT_ID + "CLIENT_ID] "
            + "[" + PREFIX_PROPERTY_ID + "PROPERTY_ID] "
            + "[" + PREFIX_EVENT_NOTE + "NOTE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EVENT_START + "30-04-2025 1700 "
            + PREFIX_EVENT_TYPE + "Meeting";

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited Event: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_EVENT_CONFLICT = "Event conflicts with existing event.";
    public static final String MESSAGE_INVALID_PROPERTY_ID = "Invalid property ID.";
    public static final String MESSAGE_INVALID_CLIENT_ID = "Invalid client ID.";

    private static final Logger logger = LogsCenter.getLogger(EditEventCommand.class);

    private final EditEventDescriptor editEventDescriptor;

    /**
     * @param index of the client in the filtered client list to edit
     * @param editEventDescriptor details to edit the client with
     */
    public EditEventCommand(Index index, EditEventDescriptor editEventDescriptor) {
        super(index, editEventDescriptor);
        this.editEventDescriptor = new EditEventDescriptor(editEventDescriptor);
    }

    /**
     * Adds a command word and its associated prefixes to the command word map.
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

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToEdit = lastShownList.get(index.getZeroBased());
        Event editedEvent = createEditedEvent(eventToEdit, editEventDescriptor, model);

        if (!eventToEdit.isSameEvent(editedEvent) && model.hasEvent(editedEvent)) {
            throw new CommandException(MESSAGE_EVENT_CONFLICT);
        }

        model.setEvent(eventToEdit, editedEvent);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, Messages.formatEvent(editedEvent)));
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit}
     * edited with {@code editEventDescriptor}.
     */
    private static Event createEditedEvent(Event eventToEdit, EditEventDescriptor editEventDescriptor, Model model)
            throws CommandException {
        assert eventToEdit != null;

        ClientName updatedClientName;
        PropertyName updatedPropertyName;
        List<Property> propertyList = model.getFilteredPropertyList();
        List<Client> clientList = model.getFilteredClientList();

        Optional<Index> optionalClientId = editEventDescriptor.getClientId();
        if (optionalClientId.isPresent()) {
            Index clientId = optionalClientId.get();
            int clientIdZeroBased = clientId.getZeroBased();
            if (clientIdZeroBased >= clientList.size()) {
                logger.warning("Invalid client ID: " + clientIdZeroBased);
                throw new CommandException(MESSAGE_INVALID_CLIENT_ID);
            }
            Client client = clientList.get(clientIdZeroBased);
            updatedClientName = client.getFullName();
            logger.fine("Client ID: " + clientIdZeroBased);
        } else {
            updatedClientName = eventToEdit.getClientName();
        }

        Optional<Index> optionalPropertyId = editEventDescriptor.getPropertyId();
        if (optionalPropertyId.isPresent()) {
            Index propertyId = optionalPropertyId.get();
            int propertyIdZeroBased = propertyId.getZeroBased();
            if (propertyIdZeroBased >= propertyList.size()) {
                logger.warning("Invalid property ID: " + propertyIdZeroBased);
                throw new CommandException(MESSAGE_INVALID_PROPERTY_ID);
            }
            Property property = propertyList.get(propertyIdZeroBased);
            updatedPropertyName = property.getFullName();
            logger.fine("Property ID: " + propertyIdZeroBased);
        } else {
            updatedPropertyName = eventToEdit.getPropertyName();
        }

        EventType updatedEventType = editEventDescriptor.getEventType().orElse(eventToEdit.getEventType());
        LocalDateTime updatedDateTime = editEventDescriptor.getDateTime().orElse(eventToEdit.getDateTime());
        Note updatedNote = editEventDescriptor.getNote().orElse(eventToEdit.getNote());

        return new Event(updatedDateTime, updatedEventType, updatedClientName, updatedPropertyName, updatedNote);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditEventCommand)) {
            return false;
        }

        EditEventCommand otherEditCommand = (EditEventCommand) other;
        return index.equals(otherEditCommand.index)
                && editEventDescriptor.equals(otherEditCommand.editEventDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editEventDescriptor", editEventDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the event with. Each non-empty field value will replace the
     * corresponding field value of the event.
     */
    public static class EditEventDescriptor extends EditDescriptor<Event> {
        private EventType eventType;
        private Index propertyId;
        private Index clientId;
        private LocalDateTime dateTime;
        private Note note;

        public EditEventDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditEventDescriptor(EditEventDescriptor toCopy) {
            setDateTime(toCopy.dateTime);
            setEventType(toCopy.eventType);
            setClientId(toCopy.clientId);
            setPropertyId(toCopy.propertyId);
            setNote(toCopy.note);
        }

        /**
         * Returns true if at least one field is edited.
         */
        @Override
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(dateTime, eventType, clientId, propertyId, note);
        }

        public void setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
        }

        public Optional<LocalDateTime> getDateTime() {
            return Optional.ofNullable(dateTime);
        }

        public void setEventType(EventType eventType) {
            this.eventType = eventType;
        }

        public Optional<EventType> getEventType() {
            return Optional.ofNullable(eventType);
        }

        public void setClientId(Index clientId) {
            this.clientId = clientId;
        }

        public Optional<Index> getClientId() {
            return Optional.ofNullable(clientId);
        }

        public void setPropertyId(Index propertyId) {
            this.propertyId = propertyId;
        }

        public Optional<Index> getPropertyId() {
            return Optional.ofNullable(propertyId);
        }

        public void setNote(Note note) {
            this.note = note;
        }

        public Optional<Note> getNote() {
            return Optional.ofNullable(note);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditEventDescriptor)) {
                return false;
            }

            EditEventDescriptor otherEditEventDescriptor = (EditEventDescriptor) other;
            return Objects.equals(dateTime, otherEditEventDescriptor.dateTime)
                    && Objects.equals(eventType, otherEditEventDescriptor.eventType)
                    && Objects.equals(clientId, otherEditEventDescriptor.clientId)
                    && Objects.equals(propertyId, otherEditEventDescriptor.propertyId)
                    && Objects.equals(note, otherEditEventDescriptor.note);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("dateTime", dateTime)
                    .add("eventType", eventType)
                    .add("clientId", clientId)
                    .add("propertyId", propertyId)
                    .add("note", note)
                    .toString();
        }
    }
}
