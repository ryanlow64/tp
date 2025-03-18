package seedu.address.logic.commands.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MAPLE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ORCHID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROPERTY_NAME_ORCHID;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROPERTY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PROPERTY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.EditCommandTest;
import seedu.address.logic.commands.property.EditPropertyCommand.EditPropertyDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.property.Property;
import seedu.address.testutil.EditPropertyDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditPropertyCommandTest extends EditCommandTest<Property> {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidPropertyIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPropertyList().size() + 1);
        EditPropertyDescriptor descriptor = new EditPropertyDescriptorBuilder()
                .withPropertyName(VALID_PROPERTY_NAME_ORCHID).build();
        EditPropertyCommand editCommand = new EditPropertyCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditPropertyCommand standardCommand = new EditPropertyCommand(INDEX_FIRST_PROPERTY, DESC_MAPLE);

        // same values -> returns true
        EditPropertyDescriptor copyDescriptor = new EditPropertyDescriptor(DESC_MAPLE);
        EditPropertyCommand commandWithSameValues = new EditPropertyCommand(INDEX_FIRST_PROPERTY, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditPropertyCommand(INDEX_SECOND_PROPERTY, DESC_MAPLE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditPropertyCommand(INDEX_FIRST_PROPERTY, DESC_ORCHID)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditPropertyDescriptor editPropertyDescriptor = new EditPropertyDescriptor();
        EditPropertyCommand editCommand = new EditPropertyCommand(index, editPropertyDescriptor);
        String expected = EditPropertyCommand.class.getCanonicalName() + "{index=" + index + ", editPropertyDescriptor="
            + editPropertyDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
