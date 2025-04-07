package seedu.address.logic.commands.deal;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DEALS;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ListCommand;
import seedu.address.model.Model;
import seedu.address.model.deal.Deal;

/**
 * Lists all deals in REconnect to the user.
 */
public class ListDealCommand extends ListCommand<Deal> {
    public static final String COMMAND_WORD = "list_deals";
    public static final String MESSAGE_SUCCESS = "Listed all deals";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays all deals in REconnect. "
            + "This command does not take additional parameters.\n"
            + "Example: " + COMMAND_WORD;

    private static final Logger logger = LogsCenter.getLogger(ListDealCommand.class);

    public static void addCommandWord() {
        initialiseCommandWord(COMMAND_WORD);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        logger.info("Executing ListDealCommand to show all deals");

        model.updateFilteredDealList(PREDICATE_SHOW_ALL_DEALS);
        int dealsCount = model.getFilteredDealList().size();
        logger.info("Listed " + dealsCount + " deals");

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
