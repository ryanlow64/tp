package seedu.address.logic.commands.client;

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
import seedu.address.model.client.Client;
import seedu.address.model.client.ClientName;
import seedu.address.model.deal.Deal;
import seedu.address.model.deal.DealStatus;
import seedu.address.model.event.Event;
import seedu.address.model.property.Property;

/**
 * Deletes a client identified using it's displayed index from the address book.
 */
public class DeleteClientCommand extends DeleteCommand<Client> {

    public static final String COMMAND_WORD = "delete_client";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the client identified by the index number used in the displayed client list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_CLIENT_SUCCESS = "Deleted Client: %1$s";

    public static final String MESSAGE_DELETE_CLIENT_ERROR = "Client cannot be deleted.\n"
        + "Client exists in either property, deals, or events.\n"
        + "Please delete the client from the categories before deleting the client itself.";

    public DeleteClientCommand(Index targetIndex) {
        super(targetIndex);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Client> lastShownClientList = model.getFilteredClientList();
        List<Deal> lastShownDealList = model.getFilteredDealList();
        List<Event> lastShownEventList = model.getFilteredEventList();
        List<Property> lastShownPropertyList = model.getFilteredPropertyList();

        if (targetIndex.getZeroBased() >= lastShownClientList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        }

        Client clientToDelete = lastShownClientList.get(targetIndex.getZeroBased());

        if (existInDeal(clientToDelete, lastShownDealList)
            || existInEvent(clientToDelete, lastShownEventList)
            || existInProperty(clientToDelete, lastShownPropertyList)) {
            throw new CommandException(MESSAGE_DELETE_CLIENT_ERROR);
        }

        model.deleteClient(clientToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_CLIENT_SUCCESS, Messages.formatClient(clientToDelete)));
    }

    private boolean existInDeal(Client clientToDelete, List<Deal> dealList) {
        boolean isInDeal = false;
        ClientName clientNameToDelete = clientToDelete.getClientName();
        for (Deal deal : dealList) {
            if (clientNameToDelete.equals(deal.getBuyer()) || clientNameToDelete.equals(deal.getSeller())) {
                if (deal.getStatus() == DealStatus.CLOSED) {
                    continue;
                }
                isInDeal = true;
                break;
            }
        }
        return isInDeal;
    }

    private boolean existInEvent(Client clientToDelete, List<Event> eventList) {
        boolean isInEvent = false;
        ClientName clientNameToDelete = clientToDelete.getClientName();
        for (Event event : eventList) {
            if (clientNameToDelete.equals(event.getClientName())) {
                if (LocalDateTime.now().isAfter(event.getDateTime())) {
                    continue;
                }
                isInEvent = true;
                break;
            }
        }
        return isInEvent;
    }

    private boolean existInProperty(Client clientToDelete, List<Property> propertyList) {
        boolean isInProperty = false;
        ClientName clientNameToDelete = clientToDelete.getClientName();
        for (Property property : propertyList) {
            if (clientNameToDelete.equals(property.getOwner())) {
                isInProperty = true;
                break;
            }
        }
        return isInProperty;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteClientCommand)) {
            return false;
        }

        DeleteClientCommand otherDeleteClientCommand = (DeleteClientCommand) other;
        return targetIndex.equals(otherDeleteClientCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("targetIndex", targetIndex)
            .toString();
    }
}
