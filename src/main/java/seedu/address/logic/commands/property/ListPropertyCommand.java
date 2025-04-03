package seedu.address.logic.commands.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROPERTIES;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ListCommand;
import seedu.address.model.Model;
import seedu.address.model.property.Property;

/**
 * Lists all properties in the address book to the user.
 */
public class ListPropertyCommand extends ListCommand<Property> {

    public static final String COMMAND_WORD = "list_properties";

    public static final String MESSAGE_SUCCESS = "Listed all properties";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays all properties in the address book. "
            + "This command does not take additional parameters.\n"
            + "Example: " + COMMAND_WORD;

    public static void addCommandWord() {
        initialiseCommandWord(COMMAND_WORD);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPropertyList(PREDICATE_SHOW_ALL_PROPERTIES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
