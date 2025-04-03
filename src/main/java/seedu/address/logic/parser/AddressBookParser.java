package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.util.Pair;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListAllCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.client.AddClientCommand;
import seedu.address.logic.commands.client.DeleteClientCommand;
import seedu.address.logic.commands.client.EditClientCommand;
import seedu.address.logic.commands.client.FindClientCommand;
import seedu.address.logic.commands.client.ListClientCommand;
import seedu.address.logic.commands.deal.AddDealCommand;
import seedu.address.logic.commands.deal.FindDealCommand;
import seedu.address.logic.commands.deal.ListDealCommand;
import seedu.address.logic.commands.deal.UpdateDealCommand;
import seedu.address.logic.commands.event.AddEventCommand;
import seedu.address.logic.commands.event.DeleteEventCommand;
import seedu.address.logic.commands.event.EditEventCommand;
import seedu.address.logic.commands.event.FindEventCommand;
import seedu.address.logic.commands.event.ListEventCommand;
import seedu.address.logic.commands.property.AddPropertyCommand;
import seedu.address.logic.commands.property.DeletePropertyCommand;
import seedu.address.logic.commands.property.EditPropertyCommand;
import seedu.address.logic.commands.property.FindPropertyCommand;
import seedu.address.logic.commands.property.ListPropertyCommand;
import seedu.address.logic.parser.client.AddClientCommandParser;
import seedu.address.logic.parser.client.DeleteClientCommandParser;
import seedu.address.logic.parser.client.EditClientCommandParser;
import seedu.address.logic.parser.client.FindClientCommandParser;
import seedu.address.logic.parser.deal.AddDealCommandParser;
import seedu.address.logic.parser.deal.FindDealCommandParser;
import seedu.address.logic.parser.deal.UpdateDealCommandParser;
import seedu.address.logic.parser.event.AddEventCommandParser;
import seedu.address.logic.parser.event.DeleteEventCommandParser;
import seedu.address.logic.parser.event.EditEventCommandParser;
import seedu.address.logic.parser.event.FindEventCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.property.AddPropertyCommandParser;
import seedu.address.logic.parser.property.DeletePropertyCommandParser;
import seedu.address.logic.parser.property.EditPropertyCommandParser;
import seedu.address.logic.parser.property.FindPropertyCommandParser;

/**
 * Parses user input.
 */
public class AddressBookParser {

    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform to the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        if (commandWord.startsWith(AddCommand.COMMAND_WORD)) {
            return getAddCommand(commandWord, arguments);
        } else if (commandWord.startsWith(DeleteCommand.COMMAND_WORD)) {
            return getDeleteCommand(commandWord, arguments);
        } else if (commandWord.startsWith(EditCommand.COMMAND_WORD)
            || commandWord.startsWith(UpdateDealCommand.COMMAND_WORD)
        ) {
            return getEditCommand(commandWord, arguments);
        } else if (commandWord.startsWith(FindCommand.COMMAND_WORD)) {
            return getFindCommand(commandWord, arguments);
        } else if (commandWord.startsWith(ListCommand.COMMAND_WORD)) {
            Pair<ListCommand<?>, String> listCommandPair = getListCommand(commandWord);
            if (!arguments.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    listCommandPair.getValue()));
            }
            return listCommandPair.getKey();
        } else if (commandWord.startsWith(ExitCommand.COMMAND_WORD)) {
            return new ExitCommand();
        } else if (commandWord.startsWith(HelpCommand.COMMAND_WORD)) {
            return new HelpCommand();
        } else {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    private Command getAddCommand(String commandWord, String arguments) throws ParseException {
        return switch (commandWord) {
        case AddClientCommand.COMMAND_WORD -> new AddClientCommandParser().parse(arguments);
        case AddPropertyCommand.COMMAND_WORD -> new AddPropertyCommandParser().parse(arguments);
        case AddEventCommand.COMMAND_WORD -> new AddEventCommandParser().parse(arguments);
        case AddDealCommand.COMMAND_WORD -> new AddDealCommandParser().parse(arguments);
        default -> throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        };
    }

    private DeleteCommand<?> getDeleteCommand(String commandWord, String arguments) throws ParseException {
        return switch (commandWord) {
        case DeleteClientCommand.COMMAND_WORD -> new DeleteClientCommandParser().parse(arguments);
        case DeletePropertyCommand.COMMAND_WORD -> new DeletePropertyCommandParser().parse(arguments);
        case DeleteEventCommand.COMMAND_WORD -> new DeleteEventCommandParser().parse(arguments);
        default -> throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        };
    }

    private EditCommand<?> getEditCommand(String commandWord, String arguments) throws ParseException {
        return switch (commandWord) {
        case EditClientCommand.COMMAND_WORD -> new EditClientCommandParser().parse(arguments);
        case EditPropertyCommand.COMMAND_WORD -> new EditPropertyCommandParser().parse(arguments);
        case EditEventCommand.COMMAND_WORD -> new EditEventCommandParser().parse(arguments);
        case UpdateDealCommand.COMMAND_WORD -> new UpdateDealCommandParser().parse(arguments);
        default -> throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        };
    }

    private FindCommand<?> getFindCommand(String commandWord, String arguments) throws ParseException {
        return switch (commandWord) {
        case FindClientCommand.COMMAND_WORD -> new FindClientCommandParser().parse(arguments);
        case FindPropertyCommand.COMMAND_WORD -> new FindPropertyCommandParser().parse(arguments);
        case FindEventCommand.COMMAND_WORD -> new FindEventCommandParser().parse(arguments);
        case FindDealCommand.COMMAND_WORD -> new FindDealCommandParser().parse(arguments);
        default -> throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        };
    }

    private Pair<ListCommand<?>, String> getListCommand(String commandWord) throws ParseException {
        return switch (commandWord) {
        case ListClientCommand.COMMAND_WORD -> new Pair<>(new ListClientCommand(), ListClientCommand.MESSAGE_USAGE);
        case ListPropertyCommand.COMMAND_WORD ->
            new Pair<>(new ListPropertyCommand(), ListPropertyCommand.MESSAGE_USAGE);
        case ListEventCommand.COMMAND_WORD -> new Pair<>(new ListEventCommand(), ListEventCommand.MESSAGE_USAGE);
        case ListDealCommand.COMMAND_WORD -> new Pair<>(new ListDealCommand(), ListDealCommand.MESSAGE_USAGE);
        case ListAllCommand.COMMAND_WORD -> new Pair<>(new ListAllCommand(), ListAllCommand.MESSAGE_USAGE);
        default -> throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        };
    }
}
