package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.Messages.MESSAGE_EVENTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalEvents.EVENT2;
import static seedu.address.testutil.TypicalEvents.EVENT3;
import static seedu.address.testutil.TypicalEvents.EVENT4;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.ClientName;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventType;
import seedu.address.model.event.predicates.EventAboutPropertyPredicate;
import seedu.address.model.event.predicates.EventOfTypePredicate;
import seedu.address.model.event.predicates.EventWithClientPredicate;
import seedu.address.model.property.PropertyName;

public class FindEventCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        EventOfTypePredicate first = new EventOfTypePredicate(EventType.MEETING);
        EventOfTypePredicate second = new EventOfTypePredicate(EventType.OTHERS);

        FindEventCommand firstCmd = new FindEventCommand(first);
        FindEventCommand secondCmd = new FindEventCommand(second);

        assertEquals(firstCmd, firstCmd);
        assertEquals(firstCmd, new FindEventCommand(first));
        assertNotEquals(null, firstCmd);
        assertNotEquals(firstCmd, secondCmd);
    }

    @Test
    public void execute_zeroKeywords_noEventsFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        EventAboutPropertyPredicate predicate = new EventAboutPropertyPredicate(new PropertyName("BLANK"));
        FindEventCommand command = new FindEventCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEventList());
    }

    @Test
    public void execute_findByClient_success() {
        String expectedMsg = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1);
        EventWithClientPredicate predicate = new EventWithClientPredicate(new ClientName("arnold"));
        FindEventCommand command = new FindEventCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMsg, expectedModel);
        assertEquals(List.of(EVENT2), model.getFilteredEventList());
    }

    @Test
    public void execute_findByProperty_success() {
        String expectedMsg = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 2);
        EventAboutPropertyPredicate predicate = new EventAboutPropertyPredicate(new PropertyName("hdb"));
        FindEventCommand command = new FindEventCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMsg, expectedModel);
        assertEquals(Arrays.asList(EVENT3, EVENT2), model.getFilteredEventList());
    }

    @Test
    public void execute_findByType_success() {
        String expectedMsg = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 2);
        EventOfTypePredicate predicate = new EventOfTypePredicate(EventType.OTHERS);
        FindEventCommand command = new FindEventCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMsg, expectedModel);
        assertEquals(List.of(EVENT3, EVENT4), model.getFilteredEventList());
    }

    @Test
    public void execute_multiplePredicate_success() {
        String expectedMsg = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1);
        Predicate<Event> predicate = new EventAboutPropertyPredicate(new PropertyName("hdb"))
                .and(new EventWithClientPredicate(new ClientName("arnold")))
                .and(new EventOfTypePredicate(EventType.MEETING));
        FindEventCommand command = new FindEventCommand(predicate);
        expectedModel.updateFilteredEventList(predicate);
        assertCommandSuccess(command, model, expectedMsg, expectedModel);
        assertEquals(List.of(EVENT2), model.getFilteredEventList());
    }

    @Test
    public void toStringMethod() {
        Predicate<Event> predicate = new EventOfTypePredicate(EventType.OTHERS);
        FindEventCommand command = new FindEventCommand(predicate);
        String expected = FindEventCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, command.toString());
    }
}
