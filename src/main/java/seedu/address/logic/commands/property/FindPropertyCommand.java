package seedu.address.logic.commands.property;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.Model;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyNameContainsKeywordsPredicate;


/**
 * Finds and lists all properties in address book whose property name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindPropertyCommand extends FindCommand<Property> {

    public static final String COMMAND_WORD = "find_property";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all properties whose property name contains "
            + "any of the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " villa condominium estate";

    private final PropertyNameContainsKeywordsPredicate predicate;

    /**
     * Creates an FindPropertyCommand to find the specified property.
     */
    public FindPropertyCommand(PropertyNameContainsKeywordsPredicate predicate) {
        super(predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPropertyList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PROPERTIES_LISTED_OVERVIEW, model.getFilteredPropertyList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindPropertyCommand)) {
            return false;
        }

        FindPropertyCommand otherFindCommand = (FindPropertyCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
