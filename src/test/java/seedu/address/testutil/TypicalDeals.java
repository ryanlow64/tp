package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.client.ClientName;
import seedu.address.model.commons.Price;
import seedu.address.model.deal.Deal;
import seedu.address.model.deal.DealStatus;
import seedu.address.model.property.PropertyName;

/**
 * A utility class containing a list of {@code Deal} objects to be used in tests.
 */
public class TypicalDeals {

    public static final Deal DEAL1 = new Deal(
            new PropertyName("Sunset Villa"),
            new ClientName("John Doe"),
            new ClientName("Jane Smith"),
            new Price(500000L),
            DealStatus.PENDING);

    public static final Deal DEAL2 = new Deal(
            new PropertyName("Ocean View Condo"),
            new ClientName("Alice Brown"),
            new ClientName("Bob Wilson"),
            new Price(750000L),
            DealStatus.CLOSED);

    public static final Deal DEAL3 = new Deal(
            new PropertyName("Mountain Lodge"),
            new ClientName("Charlie Davis"),
            new ClientName("Diana Evans"),
            new Price(1000000L),
            DealStatus.IN_NEGOTIATION);

    private TypicalDeals() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical deals.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Deal deal : getTypicalDeals()) {
            ab.addDeal(deal);
        }
        return ab;
    }

    public static List<Deal> getTypicalDeals() {
        return new ArrayList<>(Arrays.asList(DEAL1, DEAL2, DEAL3));
    }
}
