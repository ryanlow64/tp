package seedu.address.logic.commands.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLIENT_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showClientAtIndex;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.EditCommandTest;
import seedu.address.logic.commands.client.EditClientCommand.EditClientDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;
import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.EditClientDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditClientCommandTest extends EditCommandTest<Client> {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Client editedClient = new ClientBuilder().build();
        EditClientDescriptor descriptor = new EditClientDescriptorBuilder(editedClient).build();
        EditClientCommand editCommand = new EditClientCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditClientCommand.MESSAGE_EDIT_CLIENT_SUCCESS,
            Messages.formatClient(editedClient));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setClient(model.getFilteredClientList().get(0), editedClient);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastClient = Index.fromOneBased(model.getFilteredClientList().size());
        Client lastClient = model.getFilteredClientList().get(indexLastClient.getZeroBased());

        ClientBuilder clientInList = new ClientBuilder(lastClient);
        Client editedClient = clientInList.withClientName(VALID_CLIENT_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .build();

        EditClientDescriptor descriptor = new EditClientDescriptorBuilder().withClientName(VALID_CLIENT_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).build();
        EditClientCommand editCommand = new EditClientCommand(indexLastClient, descriptor);

        String expectedMessage = String.format(EditClientCommand.MESSAGE_EDIT_CLIENT_SUCCESS,
            Messages.formatClient(editedClient));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setClient(lastClient, editedClient);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditClientCommand editCommand = new EditClientCommand(INDEX_FIRST, new EditClientDescriptor());
        Client editedClient = model.getFilteredClientList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditClientCommand.MESSAGE_EDIT_CLIENT_SUCCESS,
            Messages.formatClient(editedClient));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showClientAtIndex(model, INDEX_FIRST);

        Client clientInFilteredList = model.getFilteredClientList().get(INDEX_FIRST.getZeroBased());
        Client editedClient = new ClientBuilder(clientInFilteredList).withClientName(VALID_CLIENT_NAME_BOB).build();
        EditClientCommand editCommand = new EditClientCommand(INDEX_FIRST,
            new EditClientDescriptorBuilder().withClientName(VALID_CLIENT_NAME_BOB).build());

        String expectedMessage = String.format(EditClientCommand.MESSAGE_EDIT_CLIENT_SUCCESS,
            Messages.formatClient(editedClient));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setClient(model.getFilteredClientList().get(0), editedClient);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateClientUnfilteredList_failure() {
        Client firstClient = model.getFilteredClientList().get(INDEX_FIRST.getZeroBased());
        EditClientDescriptor descriptor = new EditClientDescriptorBuilder(firstClient).build();
        EditClientCommand editCommand = new EditClientCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editCommand, model, EditClientCommand.MESSAGE_DUPLICATE_CLIENT);
    }

    @Test
    public void execute_duplicateClientFilteredList_failure() {
        showClientAtIndex(model, INDEX_FIRST);

        // edit client in filtered list into a duplicate in address book
        Client clientInList = model.getAddressBook().getClientList().get(INDEX_SECOND.getZeroBased());
        EditClientCommand editCommand = new EditClientCommand(INDEX_FIRST,
            new EditClientDescriptorBuilder(clientInList).build());

        assertCommandFailure(editCommand, model, EditClientCommand.MESSAGE_DUPLICATE_CLIENT);
    }

    @Test
    public void execute_invalidClientIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredClientList().size() + 1);
        EditClientDescriptor descriptor = new EditClientDescriptorBuilder().withClientName(VALID_CLIENT_NAME_BOB)
            .build();
        EditClientCommand editCommand = new EditClientCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidClientIndexFilteredList_failure() {
        showClientAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getClientList().size());

        EditClientCommand editCommand = new EditClientCommand(outOfBoundIndex,
            new EditClientDescriptorBuilder().withClientName(VALID_CLIENT_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditClientCommand standardCommand = new EditClientCommand(INDEX_FIRST, DESC_AMY);

        // same values -> returns true
        EditClientDescriptor copyDescriptor = new EditClientDescriptor(DESC_AMY);
        EditClientCommand commandWithSameValues = new EditClientCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditClientCommand(INDEX_SECOND, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditClientCommand(INDEX_FIRST, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditClientDescriptor editClientDescriptor = new EditClientDescriptor();
        EditClientCommand editCommand = new EditClientCommand(index, editClientDescriptor);
        String expected = EditClientCommand.class.getCanonicalName() + "{index=" + index + ", editClientDescriptor="
            + editClientDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
