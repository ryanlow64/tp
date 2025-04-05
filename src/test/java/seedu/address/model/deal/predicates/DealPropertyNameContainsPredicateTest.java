package seedu.address.model.deal.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.deal.Deal;
import seedu.address.model.property.PropertyName;
import seedu.address.testutil.DealBuilder;

public class DealPropertyNameContainsPredicateTest {

    @Test
    public void test_propertyNameContainsKeywords_returnsTrue() {
        // Property name contains one keyword
        DealPropertyNameContainsPredicate predicate =
                new DealPropertyNameContainsPredicate(new PropertyName("Maple"));
        Deal deal = new DealBuilder().withPropertyName("Maple Villa").build();
        assertTrue(predicate.test(deal));

        // Property name contains multiple keywords
        predicate = new DealPropertyNameContainsPredicate(new PropertyName("Maple Villa"));
        deal = new DealBuilder().withPropertyName("Maple Villa Condominium").build();
        assertTrue(predicate.test(deal));

        // Property name with different case
        predicate = new DealPropertyNameContainsPredicate(new PropertyName("maple villa"));
        deal = new DealBuilder().withPropertyName("Maple Villa").build();
        assertTrue(predicate.test(deal));
    }

    @Test
    public void test_propertyNameDoesNotContainKeywords_returnsFalse() {
        // No matching keywords
        DealPropertyNameContainsPredicate predicate =
                new DealPropertyNameContainsPredicate(new PropertyName("Garden Heights"));
        Deal deal = new DealBuilder().withPropertyName("Maple Villa").build();
        assertFalse(predicate.test(deal));

        // Partial word doesn't match
        predicate = new DealPropertyNameContainsPredicate(new PropertyName("Map"));
        deal = new DealBuilder().withPropertyName("Maple Villa").build();
        assertFalse(predicate.test(deal));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        DealPropertyNameContainsPredicate predicate =
                new DealPropertyNameContainsPredicate(new PropertyName("Maple Villa"));
        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        DealPropertyNameContainsPredicate predicate1 =
                new DealPropertyNameContainsPredicate(new PropertyName("Maple Villa"));
        DealPropertyNameContainsPredicate predicate2 =
                new DealPropertyNameContainsPredicate(new PropertyName("Maple Villa"));
        assertTrue(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        DealPropertyNameContainsPredicate predicate1 =
                new DealPropertyNameContainsPredicate(new PropertyName("Maple Villa"));
        DealPropertyNameContainsPredicate predicate2 =
                new DealPropertyNameContainsPredicate(new PropertyName("Garden Heights"));
        assertFalse(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentTypes_returnsFalse() {
        DealPropertyNameContainsPredicate predicate =
                new DealPropertyNameContainsPredicate(new PropertyName("Maple Villa"));
        assertFalse(predicate.equals(new Object()));
    }
}
