package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_EVENTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalProperties.getTypicalAddressBook;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;

public class FindEventCommandTest {

    // Removed dummy classes and ModelStub.

    /**
     * Parses {@code userInput} into a {@code Predicate<Event>}.
     */
    private Predicate<Event> preparePredicate(String userInput) {
        return event -> event.toString().toLowerCase().contains(userInput.toLowerCase());
    }

    @Test
    public void equals() {
        Predicate<Event> firstPredicate = preparePredicate("alpha");
        Predicate<Event> secondPredicate = preparePredicate("beta");

        FindEventCommand findFirstCommand = new FindEventCommand(firstPredicate);
        FindEventCommand findSecondCommand = new FindEventCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different predicate -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroEventsFound() throws Exception {
        String expectedMessage = MessageFormat.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        Predicate<Event> predicate = preparePredicate("gamma");
        FindEventCommand command = new FindEventCommand(predicate);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.updateFilteredEventList(predicate);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }
}
