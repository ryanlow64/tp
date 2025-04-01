package seedu.address.logic.commands.deal;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDeals.getTypicalAddressBook;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCommandTest;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.deal.Deal;
import seedu.address.model.deal.predicates.DealPropertyNameContainsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code ListDealCommand}.
 */
public class ListDealCommandTest extends ListCommandTest<Deal> {

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListDealCommand(), model, ListDealCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        // Filter the list first
        DealPropertyNameContainsPredicate predicate =
                new DealPropertyNameContainsPredicate(Collections.singletonList("Villa"));
        model.updateFilteredDealList(predicate);

        // Execute list command - should show all deals again
        assertCommandSuccess(new ListDealCommand(), model, ListDealCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyAddressBook_successWithEmptyList() {
        // Use a new, empty address book
        Model emptyModel = new ModelManager();
        Model expectedEmptyModel = new ModelManager();

        assertCommandSuccess(new ListDealCommand(), emptyModel,
                ListDealCommand.MESSAGE_SUCCESS, expectedEmptyModel);
    }
}
