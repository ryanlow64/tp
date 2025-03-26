package seedu.address.logic.commands.deal;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.Model;
import seedu.address.model.deal.Deal;

/**
 * Finds and lists all deals in address book that match the specified criteria.
 * Keyword matching is case-insensitive for name fields, and exact matching for status.
 */
public class FindDealCommand extends FindCommand<Deal> {

    public static final String COMMAND_WORD = "find_deal";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all deals that match the specified criteria "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: "
            + "[" + PREFIX_PROPERTY_NAME + "PROPERTY_NAME] "
            + "[" + PREFIX_BUYER + "BUYER_NAME] "
            + "[" + PREFIX_SELLER + "SELLER_NAME] "
            + "[" + PREFIX_STATUS + "STATUS]\n"
            + "Example: " + COMMAND_WORD + " " 
            + PREFIX_PROPERTY_NAME + "Villa " 
            + PREFIX_STATUS + "PENDING";

    /**
     * Creates a new FindDealCommand with the given predicate.
     *
     * @param predicate The predicate to filter deals by
     */
    public FindDealCommand(Predicate<Deal> predicate) {
        super(predicate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredDealList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_DEALS_LISTED_OVERVIEW, model.getFilteredDealList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindDealCommand otherFindCommand)) {
            return false;
        }

        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
} 