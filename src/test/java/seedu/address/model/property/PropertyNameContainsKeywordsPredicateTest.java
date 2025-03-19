package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PropertyBuilder;

public class PropertyNameContainsKeywordsPredicateTest {

    @Test
    public void test_propertyNameContainsKeyword_success() {
        Property property = new PropertyBuilder().withPropertyName("Maple Villa").build();

        List<String> keywords = Arrays.asList("Maple");
        PropertyNameContainsKeywordsPredicate predicate = new PropertyNameContainsKeywordsPredicate(keywords);
        assertTrue(predicate.test(property));
    }

    @Test
    public void test_propertyNameDoesNotContainKeyword_failure() {
        Property property = new PropertyBuilder().withPropertyName("Orchid Gardens").build();

        List<String> keywords = Arrays.asList("Maple");
        PropertyNameContainsKeywordsPredicate predicate = new PropertyNameContainsKeywordsPredicate(keywords);
        assertFalse(predicate.test(property));
    }

    @Test
    public void test_propertyNameContainsAnyOfTheKeywords_success() {
        Property property = new PropertyBuilder().withPropertyName("Orchid Gardens").build();

        List<String> keywords = Arrays.asList("Maple", "Orchid");
        PropertyNameContainsKeywordsPredicate predicate = new PropertyNameContainsKeywordsPredicate(keywords);
        assertTrue(predicate.test(property));
    }

    @Test
    public void test_propertyNameContainsMultipleKeywords_success() {
        Property property = new PropertyBuilder().withPropertyName("Maple Villa Condominium").build();

        List<String> keywords = Arrays.asList("Maple", "Condo");
        PropertyNameContainsKeywordsPredicate predicate = new PropertyNameContainsKeywordsPredicate(keywords);
        assertTrue(predicate.test(property));
    }

    @Test
    public void test_propertyNameDoesNotContainMultipleKeywords_failure() {
        Property property = new PropertyBuilder().withPropertyName("Rose Villa").build();

        List<String> keywords = Arrays.asList("Maple", "Condo");
        PropertyNameContainsKeywordsPredicate predicate = new PropertyNameContainsKeywordsPredicate(keywords);
        assertFalse(predicate.test(property));
    }

    @Test
    public void test_equalsSameKeywords_true() {
        List<String> keywords1 = Arrays.asList("Maple", "Villa");
        List<String> keywords2 = Arrays.asList("Maple", "Villa");
        PropertyNameContainsKeywordsPredicate predicate1 = new PropertyNameContainsKeywordsPredicate(keywords1);
        PropertyNameContainsKeywordsPredicate predicate2 = new PropertyNameContainsKeywordsPredicate(keywords2);

        assertTrue(predicate1.equals(predicate2));
    }

    @Test
    public void test_equalsDifferentKeywords_false() {
        List<String> keywords1 = Arrays.asList("Maple", "Villa");
        List<String> keywords2 = Arrays.asList("Orchid", "Garden");
        PropertyNameContainsKeywordsPredicate predicate1 = new PropertyNameContainsKeywordsPredicate(keywords1);
        PropertyNameContainsKeywordsPredicate predicate2 = new PropertyNameContainsKeywordsPredicate(keywords2);

        assertFalse(predicate1.equals(predicate2));
    }

    @Test
    public void test_toString() {
        List<String> keywords = Arrays.asList("Maple", "Villa");
        PropertyNameContainsKeywordsPredicate predicate = new PropertyNameContainsKeywordsPredicate(keywords);

        String expected = "seedu.address.model.property.PropertyNameContainsKeywordsPredicate{keywords=[Maple, Villa]}";
        assertEquals(expected, predicate.toString());
    }
}
