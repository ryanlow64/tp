package seedu.address.model.deal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.deal.exceptions.DealNotFoundException;
import seedu.address.model.deal.exceptions.DuplicateDealException;
import seedu.address.testutil.DealBuilder;

public class UniqueDealListTest {
    private final UniqueDealList uniqueDealList = new UniqueDealList();

    @Test
    public void contains_nullDeal_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDealList.contains(null));
    }

    @Test
    public void contains_dealNotInList_returnsFalse() {
        assertFalse(uniqueDealList.contains(new DealBuilder().build()));
    }

    @Test
    public void contains_dealInList_returnsTrue() {
        Deal deal = new DealBuilder().build();
        uniqueDealList.add(deal);
        assertTrue(uniqueDealList.contains(deal));
    }

    @Test
    public void add_nullDeal_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDealList.add(null));
    }

    @Test
    public void add_duplicateDeal_throwsDuplicateDealException() {
        Deal deal = new DealBuilder().build();
        uniqueDealList.add(deal);
        assertThrows(DuplicateDealException.class, () -> uniqueDealList.add(deal));
    }

    @Test
    public void add_validDeal_success() {
        Deal deal = new DealBuilder().build();
        uniqueDealList.add(deal);

        UniqueDealList expectedList = new UniqueDealList();
        expectedList.add(deal);

        assertEquals(expectedList, uniqueDealList);
    }

    @Test
    public void setDeal_nullTargetDeal_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueDealList.setDeal(null, new DealBuilder().build()));
    }

    @Test
    public void setDeal_nullEditedDeal_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueDealList.setDeal(new DealBuilder().build(), null));
    }

    @Test
    public void setDeal_targetDealNotInList_throwsDealNotFoundException() {
        Deal targetDeal = new DealBuilder().build();
        Deal editedDeal = new DealBuilder().withPropertyName("Different Property").build();

        assertThrows(DealNotFoundException.class, () ->
                uniqueDealList.setDeal(targetDeal, editedDeal));
    }

    @Test
    public void setDeal_editedDealIsSameDeal_success() {
        Deal originalDeal = new DealBuilder().build();
        uniqueDealList.add(originalDeal);

        Deal editedDeal = new DealBuilder().withPrice(200L).build();
        uniqueDealList.setDeal(originalDeal, editedDeal);

        UniqueDealList expectedList = new UniqueDealList();
        expectedList.add(editedDeal);

        assertEquals(expectedList, uniqueDealList);
    }

    @Test
    public void setDeal_editedDealHasDifferentIdentity_success() {
        Deal originalDeal = new DealBuilder().build();
        uniqueDealList.add(originalDeal);

        Deal editedDeal = new DealBuilder()
                .withPropertyName("Different Property")
                .withBuyer("Different Buyer")
                .build();

        uniqueDealList.setDeal(originalDeal, editedDeal);

        UniqueDealList expectedList = new UniqueDealList();
        expectedList.add(editedDeal);

        assertEquals(expectedList, uniqueDealList);
    }

    @Test
    public void setDeal_editedDealHasNonUniqueIdentity_throwsDuplicateDealException() {
        Deal deal1 = new DealBuilder().build();
        Deal deal2 = new DealBuilder()
                .withPropertyName("Different Property")
                .withBuyer("Different Buyer")
                .build();

        uniqueDealList.add(deal1);
        uniqueDealList.add(deal2);

        Deal editedDeal = new DealBuilder().build(); // Same identity as deal1

        assertThrows(DuplicateDealException.class, () ->
                uniqueDealList.setDeal(deal2, editedDeal));
    }

    @Test
    public void remove_nullDeal_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueDealList.remove(null));
    }

    @Test
    public void remove_dealDoesNotExist_throwsDealNotFoundException() {
        assertThrows(DealNotFoundException.class, () ->
                uniqueDealList.remove(new DealBuilder().build()));
    }

    @Test
    public void remove_existingDeal_success() {
        Deal deal = new DealBuilder().build();
        uniqueDealList.add(deal);
        uniqueDealList.remove(deal);

        UniqueDealList expectedList = new UniqueDealList();

        assertEquals(expectedList, uniqueDealList);
    }

    @Test
    public void setDeals_nullUniqueDealList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueDealList.setDeals((UniqueDealList) null));
    }

    @Test
    public void setDeals_uniqueDealList_success() {
        Deal deal = new DealBuilder().build();
        uniqueDealList.add(deal);

        UniqueDealList expectedList = new UniqueDealList();
        expectedList.add(new DealBuilder().withPropertyName("Different Property").build());

        uniqueDealList.setDeals(expectedList);

        assertEquals(expectedList, uniqueDealList);
    }

    @Test
    public void setDeals_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueDealList.setDeals((List<Deal>) null));
    }

    @Test
    public void setDeals_listWithDuplicateDeals_throwsDuplicateDealException() {
        Deal deal = new DealBuilder().build();
        List<Deal> listWithDuplicateDeals = Arrays.asList(deal, deal);

        assertThrows(DuplicateDealException.class, () ->
                uniqueDealList.setDeals(listWithDuplicateDeals));
    }

    @Test
    public void setDeals_validList_success() {
        List<Deal> validDealList = Collections.singletonList(new DealBuilder().build());
        uniqueDealList.setDeals(validDealList);

        UniqueDealList expectedList = new UniqueDealList();
        expectedList.setDeals(validDealList);

        assertEquals(expectedList, uniqueDealList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueDealList.asUnmodifiableObservableList().remove(0));
    }
}
