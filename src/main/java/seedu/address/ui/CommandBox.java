package seedu.address.ui;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.util.Pair;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private static final Map<String, List<Prefix>> AVAILABLE_COMMANDS = Command.COMMAND_WORDS;
    private final CommandExecutor commandExecutor;

    // Context menus for suggestions and command history.
    private ContextMenu suggestionsPopup = new ContextMenu();
    private ContextMenu historyPopup = new MaxSizedContextMenu();

    // List to store command history.
    private List<Pair<String, Boolean>> commandHistory = new ArrayList<>();

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
        // Listen to caret position to show command history if at position 0.
        commandTextField.caretPositionProperty().addListener((obs, oldPos, newPos) -> {
            if (newPos.intValue() == 0) {
                showHistory();
            } else {
                historyPopup.hide();
            }
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
            // Add to command history.
            commandHistory.add(new Pair<>(commandText, false));
            commandExecutor.execute(commandText);
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
            Pair<String, Boolean> lastCommand = commandHistory.get(commandHistory.size() - 1);
            commandHistory.set(commandHistory.size() - 1, new Pair<>(commandText, true));
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
        // Modified to not show suggestions when caret is at position 0.
        if (commandTextField.getCaretPosition() == 0) {
            suggestionsPopup.hide();
            return;
        }

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
                suggestionsPopup.show(commandTextField, Side.BOTTOM,
                    commandTextField.getText().length() * 6.5, 0);
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
        List<Prefix> prefixes = AVAILABLE_COMMANDS.get(userCommand);
        if (prefixes == null || prefixes.isEmpty()) {
            return List.of();
        }
        return prefixes.stream()
            .map(Prefix::getPrefix)
            .filter(prefix -> prefix.startsWith(lastWord) && !userInput.contains(prefix))
            .map(prefix -> {
                MenuItem item = new MenuItem(prefix.replace("_", "\u2017"));
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
     * Shows the command history if caret is at position 0.
     */
    private void showHistory() {
        if (commandHistory.isEmpty()) {
            historyPopup.hide();
            return;
        }
        List<MenuItem> commandList = new ArrayList<>(commandHistory.stream().map(pair -> {
            String command = pair.getKey();
            boolean isError = pair.getValue();
            MenuItem item = new MenuItem(command.replace("_", "\u2017"));
            item.setStyle("-fx-background-color: #f0f0f0;");
            if (isError) {
                item.setStyle(item.getStyle() + "-fx-text-fill: red;");
            }
            item.setOnAction(event -> {
                commandTextField.setText(command);
                commandTextField.positionCaret(command.length());
                historyPopup.hide();
            });
            return item;
        }).toList());
        Collections.reverse(commandList);
        // Display at most 4 items at a time
        historyPopup.setMaxHeight(Math.min(4, commandHistory.size()) * commandTextField.getHeight() * 1.2);
        if (!historyPopup.isShowing()) {
            historyPopup.getItems().clear();
            historyPopup.getItems().add(commandList.get(0));
            historyPopup.show(commandTextField, Side.BOTTOM, 0, 0);
            historyPopup.getItems().addAll(commandList.stream().skip(1).toList());
        }
    }

    /**
     * Custom context menu that allows for a maximum height.
     */
    private static class MaxSizedContextMenu extends ContextMenu {
        public MaxSizedContextMenu() {
            addEventHandler(Menu.ON_SHOWING, e -> {
                Node content = getSkin().getNode();
                if (content instanceof Region) {
                    Region region = (Region) content;
                    region.setMaxHeight(getMaxHeight());
                }
            });
        }
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

