package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CLIENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DEALS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROPERTIES;

import seedu.address.model.Model;

/**
 * Lists everything in the address book to the user.
 */
public class ListAllCommand extends ListCommand<Object> {
    public static final String COMMAND_WORD = "list_all";

    public static final String MESSAGE_SUCCESS = "Listed everything";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays all data in the address book. "
            + "This command does not take additional parameters.\n"
            + "Example: " + COMMAND_WORD;

    public static void addCommandWord() {
        initialiseCommandWord(COMMAND_WORD);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPropertyList(PREDICATE_SHOW_ALL_PROPERTIES);
        model.updateFilteredClientList(PREDICATE_SHOW_ALL_CLIENTS);
        model.updateFilteredDealList(PREDICATE_SHOW_ALL_DEALS);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
