package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_ABOUT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_AFTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_BEFORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_WITH;

import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Finds and lists all events in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindEventCommand extends FindCommand<Event> {
    public static final String COMMAND_WORD = "find_event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays all events that match "
            + "the specified criteria for event type, date and time, client name, and/or property name\n"
            + "Parameters: "
            + "[" + PREFIX_EVENT_BEFORE + "BEFORE] "
            + "[" + PREFIX_EVENT_AFTER + "AFTER] "
            + "[" + PREFIX_EVENT_TYPE + "EVENT_TYPE] "
            + "[" + PREFIX_EVENT_WITH + "WITH] "
            + "[" + PREFIX_EVENT_ABOUT + "ABOUT]\n"
            + "Note: At least one parameter must be provided. The first parameter is applied unconditionally, "
            + "and if more parameters are provided, all must be "
            + "combined with the same conditional operator either 'AND' or 'OR'.\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_EVENT_WITH + "Alice Yeo " + PREFIX_EVENT_TYPE.getAndPrefix()
            + "meeting";

    private static final Logger logger = LogsCenter.getLogger(FindEventCommand.class);

    /**
     * Constructs a {@code FindEventCommand} with the given predicate.
     *
     * @param predicate Predicate used to filter the events.
     */
    public FindEventCommand(Predicate<Event> predicate) {
        super(predicate);
        logger.info("FindEventCommand initialized with predicate: " + predicate);
    }

    /**
     * Adds the command word to the command word list.
     */
    public static void addCommandWord() {
        Prefix[] prefixes = {
            PREFIX_EVENT_BEFORE,
            PREFIX_EVENT_AFTER,
            PREFIX_EVENT_TYPE,
            PREFIX_EVENT_WITH,
            PREFIX_EVENT_ABOUT,
        };
        addCommandWord(COMMAND_WORD, prefixes);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        logger.info("Executing FindEventCommand");

        model.updateFilteredEventList(predicate);
        int eventsFound = model.getFilteredEventList().size();
        logger.info("Found " + eventsFound + " events satisfying the predicate");
        return new CommandResult(String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, eventsFound));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindEventCommand otherFindCommand)) {
            return false;
        }

        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
