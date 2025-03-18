package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.util.function.Predicate;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ListCommand;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Lists all events in the address book to the user.
 */
public class ListEventCommand extends ListCommand<Event> {
    public static final String COMMAND_WORD = "list_event";
    public static final String MESSAGE_SUCCESS = "Listed all events";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}