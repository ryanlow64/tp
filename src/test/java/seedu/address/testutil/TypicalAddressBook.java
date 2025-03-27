package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.client.Client;
import seedu.address.model.deal.Deal;
import seedu.address.model.event.Event;
import seedu.address.model.property.Property;

/**
 * Constructs and returns a typical address book.
 */
public class TypicalAddressBook {
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Event event : TypicalEvents.getTypicalEvents()) {
            ab.addEvent(event);
        }
        for (Deal deal : TypicalDeals.getTypicalDeals()) {
            ab.addDeal(deal);
        }
        for (Client client : TypicalClients.getTypicalClients()) {
            ab.addClient(client);
        }
        for (Property property : TypicalProperties.getTypicalProperties()) {
            ab.addProperty(property);
        }
        return ab;
    }
}
