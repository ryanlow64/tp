package seedu.address.logic.commands.property;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProperties.MAPLE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddCommandTest;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.property.Property;
import seedu.address.testutil.PropertyBuilder;

public class AddPropertyCommandTest extends AddCommandTest<Property> {

    @Test
    public void constructor_nullProperty_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPropertyCommand(null));
    }

    @Test
    public void execute_propertyAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPropertyAdded modelStub = new ModelStubAcceptingPropertyAdded();
        Property validProperty = new PropertyBuilder().build();

        CommandResult commandResult = new AddPropertyCommand(validProperty).execute(modelStub);

        assertEquals(String.format(AddPropertyCommand.MESSAGE_SUCCESS, Messages.format(validProperty)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validProperty), modelStub.propertiesAdded);
    }

    @Test
    public void execute_duplicateProperty_throwsCommandException() {
        Property validProperty = new PropertyBuilder().build();
        AddCommand addCommand = new AddPropertyCommand(validProperty);
        ModelStub modelStub = new ModelStubWithProperty(validProperty);

        assertThrows(CommandException.class, AddPropertyCommand.MESSAGE_DUPLICATE_PROPERTY, ()
            -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Property maple = new PropertyBuilder().withPropertyName("Maple").build();
        Property orchid = new PropertyBuilder().withPropertyName("Orchid").build();
        AddCommand<Property> addMapleCommand = new AddPropertyCommand(maple);
        AddCommand<Property> addOrchidCommand = new AddPropertyCommand(orchid);

        // same object -> returns true
        assertTrue(addMapleCommand.equals(addMapleCommand));

        // same values -> returns true
        AddCommand addMapleCommandCopy = new AddPropertyCommand(maple);
        assertTrue(addMapleCommand.equals(addMapleCommandCopy));

        // different types -> returns false
        assertFalse(addMapleCommand.equals(1));

        // null -> returns false
        assertFalse(addMapleCommand.equals(null));

        // different property -> returns false
        assertFalse(addMapleCommand.equals(addOrchidCommand));
    }

    @Test
    public void toStringMethod() {
        AddPropertyCommand addPropertyCommand = new AddPropertyCommand(MAPLE);
        String expected = AddPropertyCommand.class.getCanonicalName() + "{toAdd=" + MAPLE + "}";
        assertEquals(expected, addPropertyCommand.toString());
    }

    /**
     * A default model stub that have all the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addClient(Client client) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addProperty(Property property) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasClient(Client client) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasProperty(Property property) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteClient(Client target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteProperty(Property property) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setClient(Client target, Client editedClient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setProperty(Property target, Property editedProperty) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Client> getFilteredClientList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Property> getFilteredPropertyList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredClientList(Predicate<Client> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPropertyList(Predicate<Property> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single property.
     */
    private class ModelStubWithProperty extends ModelStub {
        private final Property property;

        ModelStubWithProperty(Property property) {
            requireNonNull(property);
            this.property = property;
        }

        @Override
        public boolean hasProperty(Property property) {
            requireNonNull(property);
            return this.property.isSameProperty(property);
        }
    }

    /**
     * A Model stub that always accept the property being added.
     */
    private class ModelStubAcceptingPropertyAdded extends ModelStub {
        final ArrayList<Property> propertiesAdded = new ArrayList<>();

        @Override
        public boolean hasProperty(Property property) {
            requireNonNull(property);
            return propertiesAdded.stream().anyMatch(property::isSameProperty);
        }

        @Override
        public void addProperty(Property property) {
            requireNonNull(property);
            propertiesAdded.add(property);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
