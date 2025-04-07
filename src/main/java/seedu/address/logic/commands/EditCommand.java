package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Generic class for editing an item in REconnect.
 */
public abstract class EditCommand<T> extends Command {

    public static final String COMMAND_WORD = "edit";

    protected final Index index;
    protected final EditDescriptor<T> editDescriptor;

    /**
     * Creates an EditCommand to edit the specified item at {@code index}.
     */
    public EditCommand(Index index, EditDescriptor<T> editDescriptor) {
        requireNonNull(index);
        requireNonNull(editDescriptor);

        this.index = index;
        this.editDescriptor = editDescriptor;
    }

    public abstract CommandResult execute(Model model) throws CommandException;
}
