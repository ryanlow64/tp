package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Generic class for adding items to the address book.
 */
public abstract class AddCommand<T> extends Command {

    public static final String COMMAND_WORD = "add";

    protected final T toAdd;

    /**
     * Creates an AddCommand to add the specified item.
     */
    public AddCommand(T item) {
        requireNonNull(item);
        toAdd = item;
    }

    public abstract CommandResult execute(Model model) throws CommandException;
}
