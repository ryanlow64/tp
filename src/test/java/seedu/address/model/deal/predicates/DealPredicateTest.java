package seedu.address.model.deal.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.deal.Deal;
import seedu.address.testutil.DealBuilder;

/**
 * Tests for the DealPredicate abstract class.
 * Uses a concrete implementation to test abstract class behavior.
 */
public class DealPredicateTest {

    private static class TestDealPredicate extends DealPredicate<String> {
        TestDealPredicate(String value) {
            super(value);
        }

        @Override
        public boolean test(Deal deal) {
            return true; // Simple implementation for testing
        }
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        TestDealPredicate predicate = new TestDealPredicate("test");
        assertTrue(predicate.equals(predicate));
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        TestDealPredicate predicate1 = new TestDealPredicate("test");
        TestDealPredicate predicate2 = new TestDealPredicate("test");
        assertTrue(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        TestDealPredicate predicate = new TestDealPredicate("test");
        assertFalse(predicate.equals(new Object()));
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        TestDealPredicate predicate1 = new TestDealPredicate("test1");
        TestDealPredicate predicate2 = new TestDealPredicate("test2");
        assertFalse(predicate1.equals(predicate2));
    }

    @Test
    public void test_implementation_success() {
        TestDealPredicate predicate = new TestDealPredicate("test");
        Deal deal = new DealBuilder().build();

        assertTrue(predicate.test(deal)); // Based on our test implementation
    }

    @Test
    public void getValue_success() {
        TestDealPredicate predicate = new TestDealPredicate("test");
        assertEquals("test", predicate.value);
    }
}
