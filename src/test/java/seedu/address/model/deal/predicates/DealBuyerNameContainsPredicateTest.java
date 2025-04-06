package seedu.address.model.deal.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.client.ClientName;
import seedu.address.model.deal.Deal;
import seedu.address.testutil.DealBuilder;

public class DealBuyerNameContainsPredicateTest {

    @Test
    public void test_buyerNameContainsKeyword_returnsTrue() {
        // Buyer name contains the keyword (exact match, case insensitive)
        DealBuyerNameContainsPredicate predicate =
                new DealBuyerNameContainsPredicate(new ClientName("Amy"));
        Deal deal = new DealBuilder().withBuyer("Amy Bee").build();
        assertTrue(predicate.test(deal));

        // Buyer name contains multiple keywords (partial match)
        predicate = new DealBuyerNameContainsPredicate(new ClientName("Amy Bee"));
        deal = new DealBuilder().withBuyer("Amy Bee Charlotte").build();
        assertTrue(predicate.test(deal));

        // Buyer name with different case
        predicate = new DealBuyerNameContainsPredicate(new ClientName("amy"));
        deal = new DealBuilder().withBuyer("Amy Bee").build();
        assertTrue(predicate.test(deal));
    }

    @Test
    public void test_buyerNameDoesNotContainKeyword_returnsFalse() {
        // Buyer name doesn't contain any matching keywords
        DealBuyerNameContainsPredicate predicate =
                new DealBuyerNameContainsPredicate(new ClientName("Carol"));
        Deal deal = new DealBuilder().withBuyer("Amy Bee").build();
        assertFalse(predicate.test(deal));

        // Partial word doesn't match
        predicate = new DealBuyerNameContainsPredicate(new ClientName("Am"));
        deal = new DealBuilder().withBuyer("Amy Bee").build();
        assertFalse(predicate.test(deal));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        DealBuyerNameContainsPredicate predicate =
                new DealBuyerNameContainsPredicate(new ClientName("Amy"));
        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        DealBuyerNameContainsPredicate predicate1 =
                new DealBuyerNameContainsPredicate(new ClientName("Amy"));
        DealBuyerNameContainsPredicate predicate2 =
                new DealBuyerNameContainsPredicate(new ClientName("Amy"));
        assertTrue(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        DealBuyerNameContainsPredicate predicate1 =
                new DealBuyerNameContainsPredicate(new ClientName("Amy"));
        DealBuyerNameContainsPredicate predicate2 =
                new DealBuyerNameContainsPredicate(new ClientName("Bob"));
        assertFalse(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentTypes_returnsFalse() {
        DealBuyerNameContainsPredicate predicate =
                new DealBuyerNameContainsPredicate(new ClientName("Amy"));
        assertFalse(predicate.equals(new Object()));
    }
}
