package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Deletes an event identified using its index from the address book.
 */
public class DeleteEventCommand extends DeleteCommand<Event> {

    public static final String COMMAND_WORD = "delete_event";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the event identified by the index number used in the displayed event list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Deleted event: %s";
    public static final String MESSAGE_INVALID_EVENT = "This event index provided is invalid.";

    private static final Logger logger = LogsCenter.getLogger(DeleteEventCommand.class);

    /**
     * Constructs a {@code DeleteEventCommand} with the target index.
     *
     * @param targetIndex {@code Index} of the event in the filtered list to delete.
     */
    public DeleteEventCommand(Index targetIndex) {
        super(targetIndex);
        logger.info("DeleteEventCommand initialized with index: " + targetIndex.getZeroBased());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing DeleteEventCommand");
        List<Event> lastShownList = model.getFilteredEventList();

        int targetIndexZeroBased = targetIndex.getZeroBased();
        if (targetIndexZeroBased >= lastShownList.size()) {
            logger.warning("Invalid index: " + targetIndexZeroBased);
            throw new CommandException(MESSAGE_INVALID_EVENT);
        }
        logger.fine("Index: " + targetIndexZeroBased);

        Event eventToDelete = lastShownList.get(targetIndexZeroBased);
        model.deleteEvent(eventToDelete);
        String eventDescription = Messages.formatEvent(eventToDelete);
        logger.info("Deleted event: " + eventDescription);
        return new CommandResult(String.format(MESSAGE_SUCCESS, eventDescription));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteEventCommand otherDeleteCommand)) {
            return false;
        }

        return this.targetIndex.equals(otherDeleteCommand.targetIndex);
    }
}
