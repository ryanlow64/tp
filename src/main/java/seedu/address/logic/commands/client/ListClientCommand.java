package seedu.address.logic.commands.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CLIENTS;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ListCommand;
import seedu.address.model.Model;
import seedu.address.model.client.Client;

/**
 * Lists all clients in the address book to the user.
 */
public class ListClientCommand extends ListCommand<Client> {

    public static final String COMMAND_WORD = "list_client";

    public static final String MESSAGE_SUCCESS = "Listed all clients";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays all clients in the address book. "
            + "This command does not take additional parameters.\n"
            + "Example: " + COMMAND_WORD;

    public static void addCommandWord() {
        initialiseCommandWord(COMMAND_WORD);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredClientList(PREDICATE_SHOW_ALL_CLIENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
