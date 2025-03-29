package seedu.address.ui;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private static final Map<String, Set<String>> AVAILABLE_COMMANDS = Command.COMMAND_WORDS;
    private final CommandExecutor commandExecutor;

    // Sample list of available commands for suggestions.

    // ContextMenu to display suggestions.
    private ContextMenu suggestionsPopup = new ContextMenu();

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() and updates suggestions whenever there is a change.
        commandTextField.textProperty().addListener((unused1, unused2, newText) -> {
            setStyleToDefault();
            showSuggestions(newText);
        });
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        suggestionsPopup.hide();
        try {
            commandExecutor.execute(commandText);
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();
        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }
        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Filters available commands based on the given input and shows them as suggestions.
     */
    private void showSuggestions(String userInput) {
        // Hide popup if the filter is empty.
        if (userInput.isEmpty()) {
            suggestionsPopup.hide();
            return;
        }

        String[] words = userInput.split(" ");
        String userCommand = words[0];
        String lastWord = words[words.length - 1];

        boolean isFullCommandPresent = AVAILABLE_COMMANDS.keySet().stream()
                .anyMatch(userCommand::startsWith);

        List<MenuItem> suggestions = !isFullCommandPresent
            ? getCommandSuggestions(userInput)
            : getPrefixSuggestions(userInput, userCommand, lastWord);

        // If there are suggestions, show the popup.
        if (!suggestions.isEmpty()) {
            suggestionsPopup.getItems().setAll(suggestions);
            if (!suggestionsPopup.isShowing()) {
                suggestionsPopup.show(commandTextField, Side.BOTTOM, 0, 0);
            }
        } else {
            suggestionsPopup.hide();
        }
    }

    /**
     * Returns a list of command suggestions based on the user input.
     *
     * @param userInput The user input to filter command suggestions.
     * @return A list of command suggestions.
     */
    private List<MenuItem> getCommandSuggestions(String userInput) {
        return AVAILABLE_COMMANDS.keySet().stream()
            .filter(cmd -> cmd.startsWith(userInput))
            .map(cmd -> {
                MenuItem item = new MenuItem(cmd.replace("_", "\u2017"));
                item.setOnAction(event -> {
                    commandTextField.setText(cmd);
                    commandTextField.positionCaret(cmd.length());
                    suggestionsPopup.hide();
                });
                return item;
            })
            .collect(Collectors.toList());
    }

    /**
     * Returns a list of prefix suggestions based on the user input and command.
     *
     * @param userInput The user input to filter prefix suggestions.
     * @param userCommand The command to filter prefix suggestions.
     * @param lastWord The last word of the user input.
     * @return A list of prefix suggestions.
     */
    private List<MenuItem> getPrefixSuggestions(String userInput, String userCommand, String lastWord) {
        Set<String> prefixes = AVAILABLE_COMMANDS.get(userCommand);
        if (prefixes == null || prefixes.isEmpty()) {
            return List.of();
        }
        return prefixes.stream()
            .filter(prefix -> prefix.startsWith(lastWord) && !userInput.contains(prefix))
            .map(prefix -> {
                MenuItem item = new MenuItem(prefix);
                item.setOnAction(event -> {
                    commandTextField.setText(userInput.substring(0, userInput.length() - lastWord.length())
                        + prefix);
                    commandTextField.positionCaret(commandTextField.getText().length());
                    suggestionsPopup.hide();
                });
                return item;
            })
            .collect(Collectors.toList());
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
