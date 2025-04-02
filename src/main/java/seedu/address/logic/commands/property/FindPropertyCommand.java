package seedu.address.logic.commands.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEYWORDS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OWNER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE_ABOVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE_BELOW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SIZE_ABOVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SIZE_BELOW;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.property.Property;

/**
 * Finds and lists all properties in address book whose property name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindPropertyCommand extends FindCommand<Property> {

    public static final String COMMAND_WORD = "find_property";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all properties whose property name contains "
            + "any of the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " villa condominium estate";

    private final Predicate<Property> predicate;

    /**
     * Creates an FindPropertyCommand to find the specified property.
     */
    public FindPropertyCommand(Predicate<Property> predicate) {
        super(predicate);
        this.predicate = predicate;
    }

    /**
     * Adds the command word to the command word list.
     */
    public static void addCommandWord() {
        Prefix[] prefixes = {
            PREFIX_KEYWORDS,
            PREFIX_ADDRESS,
            PREFIX_PRICE_BELOW,
            PREFIX_PRICE_ABOVE,
            PREFIX_SIZE_BELOW,
            PREFIX_SIZE_ABOVE,
            PREFIX_OWNER
        };
        addCommandWord(COMMAND_WORD, prefixes);
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
