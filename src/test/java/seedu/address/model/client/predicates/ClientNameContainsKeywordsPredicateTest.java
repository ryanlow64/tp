package seedu.address.model.client.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ClientBuilder;

public class ClientNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ClientNameContainsKeywordsPredicate firstPredicate =
            new ClientNameContainsKeywordsPredicate(firstPredicateKeywordList);
        ClientNameContainsKeywordsPredicate secondPredicate =
            new ClientNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ClientNameContainsKeywordsPredicate firstPredicateCopy =
            new ClientNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different client -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        ClientNameContainsKeywordsPredicate predicate =
            new ClientNameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new ClientBuilder().withClientName("Alice Bob").build()));

        // Multiple keywords
        predicate = new ClientNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new ClientBuilder().withClientName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new ClientNameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new ClientBuilder().withClientName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new ClientNameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new ClientBuilder().withClientName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ClientNameContainsKeywordsPredicate predicate =
            new ClientNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ClientBuilder().withClientName("Alice").build()));

        // Non-matching keyword
        predicate = new ClientNameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new ClientBuilder().withClientName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new ClientNameContainsKeywordsPredicate(Arrays.asList("12345678", "alice@email.com", "Main",
            "Street"));
        assertFalse(predicate.test(new ClientBuilder().withClientName("Alice").withPhone("12345678")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        ClientNameContainsKeywordsPredicate predicate = new ClientNameContainsKeywordsPredicate(keywords);

        String expected = ClientNameContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
