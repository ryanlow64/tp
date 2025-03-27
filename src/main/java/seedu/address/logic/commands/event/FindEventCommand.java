package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ABOUT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_WITH;

import java.util.function.Predicate;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Finds and lists all events in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindEventCommand extends FindCommand<Event> {
    public static final String COMMAND_WORD = "find_event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all events with the specified event type or "
            + "client or property fullname(s) and displays them as a list with index numbers.\n"
            + "Parameters: "
            + PREFIX_EVENT_ABOUT + "ABOUT "
            + PREFIX_EVENT_TYPE + "EVENT_TYPE "
            + PREFIX_EVENT_WITH + "WITH \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_EVENT_WITH + "Alice Yeo";

    /**
     * Constructs a {@code FindEventCommand} with the given predicate.
     *
     * @param predicate Predicate used to filter the events.
     */
    public FindEventCommand(Predicate<Event> predicate) {
        super(predicate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEventList(predicate);
        return new CommandResult(String.format(
            Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, model.getFilteredEventList().size()));
    }
}
