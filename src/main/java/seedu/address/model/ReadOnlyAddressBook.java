package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.client.Client;
import seedu.address.model.deal.Deal;
import seedu.address.model.property.Property;
import seedu.address.model.schedule.Schedule;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the clients list.
     * This list will not contain any duplicate clients.
     */
    ObservableList<Client> getClientList();

    /**
     * Returns an unmodifiable view of the deals list.
     * This list will not contain any duplicate deals.
     */
    ObservableList<Deal> getDealList();

    /**
     * Returns an unmodifiable view of the properties list.
     * This list will not contain any duplicate properties.
     */
    ObservableList<Property> getPropertyList();

    /**
     * Returns an unmodifiable view of the schedules list.
     * This list will not contain any duplicate schedules.
     */
    ObservableList<Schedule> getScheduleList();

}
