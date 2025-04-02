package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
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

    /**
     * Adds the command word to the command word list.
     */
    public static void addCommandWord(String commandWord, Prefix... prefixes) {
        List<Prefix> prefixList = Stream.concat(
            Stream.of(prefixes),
            Stream.concat(
                Stream.of(prefixes).filter(Prefix::canBeConditional).map(Prefix::getAndPrefix),
                Stream.of(prefixes).filter(Prefix::canBeConditional).map(Prefix::getOrPrefix))
        ).toList();

        initialiseCommandWord(commandWord, prefixList.toArray(Prefix[]::new));
    }

    public abstract CommandResult execute(Model model) throws CommandException;
}
