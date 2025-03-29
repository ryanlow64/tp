package seedu.address.logic;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.Set;
import java.util.logging.Logger;

import org.reflections.Reflections;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.client.Client;
import seedu.address.model.deal.Deal;
import seedu.address.model.event.Event;
import seedu.address.model.property.Property;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        assert model != null : "Model cannot be null";
        assert storage != null : "Storage cannot be null";
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
        initialiseCommandWords();
    }

    /**
     * Initialises the command words for all commands in the package at runtime.
     * This method uses reflection to find all subclasses of Command and register their command words.
     */
    private void initialiseCommandWords() {
        Reflections reflections = new Reflections("seedu.address.logic.commands");
        Set<Class<? extends Command>> commandClasses = reflections.getSubTypesOf(Command.class);
        for (Class<? extends Command> commandClass : commandClasses) {
            boolean isAbstract = Modifier.isAbstract(commandClass.getModifiers());
            if (!isAbstract) {
                try {
                    Method staticMethod = commandClass.getDeclaredMethod("addCommandWord");
                    if (Modifier.isStatic(staticMethod.getModifiers())) {
                        staticMethod.invoke(null);
                    }
                } catch (NoSuchMethodException e) {
                    logger.warning("No such method addCommandWord in " + commandClass.getSimpleName());
                } catch (Exception e) {
                    logger.warning("Failed to initialise command word for " + commandClass.getSimpleName()
                        + ": " + e);
                }
            }
        }
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Client> getFilteredClientList() {
        return model.getFilteredClientList();
    }

    @Override
    public ObservableList<Deal> getFilteredDealList() {
        return model.getFilteredDealList();
    }

    @Override
    public ObservableList<Property> getFilteredPropertyList() {
        return model.getFilteredPropertyList();
    }

    @Override
    public ObservableList<Event> getFilteredEventList() {
        return model.getFilteredEventList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
