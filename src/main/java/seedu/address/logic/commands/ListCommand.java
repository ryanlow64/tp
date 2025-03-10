package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Lists all clients in the address book to the user.
 */
public abstract class ListCommand<T> extends Command {

    public static final String COMMAND_WORD = "list";

    public abstract CommandResult execute(Model model);
}
