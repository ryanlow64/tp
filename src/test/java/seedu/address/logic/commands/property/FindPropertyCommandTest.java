package seedu.address.logic.commands.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PROPERTIES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalProperties.MAPLE;
import static seedu.address.testutil.TypicalProperties.ORCHID;
import static seedu.address.testutil.TypicalProperties.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.property.predicates.PropertyNameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindPropertyCommand}.
 */
public class FindPropertyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PropertyNameContainsKeywordsPredicate firstPredicate =
                new PropertyNameContainsKeywordsPredicate(Collections.singletonList("Maple"));
        PropertyNameContainsKeywordsPredicate secondPredicate =
                new PropertyNameContainsKeywordsPredicate(Collections.singletonList("Orchid"));

        FindPropertyCommand findFirstCommand = new FindPropertyCommand(firstPredicate);
        FindPropertyCommand findSecondCommand = new FindPropertyCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindPropertyCommand findFirstCommandCopy = new FindPropertyCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different property -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPropertyFound() {
        String expectedMessage = String.format(MESSAGE_PROPERTIES_LISTED_OVERVIEW, 0);
        PropertyNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindPropertyCommand command = new FindPropertyCommand(predicate);
        expectedModel.updateFilteredPropertyList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPropertyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePropertiesFound() {
        String expectedMessage = String.format(MESSAGE_PROPERTIES_LISTED_OVERVIEW, 2);
        PropertyNameContainsKeywordsPredicate predicate = preparePredicate("Maple Orchid");
        FindPropertyCommand command = new FindPropertyCommand(predicate);
        expectedModel.updateFilteredPropertyList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(MAPLE, ORCHID), model.getFilteredPropertyList());
    }

    @Test
    public void toStringMethod() {
        PropertyNameContainsKeywordsPredicate predicate = new PropertyNameContainsKeywordsPredicate(Arrays
                .asList("Maple"));
        FindPropertyCommand findPropertyCommand = new FindPropertyCommand(predicate);
        String expected = FindPropertyCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findPropertyCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code PropertyNameContainsKeywordsPredicate}.
     */
    private PropertyNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new PropertyNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
