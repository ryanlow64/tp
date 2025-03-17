package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.commons.Price;
import seedu.address.model.deal.Deal;
import seedu.address.model.deal.DealStatus;

/**
 * A utility class containing a list of {@code Deal} objects to be used in tests.
 */
public class TypicalDeals {

    public static final Deal DEAL1 = new Deal(
            Index.fromOneBased(1),
            Index.fromOneBased(1),
            Index.fromOneBased(2),
            new Price(500000),
            DealStatus.PENDING);

    public static final Deal DEAL2 = new Deal(
            Index.fromOneBased(2),
            Index.fromOneBased(3),
            Index.fromOneBased(4),
            new Price(750000),
            DealStatus.CLOSED);

    public static final Deal DEAL3 = new Deal(
            Index.fromOneBased(3),
            Index.fromOneBased(5),
            Index.fromOneBased(6),
            new Price(1000000),
            DealStatus.IN_NEGOTIATION);

    private TypicalDeals() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical deals.
     */
    public static AddressBook getTypicalAddressBookWithDeals() {
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
