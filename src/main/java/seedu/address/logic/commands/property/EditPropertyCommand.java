package seedu.address.logic.commands.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SIZE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROPERTIES;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.commons.Address;
import seedu.address.model.property.Description;
import seedu.address.model.commons.Price;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyName;
import seedu.address.model.property.Size;

/**
 * Edits the details of an existing property in the address book.
 */
public class EditPropertyCommand extends EditCommand<Property> {

    public static final String COMMAND_WORD = "edit_property";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the property identified "
            + "by the index number used in the displayed property list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_PROPERTY_NAME + "PROPERTYNAME] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_PRICE + "PRICE (in S$ millions)] "
            + "[" + PREFIX_SIZE + "SIZE (in square feet)] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ADDRESS + "234 Maple Street "
            + PREFIX_PRICE + "1.5";

    public static final String MESSAGE_EDIT_PROPERTY_SUCCESS = "Edited Property: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PROPERTY = "This property already exists in the address book.";

    private final EditPropertyDescriptor editPropertyDescriptor;

    /**
     * @param index of the property in the filtered property list to edit
     * @param editPropertyDescriptor details to edit the property with
     */
    public EditPropertyCommand(Index index, EditPropertyDescriptor editPropertyDescriptor) {
        super(index, editPropertyDescriptor);
        this.editPropertyDescriptor = new EditPropertyDescriptor(editPropertyDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Property> lastShownList = model.getFilteredPropertyList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX);
        }

        Property propertyToEdit = lastShownList.get(index.getZeroBased());
        Property editedProperty = createEditedProperty(propertyToEdit, editPropertyDescriptor);

        if (!propertyToEdit.isSameProperty(editedProperty) && model.hasProperty(editedProperty)) {
            throw new CommandException(MESSAGE_DUPLICATE_PROPERTY);
        }

        model.setProperty(propertyToEdit, editedProperty);
        model.updateFilteredPropertyList(PREDICATE_SHOW_ALL_PROPERTIES);
        return new CommandResult(String.format(MESSAGE_EDIT_PROPERTY_SUCCESS, Messages.format(editedProperty)));
    }

    /**
     * Creates and returns a {@code Property} with the details of {@code propertyToEdit}
     * edited with {@code editPropertyDescriptor}.
     */
    private static Property createEditedProperty(Property propertyToEdit,
                                                 EditPropertyDescriptor editPropertyDescriptor) {
        assert propertyToEdit != null;

        PropertyName updatedPropertyName = editPropertyDescriptor.getPropertyName()
                .orElse(propertyToEdit.getPropertyName());
        Address updatedAddress = editPropertyDescriptor.getAddress().orElse(propertyToEdit.getAddress());
        Optional<Price> updatedPrice = editPropertyDescriptor.getPrice().orElse(propertyToEdit.getPrice());
        Optional<Size> updatedSize = editPropertyDescriptor.getSize().orElse(propertyToEdit.getSize());
        Optional<Description> updatedDescription = editPropertyDescriptor.getDescription()
                .orElse(propertyToEdit.getDescription());

        return new Property(updatedPropertyName, updatedAddress, updatedPrice, updatedSize, updatedDescription);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPropertyCommand otherEditCommand)) {
            return false;
        }

        return index.equals(otherEditCommand.index)
                && editPropertyDescriptor.equals(otherEditCommand.editPropertyDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPropertyDescriptor", editPropertyDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the property with. Each non-empty field value will replace the
     * corresponding field value of the property.
     */
    public static class EditPropertyDescriptor extends EditDescriptor<Property> {
        private PropertyName propertyName;
        private Address address;
        private Optional<Price> price;
        private Optional<Size> size;
        private Optional<Description> description;

        public EditPropertyDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPropertyDescriptor(EditPropertyDescriptor toCopy) {
            setPropertyName(toCopy.propertyName);
            setAddress(toCopy.address);
            setPrice(toCopy.price);
            setSize(toCopy.size);
            setDescription(toCopy.description);
        }

        /**
         * Returns true if at least one field is edited.
         */
        @Override
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(propertyName, address, price, size, description);
        }

        public void setPropertyName(PropertyName propertyName) {
            this.propertyName = propertyName;
        }

        public Optional<PropertyName> getPropertyName() {
            return Optional.ofNullable(propertyName);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setPrice(Optional<Price> price) {
            this.price = price;
        }

        public Optional<Optional<Price>> getPrice() {
            return Optional.ofNullable(price);
        }

        public void setSize(Optional<Size> size) {
            this.size = size;
        }

        public Optional<Optional<Size>> getSize() {
            return Optional.ofNullable(size);
        }

        public void setDescription(Optional<Description> description) {
            this.description = description;
        }

        public Optional<Optional<Description>> getDescription() {
            return Optional.ofNullable(description);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPropertyDescriptor otherEditPropertyDescriptor)) {
                return false;
            }

            return Objects.equals(propertyName, otherEditPropertyDescriptor.propertyName)
                    && Objects.equals(address, otherEditPropertyDescriptor.address)
                    && Objects.equals(price, otherEditPropertyDescriptor.price)
                    && Objects.equals(size, otherEditPropertyDescriptor.size)
                    && Objects.equals(description, otherEditPropertyDescriptor.description);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("propertyName", propertyName)
                    .add("address", address)
                    .add("price", price)
                    .add("size", size)
                    .add("description", description)
                    .toString();
        }
    }
}
