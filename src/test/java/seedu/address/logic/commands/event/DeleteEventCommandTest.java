package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showEventAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteCommandTest;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;

class DeleteEventCommandTest extends DeleteCommandTest<Event> {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Event eventToDelete = model.getFilteredEventList().get(INDEX_SECOND.getZeroBased());
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(INDEX_SECOND);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_SUCCESS, Messages.formatEvent(eventToDelete));
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);

        assertCommandSuccess(deleteEventCommand, model, expectedMessage, expectedModel);
        assertFalse(model.hasEvent(eventToDelete));
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(outOfBoundIndex);

        assertCommandFailure(deleteEventCommand, model, DeleteEventCommand.MESSAGE_INVALID_EVENT);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showEventAtIndex(model, INDEX_SECOND);
        Event eventToDelete = model.getFilteredEventList().get(0);
        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteEventCommand.MESSAGE_SUCCESS, Messages.formatEvent(eventToDelete));
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);
        expectedModel.updateFilteredEventList(x -> false);

        assertCommandSuccess(deleteEventCommand, model, expectedMessage, expectedModel);
        assertFalse(model.hasEvent(eventToDelete));
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showEventAtIndex(model, INDEX_SECOND);
        Index outOfBoundIndex = INDEX_SECOND;
        assertFalse(outOfBoundIndex.getZeroBased() < model.getFilteredEventList().size());
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getEventList().size());

        DeleteEventCommand deleteEventCommand = new DeleteEventCommand(outOfBoundIndex);
        assertCommandFailure(deleteEventCommand, model, DeleteEventCommand.MESSAGE_INVALID_EVENT);
    }
}
