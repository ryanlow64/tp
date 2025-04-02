package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalClients.ALICE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;
import static seedu.address.testutil.TypicalProperties.MAPLE;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommandTest;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ModelStub;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.client.Client;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventType;
import seedu.address.model.event.Note;
import seedu.address.model.property.Property;

class AddEventCommandTest extends AddCommandTest<Event> {
    private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2050, 4, 30, 17, 42);
    private static final Note NIL_NOTE = new Note("nil");

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new AddEventCommand(null, null, null, null, null));
    }

    @Test
    public void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        modelStub.addClient(ALICE);
        modelStub.addProperty(MAPLE);

        Event validEvent = new Event(
                EventType.MEETING, MAPLE.getFullName(), ALICE.getFullName(), LOCAL_DATE_TIME, NIL_NOTE);

        CommandResult commandResult = new AddEventCommand(
                EventType.MEETING, INDEX_FIRST, INDEX_FIRST, LOCAL_DATE_TIME, NIL_NOTE)
                .execute(modelStub);

        assertEquals(1, modelStub.events.size());
        assertTrue(modelStub.events.get(0).isSameEvent(validEvent));
        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, Messages.formatEvent(validEvent)),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_conflictingEvent_throwsCommandException() {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        modelStub.addClient(ALICE);
        modelStub.addProperty(MAPLE);
        AddEventCommand addEventCommand = new AddEventCommand(
                EventType.MEETING, INDEX_FIRST, INDEX_FIRST, LOCAL_DATE_TIME, NIL_NOTE);

        assertDoesNotThrow(() -> addEventCommand.execute(modelStub));
        assertEquals(1, modelStub.events.size());

        CommandException exception = assertThrows(CommandException.class, () -> addEventCommand.execute(modelStub));
        assertEquals(AddEventCommand.MESSAGE_EVENT_CONFLICT, exception.getMessage());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        modelStub.addClient(ALICE);
        modelStub.addProperty(MAPLE);
        AddEventCommand addEventCommand1 = new AddEventCommand(
                EventType.MEETING, INDEX_SECOND, INDEX_FIRST, LOCAL_DATE_TIME, NIL_NOTE);
        AddEventCommand addEventCommand2 = new AddEventCommand(
                EventType.MEETING, INDEX_FIRST, INDEX_THIRD, LOCAL_DATE_TIME, NIL_NOTE);

        assertThrows(CommandException.class, () -> addEventCommand1.execute(modelStub));
        CommandException exception = assertThrows(CommandException.class, () -> addEventCommand2.execute(modelStub));
        assertEquals(AddEventCommand.MESSAGE_INVALID_CLIENT_ID, exception.getMessage());
        assertEquals(0, modelStub.events.size());
    }

    @Test
    public void execute_eventInPast_throwsCommandException() {
        ModelStubAcceptingEventAdded modelStub = new ModelStubAcceptingEventAdded();
        modelStub.addClient(ALICE);
        modelStub.addProperty(MAPLE);
        AddEventCommand addEventCommand = new AddEventCommand(
                EventType.MEETING, INDEX_FIRST, INDEX_FIRST, LocalDateTime.of(1889, 4, 20, 17, 42), NIL_NOTE);

        CommandException exception = assertThrows(CommandException.class, () -> addEventCommand.execute(modelStub));
        assertEquals(AddEventCommand.MESSAGE_EVENT_IN_PAST, exception.getMessage());
        assertEquals(0, modelStub.events.size());
    }

    /**
     * A Model stub that accepts events.
     * Also contains clients and properties for event fields.
     */
    private class ModelStubAcceptingEventAdded extends ModelStub {
        final ArrayList<Event> events = new ArrayList<>();
        final ArrayList<Client> clients = new ArrayList<>();
        final ArrayList<Property> properties = new ArrayList<>();

        @Override
        public boolean hasEvent(Event event) {
            requireNonNull(event);
            return events.stream().anyMatch(event::isSameEvent);
        }

        @Override
        public void addEvent(Event event) {
            requireNonNull(event);
            events.add(event);
        }

        @Override
        public void addClient(Client client) {
            requireNonNull(client);
            clients.add(client);
        }

        @Override
        public void addProperty(Property property) {
            requireNonNull(property);
            properties.add(property);
        }
        @Override
        public ObservableList<Event> getFilteredEventList() {
            return FXCollections.observableArrayList(events);
        }

        @Override
        public ObservableList<Client> getFilteredClientList() {
            return FXCollections.observableArrayList(clients);
        }

        @Override
        public ObservableList<Property> getFilteredPropertyList() {
            return FXCollections.observableArrayList(properties);
        }
    }
}
