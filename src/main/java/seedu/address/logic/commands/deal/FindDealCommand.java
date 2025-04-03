package seedu.address.logic.commands.deal;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE_ABOVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE_BELOW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.deal.Deal;

/**
 * Finds and lists all deals in address book that match the specified criteria.
 * Keyword matching is case-insensitive for name fields, and exact matching for status.
 */
public class FindDealCommand extends FindCommand<Deal> {

    public static final String COMMAND_WORD = "find_deal";

    private static final Logger logger = LogsCenter.getLogger(FindDealCommand.class);

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays all deals that match the specified criteria "
            + "for buyer, seller, property name, deal status, and/or price\n"
            + "Parameters: "
            + "[" + PREFIX_PROPERTY_NAME + "PROPERTY_NAME] "
            + "[" + PREFIX_BUYER + "BUYER_NAME] "
            + "[" + PREFIX_SELLER + "SELLER_NAME] "
            + "[" + PREFIX_STATUS + "STATUS] "
            + "[" + PREFIX_PRICE_ABOVE + "PRICE_ABOVE] "
            + "[" + PREFIX_PRICE_BELOW + "PRICE_BELOW]\n"
            + "Note: At least one parameter must be provided. The first parameter is applied unconditionally, "
            + "and if more parameters are provided, all must be "
            + "combined with the same conditional operator either 'AND' or 'OR'.\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PROPERTY_NAME + "Villa " + PREFIX_STATUS.getAndPrefix()
            + "PENDING";

    /**
     * Creates a new FindDealCommand with the given predicate.
     *
     * @param predicate The predicate to filter deals by
     */
    public FindDealCommand(Predicate<Deal> predicate) {
        super(predicate);
        logger.info("FindDealCommand initialized with predicate: " + predicate);
    }

    /**
     * Adds the command word to the command word list.
     */
    public static void addCommandWord() {
        Prefix[] prefixes = {
            PREFIX_PROPERTY_NAME,
            PREFIX_BUYER,
            PREFIX_SELLER,
            PREFIX_STATUS,
            PREFIX_PRICE_BELOW,
            PREFIX_PRICE_ABOVE
        };
        addCommandWord(COMMAND_WORD, prefixes);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        logger.info("Executing FindDealCommand");

        model.updateFilteredDealList(predicate);
        int dealsFound = model.getFilteredDealList().size();
        logger.info("Found " + dealsFound + " deals satisfying the predicate");

        return new CommandResult(String.format(Messages.MESSAGE_DEALS_LISTED_OVERVIEW, dealsFound));
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
