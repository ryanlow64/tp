package seedu.address.logic.commands.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showClientAtIndex;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;
import static seedu.address.testutil.TypicalDeals.DEAL1;
import static seedu.address.testutil.TypicalDeals.DEAL4;
import static seedu.address.testutil.TypicalEvents.EVENT1;
import static seedu.address.testutil.TypicalEvents.EVENT4;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalProperties.MAPLE;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteCommandTest;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteClientCommand}.
 */
public class DeleteClientCommandTest extends DeleteCommandTest<Client> {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Client clientToDelete = model.getFilteredClientList().get(INDEX_FIRST.getZeroBased());
        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteClientCommand.MESSAGE_DELETE_CLIENT_SUCCESS,
            Messages.formatClient(clientToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteClient(clientToDelete);

        assertCommandSuccess(deleteClientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClientList().size() + 1);
        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(outOfBoundIndex);

        assertCommandFailure(deleteClientCommand, model, Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        Client clientToDelete = model.getFilteredClientList().get(INDEX_FIRST.getZeroBased());
        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteClientCommand.MESSAGE_DELETE_CLIENT_SUCCESS,
            Messages.formatClient(clientToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteClient(clientToDelete);

        assertCommandSuccess(deleteClientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showClientAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getClientList().size());

        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(outOfBoundIndex);

        assertCommandFailure(deleteClientCommand, model, Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_clientInClosedDeal_success() {
        model.addDeal(DEAL4);
        Client clientToDelete = model.getFilteredClientList().get(INDEX_FIRST.getZeroBased());
        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteClientCommand.MESSAGE_DELETE_CLIENT_SUCCESS,
            Messages.formatClient(clientToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteClient(clientToDelete);

        assertCommandSuccess(deleteClientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_clientInOpenDeal_failure() {
        model.addDeal(DEAL1);
        model.addClient(new seedu.address.testutil.ClientBuilder().withClientName("John Doe").build());

        Index johnIndex = Index.fromOneBased(model.getFilteredClientList().size());
        DeleteClientCommand command = new DeleteClientCommand(johnIndex);

        assertCommandFailure(command, model, DeleteClientCommand.MESSAGE_DELETE_CLIENT_ERROR);
    }

    @Test
    public void execute_clientInPastEvent_success() {
        model.addEvent(EVENT4);
        Client clientToDelete = model.getFilteredClientList().get(INDEX_FIRST.getZeroBased());
        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteClientCommand.MESSAGE_DELETE_CLIENT_SUCCESS,
            Messages.formatClient(clientToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteClient(clientToDelete);

        assertCommandSuccess(deleteClientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_clientInFutureEvent_failure() {
        model.addEvent(EVENT1);
        DeleteClientCommand command = new DeleteClientCommand(INDEX_FIRST);

        assertCommandFailure(command, model, DeleteClientCommand.MESSAGE_DELETE_CLIENT_ERROR);
    }

    @Test
    public void execute_clientIsPropertyOwner_failure() {
        model.addProperty(MAPLE);
        model.addClient(new seedu.address.testutil.ClientBuilder().withClientName("Amy Bee").build());

        Index amyIndex = Index.fromOneBased(model.getFilteredClientList().size());
        DeleteClientCommand command = new DeleteClientCommand(amyIndex);

        assertCommandFailure(command, model, DeleteClientCommand.MESSAGE_DELETE_CLIENT_ERROR);
    }

    @Test
    public void equals() {
        DeleteClientCommand deleteFirstCommand = new DeleteClientCommand(INDEX_FIRST);
        DeleteClientCommand deleteSecondCommand = new DeleteClientCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteClientCommand deleteFirstCommandCopy = new DeleteClientCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different client -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteClientCommand deleteClientCommand = new DeleteClientCommand(targetIndex);
        String expected = DeleteClientCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteClientCommand.toString());
    }
}
