package seedu.address.logic.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    public static final Map<String, List<Prefix>> COMMAND_WORDS = new HashMap<>();

    protected static void initialiseCommandWord(String commandWord, Prefix... prefixes) {
        COMMAND_WORDS.put(commandWord, List.of(prefixes));
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
