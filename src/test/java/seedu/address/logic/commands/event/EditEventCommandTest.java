package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.EditCommandTest;
import seedu.address.logic.commands.event.EditEventCommand.EditEventDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.ClientName;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventType;
import seedu.address.model.event.Note;
import seedu.address.model.property.PropertyName;

public class EditEventCommandTest extends EditCommandTest<Event> {

    private static final String EVENT_NOTE = "Meet At Cafe";
    private static final String EVENT_TYPE = "Meeting";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        EditEventDescriptor descriptor = new EditEventDescriptor();

        descriptor.setEventType(EventType.valueOf(EVENT_TYPE.toUpperCase()));
        descriptor.setClientId(Index.fromOneBased(1));
        descriptor.setPropertyId(Index.fromOneBased(1));
        descriptor.setDateTime(LocalDateTime.of(2025, 6, 6, 13, 0));
        descriptor.setNote(new Note(EVENT_NOTE));

        EditEventCommand editCommand = new EditEventCommand(INDEX_FIRST, descriptor);
        Event editedEvent = new Event(LocalDateTime.of(2025, 6, 6, 13, 0),
                EventType.valueOf(EVENT_TYPE.toUpperCase()),
                new ClientName("Alice Pauline"),
                new PropertyName("Maple Villa Condominium"),
                new Note(EVENT_NOTE));

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS,
                Messages.formatEvent(editedEvent));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setEvent(model.getFilteredEventList().get(0), editedEvent);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Event eventToEdit = model.getFilteredEventList().get(INDEX_FIRST.getZeroBased());

        Event editedEvent = new Event(eventToEdit.getDateTime(),
                EventType.valueOf(EVENT_TYPE.toUpperCase()),
                eventToEdit.getClientName(),
                eventToEdit.getPropertyName(),
                new Note(EVENT_NOTE));

        EditEventDescriptor descriptor = new EditEventDescriptor();
        descriptor.setEventType(EventType.valueOf(EVENT_TYPE.toUpperCase()));
        descriptor.setNote(new Note(EVENT_NOTE));

        EditEventCommand editCommand = new EditEventCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS,
                Messages.formatEvent(editedEvent));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setEvent(eventToEdit, editedEvent);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidEventIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        EditEventDescriptor descriptor = new EditEventDescriptor();
        descriptor.setEventType(EventType.valueOf(EVENT_TYPE.toUpperCase()));
        EditEventCommand editCommand = new EditEventCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        EditEventDescriptor descriptor = new EditEventDescriptor();
        descriptor.setEventType(EventType.MEETING);
        final EditEventCommand standardCommand = new EditEventCommand(INDEX_FIRST, descriptor);

        // same values -> returns true
        EditEventDescriptor copyDescriptor = new EditEventDescriptor();
        copyDescriptor.setEventType(EventType.MEETING);
        EditEventCommand commandWithSameValues = new EditEventCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditEventCommand(INDEX_SECOND, copyDescriptor)));

        // different descriptor -> returns false
        EditEventDescriptor differentDescriptor = new EditEventDescriptor();
        differentDescriptor.setEventType(EventType.CONFERENCE);
        assertFalse(standardCommand.equals(new EditEventCommand(INDEX_FIRST, differentDescriptor)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditEventDescriptor editEventDescriptor = new EditEventDescriptor();
        EditEventCommand editCommand = new EditEventCommand(index, editEventDescriptor);
        String expected = EditEventCommand.class.getCanonicalName() + "{index=" + index + ", editEventDescriptor="
                + editEventDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }
}
