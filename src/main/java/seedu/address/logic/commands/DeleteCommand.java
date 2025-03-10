package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Generic class for deleting an item in the address book.
 */
public abstract class DeleteCommand<T> extends Command {

    public static final String COMMAND_WORD = "delete";

    protected final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Deletes the item identified by the index number used in the displayed client list.
     */
    public abstract CommandResult execute(Model model) throws CommandException;
}
