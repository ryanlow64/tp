package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Finds and lists all clients in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public abstract class FindCommand<T> extends Command {

    public static final String COMMAND_WORD = "find";

    protected final Predicate<T> predicate;

    /**
     * Creates an FindCommand to find the specified item.
     */
    public FindCommand(Predicate<T> predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    public abstract CommandResult execute(Model model) throws CommandException;
}
