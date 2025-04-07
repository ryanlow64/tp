package seedu.address.logic.commands.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEYWORDS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.client.Client;

/**
 * Finds and lists all clients in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindClientCommand extends FindCommand<Client> {

    public static final String COMMAND_WORD = "find_client";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays all clients that match the specified "
            + "criteria for name, phone, email, and/or address.\n"
            + "Parameters: "
            + "[" + PREFIX_KEYWORDS + "KEYWORDS] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS]\n"
            + "Note: At least one parameter must be provided. If more than 1 parameter is provided, all must be "
            + "combined with the same connective operator either 'AND' or 'OR'.\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_KEYWORDS + "Alice " + PREFIX_PHONE.getOrPrefix()
            + "12345678";

    public FindClientCommand(Predicate<Client> predicate) {
        super(predicate);
    }

    /**
     * Adds the command word to the command word list.
     */
    public static void addCommandWord() {
        Prefix[] prefixes = {
            PREFIX_KEYWORDS,
            PREFIX_PHONE,
            PREFIX_EMAIL,
            PREFIX_ADDRESS
        };
        addCommandWord(COMMAND_WORD, prefixes);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredClientList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_CLIENTS_LISTED_OVERVIEW, model.getFilteredClientList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindClientCommand otherFindCommand)) {
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
