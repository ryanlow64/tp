package seedu.address.model.deal.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.deal.Deal;
import seedu.address.model.deal.DealStatus;
import seedu.address.testutil.DealBuilder;

public class DealStatusPredicateTest {

    @Test
    public void test_dealHasMatchingStatus_returnsTrue() {
        // Deal with PENDING status
        DealStatusPredicate predicate = new DealStatusPredicate(DealStatus.PENDING);
        Deal deal = new DealBuilder().withStatus(DealStatus.PENDING).build();
        assertTrue(predicate.test(deal));

        // Deal with CLOSED status
        predicate = new DealStatusPredicate(DealStatus.CLOSED);
        deal = new DealBuilder().withStatus(DealStatus.CLOSED).build();
        assertTrue(predicate.test(deal));

        // Deal with OPEN status
        predicate = new DealStatusPredicate(DealStatus.OPEN);
        deal = new DealBuilder().withStatus(DealStatus.OPEN).build();
        assertTrue(predicate.test(deal));
    }

    @Test
    public void test_dealDoesNotHaveMatchingStatus_returnsFalse() {
        // Looking for PENDING but deal is CLOSED
        DealStatusPredicate predicate = new DealStatusPredicate(DealStatus.PENDING);
        Deal deal = new DealBuilder().withStatus(DealStatus.CLOSED).build();
        assertFalse(predicate.test(deal));

        // Looking for OPEN but deal is PENDING
        predicate = new DealStatusPredicate(DealStatus.OPEN);
        deal = new DealBuilder().withStatus(DealStatus.PENDING).build();
        assertFalse(predicate.test(deal));

        // Looking for CLOSED but deal is OPEN
        predicate = new DealStatusPredicate(DealStatus.CLOSED);
        deal = new DealBuilder().withStatus(DealStatus.OPEN).build();
        assertFalse(predicate.test(deal));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        DealStatusPredicate predicate = new DealStatusPredicate(DealStatus.PENDING);
        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        DealStatusPredicate predicate1 = new DealStatusPredicate(DealStatus.PENDING);
        DealStatusPredicate predicate2 = new DealStatusPredicate(DealStatus.PENDING);
        assertTrue(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        DealStatusPredicate predicate1 = new DealStatusPredicate(DealStatus.PENDING);
        DealStatusPredicate predicate2 = new DealStatusPredicate(DealStatus.CLOSED);
        assertFalse(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentTypes_returnsFalse() {
        DealStatusPredicate predicate = new DealStatusPredicate(DealStatus.PENDING);
        assertFalse(predicate.equals(new Object()));
    }
}
