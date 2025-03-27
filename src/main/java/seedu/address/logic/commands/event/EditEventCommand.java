// package seedu.address.logic.commands.event;

// import static java.util.Objects.requireNonNull;

// import java.util.List;

// import seedu.address.commons.core.index.Index;
// import seedu.address.logic.Messages;
// import seedu.address.logic.commands.CommandResult;
// import seedu.address.logic.commands.EditCommand;
// import seedu.address.logic.commands.exceptions.CommandException;
// import seedu.address.model.Model;
// import seedu.address.model.event.Event;

// /**
//  * Deletes an event identified using its index from the address book.
//  */
// public class EditEventCommand extends EditCommand<Event> {
//     public static final String COMMAND_WORD = "edit_event";

//     public static final String MESSAGE_USAGE = new StringBuilder(COMMAND_WORD)
//             .append(" INDEX")
//             .append(System.lineSeparator())
//             .append("Example: ")
//             .append(COMMAND_WORD)
//             .append(" 42")
//             .append()
//             .toString();

//     public static final String MESSAGE_SUCCESS = "Deleted event: %s";
//     public static final String MESSAGE_INVALID_EVENT = "This event index provided is invalid.";

//     /**
//      * Constructs a {@code DeleteEventCommand} with the target index.
//      *
//      * @param targetIndex {@code Index} of the event in the filtered list to delete.
//      */
//     public DeleteEventCommand(Index targetIndex) {
//         super(targetIndex);
//     }

//     @Override
//     public CommandResult execute(Model model) throws CommandException {
//         requireNonNull(model);
//         List<Event> lastShownList = model.getFilteredEventList();

//         if (targetIndex.getZeroBased() >= lastShownList.size()) {
//             throw new CommandException(MESSAGE_INVALID_EVENT);
//         }

//         Event eventToDelete = lastShownList.get(targetIndex.getZeroBased());
//         model.deleteEvent(eventToDelete);
//         return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.formatEvent(eventToDelete)));
//     }
// }
