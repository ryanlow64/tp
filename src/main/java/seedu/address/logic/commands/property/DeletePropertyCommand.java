package seedu.address.logic.commands.property;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deal.Deal;
import seedu.address.model.deal.DealStatus;
import seedu.address.model.event.Event;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyName;

/**
 * Deletes a property identified using it's displayed index from the address book.
 */
public class DeletePropertyCommand extends DeleteCommand<Property> {

    public static final String COMMAND_WORD = "delete_property";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the property identified by the index number used in the displayed property list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PROPERTY_SUCCESS = "Deleted Property: %1$s";
    public static final String MESSAGE_DELETE_PROPERTY_ERROR = "Property cannot be deleted.\n"
            + "Property exists in either deals or events.\n"
            + "Please delete the property from the categories before deleting the property itself.";

    public DeletePropertyCommand(Index targetIndex) {
        super(targetIndex);
    }

    /**
     * Adds a command word and its associated prefixes to the command word map.
     */
    public static void addCommandWord() {
        initialiseCommandWord(COMMAND_WORD);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Property> lastShownList = model.getFilteredPropertyList();
        List<Deal> lastShownDealList = model.getFilteredDealList();
        List<Event> lastShownEventList = model.getFilteredEventList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX);
        }

        Property propertyToDelete = lastShownList.get(targetIndex.getZeroBased());

        if (existInDeals(propertyToDelete, lastShownDealList)
                || existInEvents(propertyToDelete, lastShownEventList)) {
            throw new CommandException(MESSAGE_DELETE_PROPERTY_ERROR);
        }
        model.deleteProperty(propertyToDelete);
        return new CommandResult(String.format(
                MESSAGE_DELETE_PROPERTY_SUCCESS, Messages.formatProperty(propertyToDelete)));
    }

    private boolean existInDeals(Property propertyToDelete, List<Deal> dealList) {
        PropertyName propertyNameToDelete = propertyToDelete.getFullName();
        for (Deal deal : dealList) {
            if ((propertyNameToDelete.equals(deal.getPropertyName()))
                    && deal.getStatus() != DealStatus.CLOSED) {
                return true;
            }
        }
        return false;
    }

    private boolean existInEvents(Property propertyToDelete, List<Event> eventList) {
        PropertyName propertyNameToDelete = propertyToDelete.getFullName();
        for (Event event : eventList) {
            if (propertyNameToDelete.equals(event.getPropertyName())
                    && LocalDateTime.now().isBefore(event.getDateTime())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeletePropertyCommand otherDeletePropertyCommand)) {
            return false;
        }

        return targetIndex.equals(otherDeletePropertyCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
