package seedu.address.logic.commands.deal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_DEALS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDeals.DEAL1;
import static seedu.address.testutil.TypicalDeals.DEAL3;
import static seedu.address.testutil.TypicalDeals.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.deal.Deal;
import seedu.address.model.deal.DealStatus;
import seedu.address.model.deal.predicates.DealBuyerNameContainsPredicate;
import seedu.address.model.deal.predicates.DealPropertyNameContainsPredicate;
import seedu.address.model.deal.predicates.DealSellerNameContainsPredicate;
import seedu.address.model.deal.predicates.DealStatusPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindDealCommand}.
 */
public class FindDealCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        DealPropertyNameContainsPredicate firstPredicate =
                new DealPropertyNameContainsPredicate(Collections.singletonList("Villa"));
        DealPropertyNameContainsPredicate secondPredicate =
                new DealPropertyNameContainsPredicate(Collections.singletonList("Condo"));

        FindDealCommand findFirstCommand = new FindDealCommand(firstPredicate);
        FindDealCommand findSecondCommand = new FindDealCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindDealCommand findFirstCommandCopy = new FindDealCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different predicate -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noDealsFound() {
        String expectedMessage = String.format(MESSAGE_DEALS_LISTED_OVERVIEW, 0);
        DealPropertyNameContainsPredicate predicate = preparePropertyNamePredicate(" ");
        FindDealCommand command = new FindDealCommand(predicate);
        expectedModel.updateFilteredDealList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredDealList());
    }

    @Test
    public void execute_findByPropertyName_success() {
        // Villa is in DEAL1 property name
        String expectedMessage = String.format(MESSAGE_DEALS_LISTED_OVERVIEW, 1);

        // Create a predicate that allows all deals through except for property name filter
        Predicate<Deal> alwaysTrue = deal -> true;
        DealPropertyNameContainsPredicate propertyPredicate = preparePropertyNamePredicate("Villa");
        Predicate<Deal> combinedPredicate = propertyPredicate.and(alwaysTrue).and(alwaysTrue);

        FindDealCommand command = new FindDealCommand(combinedPredicate);
        expectedModel.updateFilteredDealList(combinedPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DEAL1), model.getFilteredDealList());
    }

    @Test
    public void execute_findByBuyerName_success() {
        // "John" is in DEAL1 buyer name
        String expectedMessage = String.format(MESSAGE_DEALS_LISTED_OVERVIEW, 1);

        // Create a predicate that allows all deals through except for buyer name filter
        Predicate<Deal> alwaysTrue = deal -> true;
        DealBuyerNameContainsPredicate buyerPredicate = prepareBuyerNamePredicate("John");
        Predicate<Deal> combinedPredicate = alwaysTrue.and(buyerPredicate).and(alwaysTrue);

        FindDealCommand command = new FindDealCommand(combinedPredicate);
        expectedModel.updateFilteredDealList(combinedPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DEAL1), model.getFilteredDealList());
    }

    @Test
    public void execute_findBySellerName_success() {
        // "Jane" is in DEAL1 seller name
        String expectedMessage = String.format(MESSAGE_DEALS_LISTED_OVERVIEW, 1);

        // Create a predicate that allows all deals through except for seller name filter
        Predicate<Deal> alwaysTrue = deal -> true;
        DealSellerNameContainsPredicate sellerPredicate = prepareSellerNamePredicate("Jane");
        Predicate<Deal> combinedPredicate = alwaysTrue.and(alwaysTrue).and(sellerPredicate);

        FindDealCommand command = new FindDealCommand(combinedPredicate);
        expectedModel.updateFilteredDealList(combinedPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DEAL1), model.getFilteredDealList());
    }

    @Test
    public void execute_findByStatus_success() {
        // PENDING status will match DEAL1 and DEAL3
        String expectedMessage = String.format(MESSAGE_DEALS_LISTED_OVERVIEW, 2);

        // Create a predicate that allows all deals through except for status filter
        Predicate<Deal> alwaysTrue = deal -> true;
        DealStatusPredicate statusPredicate = new DealStatusPredicate(DealStatus.PENDING);
        Predicate<Deal> combinedPredicate = alwaysTrue.and(alwaysTrue).and(alwaysTrue).and(statusPredicate);

        FindDealCommand command = new FindDealCommand(combinedPredicate);
        expectedModel.updateFilteredDealList(combinedPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DEAL1, DEAL3), model.getFilteredDealList());
    }

    @Test
    public void execute_multiplePredicate_success() {
        // Create predicates for property name "Villa", buyer name "John", and seller name "Jane"
        // This should match DEAL1 only, as it has all those attributes
        String expectedMessage = String.format(MESSAGE_DEALS_LISTED_OVERVIEW, 1);

        DealPropertyNameContainsPredicate propertyPredicate = preparePropertyNamePredicate("Villa");
        DealBuyerNameContainsPredicate buyerPredicate = prepareBuyerNamePredicate("John");
        DealSellerNameContainsPredicate sellerPredicate = prepareSellerNamePredicate("Jane");

        // Combine predicates with AND logic
        Predicate<Deal> combinedPredicate = propertyPredicate
                .and(buyerPredicate)
                .and(sellerPredicate);

        FindDealCommand command = new FindDealCommand(combinedPredicate);
        expectedModel.updateFilteredDealList(combinedPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DEAL1), model.getFilteredDealList());
    }

    @Test
    public void toStringMethod() {
        DealPropertyNameContainsPredicate predicate = preparePropertyNamePredicate("Villa");
        FindDealCommand command = new FindDealCommand(predicate);
        String expected = FindDealCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, command.toString());
    }

    /**
     * Parses {@code userInput} into a {@code DealPropertyNameContainsPredicate}.
     */
    private DealPropertyNameContainsPredicate preparePropertyNamePredicate(String userInput) {
        return new DealPropertyNameContainsPredicate(
                Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code DealBuyerNameContainsPredicate}.
     */
    private DealBuyerNameContainsPredicate prepareBuyerNamePredicate(String userInput) {
        return new DealBuyerNameContainsPredicate(
                Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code DealSellerNameContainsPredicate}.
     */
    private DealSellerNameContainsPredicate prepareSellerNamePredicate(String userInput) {
        return new DealSellerNameContainsPredicate(
                Arrays.asList(userInput.split("\\s+")));
    }
}
