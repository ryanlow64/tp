package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ListCommand;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Lists all events in the address book to the user.
 */
public class ListEventCommand extends ListCommand<Event> {
    public static final String COMMAND_WORD = "list_events";
    public static final String MESSAGE_SUCCESS = "Listed all events";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays all events in the address book. "
            + "This command does not take additional parameters.\n"
            + "Example: " + COMMAND_WORD;

    private static final Logger logger = LogsCenter.getLogger(ListEventCommand.class);

    public static void addCommandWord() {
        initialiseCommandWord(COMMAND_WORD);
    }

    /**
     * Executes the {@code ListEventCommand} and shows all events in the list, removing the find filter (if any).
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        logger.info("Executing ListEventCommand");
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        logger.info("Listed all events");
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
