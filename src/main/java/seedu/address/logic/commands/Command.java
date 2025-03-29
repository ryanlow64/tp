package seedu.address.logic.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    public static final Map<String, Set<String>> COMMAND_WORDS = new HashMap<>();

    protected static void initialiseCommandWord(String commandWord, Prefix... prefixes) {
        Set<String> prefixSet = Set.of(prefixes).stream()
                .map(Prefix::getPrefix)
                .collect(Collectors.toSet());

        assert !COMMAND_WORDS.containsKey(commandWord) : "Command word should not exist in the map";
        COMMAND_WORDS.put(commandWord, prefixSet);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

}
