package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.NameContainsKeywordsPredicate;

/**
 * Finds and lists all clients in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public abstract class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    protected final NameContainsKeywordsPredicate predicate;

    /**
     * Creates an FindCommand to find the specified item.
     */
    public FindCommand(NameContainsKeywordsPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    public abstract CommandResult execute(Model model) throws CommandException;
}
