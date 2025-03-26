package seedu.address.logic.commands.deal;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DEALS;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ListCommand;
import seedu.address.model.Model;
import seedu.address.model.deal.Deal;

/**
 * Lists all deals in the address book to the user.
 */
public class ListDealCommand extends ListCommand<Deal> {
    public static final String COMMAND_WORD = "list_deal";
    public static final String MESSAGE_SUCCESS = "Listed all deals";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredDealList(PREDICATE_SHOW_ALL_DEALS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
} 