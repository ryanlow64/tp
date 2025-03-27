package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showClientAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showDealAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showEventAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showPropertyAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListAllCommand.
 */
public class ListAllCommandTest extends ListCommandTest<Object> {

    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    public void execute_listIsNotFiltered_showsAllEntities() {
        assertCommandSuccess(new ListAllCommand(), model, ListAllCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showClientAtIndex(model, INDEX_FIRST);
        showPropertyAtIndex(model, INDEX_FIRST);
        showDealAtIndex(model, INDEX_FIRST);
        showEventAtIndex(model, INDEX_FIRST);

        assertCommandSuccess(new ListAllCommand(), model, ListAllCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
