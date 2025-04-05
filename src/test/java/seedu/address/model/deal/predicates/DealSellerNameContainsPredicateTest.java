package seedu.address.model.deal.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.client.ClientName;
import seedu.address.model.deal.Deal;
import seedu.address.testutil.DealBuilder;

public class DealSellerNameContainsPredicateTest {

    @Test
    public void test_sellerNameContainsKeyword_returnsTrue() {
        // Seller name contains the keyword (exact match, case insensitive)
        DealSellerNameContainsPredicate predicate =
                new DealSellerNameContainsPredicate(new ClientName("John"));
        Deal deal = new DealBuilder().withSeller("John Doe").build();
        assertTrue(predicate.test(deal));

        // Seller name contains multiple keywords (partial match)
        predicate = new DealSellerNameContainsPredicate(new ClientName("John Doe"));
        deal = new DealBuilder().withSeller("John Doe Smith").build();
        assertTrue(predicate.test(deal));

        // Seller name with different case
        predicate = new DealSellerNameContainsPredicate(new ClientName("john"));
        deal = new DealBuilder().withSeller("John Doe").build();
        assertTrue(predicate.test(deal));
    }

    @Test
    public void test_sellerNameDoesNotContainKeyword_returnsFalse() {
        // Seller name doesn't contain any matching keywords
        DealSellerNameContainsPredicate predicate =
                new DealSellerNameContainsPredicate(new ClientName("Peter"));
        Deal deal = new DealBuilder().withSeller("John Doe").build();
        assertFalse(predicate.test(deal));

        // Partial word doesn't match
        predicate = new DealSellerNameContainsPredicate(new ClientName("Jo"));
        deal = new DealBuilder().withSeller("John Doe").build();
        assertFalse(predicate.test(deal));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        DealSellerNameContainsPredicate predicate =
                new DealSellerNameContainsPredicate(new ClientName("John"));
        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        DealSellerNameContainsPredicate predicate1 =
                new DealSellerNameContainsPredicate(new ClientName("John"));
        DealSellerNameContainsPredicate predicate2 =
                new DealSellerNameContainsPredicate(new ClientName("John"));
        assertTrue(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        DealSellerNameContainsPredicate predicate1 =
                new DealSellerNameContainsPredicate(new ClientName("John"));
        DealSellerNameContainsPredicate predicate2 =
                new DealSellerNameContainsPredicate(new ClientName("Peter"));
        assertFalse(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentTypes_returnsFalse() {
        DealSellerNameContainsPredicate predicate =
                new DealSellerNameContainsPredicate(new ClientName("John"));
        assertFalse(predicate.equals(new Object()));
    }
}
