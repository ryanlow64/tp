package seedu.address.logic.commands.deal;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ModelStub;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.client.ClientName;
import seedu.address.model.commons.Price;
import seedu.address.model.deal.Deal;
import seedu.address.model.deal.DealStatus;
import seedu.address.model.property.PropertyName;


public class AddDealCommandTest {

    @Test
    public void constructor_nullDeal_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddDealCommand(null, null, null, null, null));
    }

    @Test
    public void execute_dealAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingDealAdded modelStub = new ModelStubAcceptingDealAdded();
        PropertyName propertyName = new PropertyName("Test Property");
        ClientName buyer = new ClientName("John Doe");
        ClientName seller = new ClientName("Jane Smith");
        Price price = new Price(500000L);
        DealStatus status = DealStatus.PENDING;

        Deal validDeal = new Deal(propertyName, buyer, seller, price, status);
        CommandResult commandResult = new AddDealCommand(propertyName, buyer, seller, price, status)
                .execute(modelStub);

        assertEquals(String.format(AddDealCommand.MESSAGE_SUCCESS,
                propertyName.toString(),
                buyer.toString(),
                seller.toString(),
                price.value,
                status),
                commandResult.getFeedbackToUser());
        assertEquals(1, modelStub.dealsAdded.size());
        assertTrue(modelStub.dealsAdded.get(0).isSameDeal(validDeal));
    }

    @Test
    public void execute_duplicateDeal_throwsCommandException() {
        PropertyName propertyName = new PropertyName("Test Property");
        ClientName buyer = new ClientName("John Doe");
        ClientName seller = new ClientName("Jane Smith");
        Price price = new Price(500000L);
        DealStatus status = DealStatus.PENDING;

        Deal validDeal = new Deal(propertyName, buyer, seller, price, status);
        AddDealCommand addDealCommand = new AddDealCommand(propertyName, buyer, seller, price, status);
        ModelStub modelStub = new ModelStubWithDeal(validDeal);

        CommandException exception = assertThrows(CommandException.class, (
                ) -> addDealCommand.execute(modelStub));
        assertEquals(AddDealCommand.MESSAGE_DUPLICATE_DEAL, exception.getMessage());
    }

    @Test
    public void execute_sameBuyerAndSeller_throwsCommandException() {
        PropertyName propertyName = new PropertyName("Test Property");
        ClientName person = new ClientName("Same Person");
        Price price = new Price(500000L);
        DealStatus status = DealStatus.PENDING;

        AddDealCommand addDealCommand = new AddDealCommand(propertyName, person, person, price, status);
        ModelStub modelStub = new ModelStubWithClients();

        CommandException exception = assertThrows(CommandException.class, (
                ) -> addDealCommand.execute(modelStub));
        assertEquals(AddDealCommand.MESSAGE_SAME_BUYER_SELLER, exception.getMessage());
    }

    @Test
    public void execute_propertyAlreadyInDeal_throwsCommandException() {
        PropertyName propertyName = new PropertyName("Test Property");
        ClientName buyer1 = new ClientName("John Doe");
        ClientName seller1 = new ClientName("Jane Smith");
        ClientName buyer2 = new ClientName("Alice Brown");
        ClientName seller2 = new ClientName("Bob Wilson");
        Price price = new Price(500000L);
        DealStatus status = DealStatus.PENDING;

        Deal existingDeal = new Deal(propertyName, buyer1, seller1, price, status);
        AddDealCommand addDealCommand = new AddDealCommand(propertyName, buyer2, seller2, price, status);
        ModelStub modelStub = new ModelStubWithDealAndClients(existingDeal);

        CommandException exception = assertThrows(CommandException.class, (
                ) -> addDealCommand.execute(modelStub));
        assertEquals(AddDealCommand.MESSAGE_PROPERTY_ALREADY_IN_DEAL, exception.getMessage());
    }

    @Test
    public void equals() {
        PropertyName property1 = new PropertyName("Property 1");
        PropertyName property2 = new PropertyName("Property 2");
        ClientName buyer1 = new ClientName("John Doe");
        ClientName buyer2 = new ClientName("Jane Doe");
        ClientName seller1 = new ClientName("Bob Smith");
        ClientName seller2 = new ClientName("Alice Smith");
        Price price1 = new Price(500000L);
        Price price2 = new Price(600000L);
        DealStatus status1 = DealStatus.PENDING;
        DealStatus status2 = DealStatus.CLOSED;

        AddDealCommand addFirstCommand = new AddDealCommand(property1, buyer1, seller1, price1, status1);
        AddDealCommand addSecondCommand = new AddDealCommand(property2, buyer2, seller2, price2, status2);

        // same object -> returns true
        assertTrue(addFirstCommand.equals(addFirstCommand));

        // same values -> returns true
        AddDealCommand addFirstCommandCopy = new AddDealCommand(property1, buyer1, seller1, price1, status1);
        assertTrue(addFirstCommand.equals(addFirstCommandCopy));

        // different types -> returns false
        assertFalse(addFirstCommand.equals(1));

        // null -> returns false
        assertFalse(addFirstCommand.equals(null));

        // different deal -> returns false
        assertFalse(addFirstCommand.equals(addSecondCommand));
    }

    /**
     * A Model stub that contains a single deal.
     */
    private class ModelStubWithDeal extends ModelStub {
        private final Deal deal;

        ModelStubWithDeal(Deal deal) {
            requireNonNull(deal);
            this.deal = deal;
        }

        @Override
        public boolean hasDeal(Deal deal) {
            requireNonNull(deal);
            return this.deal.isSameDeal(deal);
        }
    }

    /**
     * A Model stub that contains clients but no deals.
     */
    private class ModelStubWithClients extends ModelStub {
        @Override
        public boolean hasDeal(Deal deal) {
            return false;
        }
    }

    /**
     * A Model stub that contains both clients and a deal.
     */
    private class ModelStubWithDealAndClients extends ModelStub {
        private final Deal deal;
        ModelStubWithDealAndClients(Deal deal) {
            requireNonNull(deal);
            this.deal = deal;
        }

        @Override
        public boolean hasDeal(Deal deal) {
            requireNonNull(deal);
            // For property already in deal test, we should return false here
            // so that the check for property already in deal can be triggered
            return false;
        }
        @Override
        public ObservableList<Deal> getFilteredDealList() {
            return FXCollections.observableArrayList(List.of(deal));
        }
    }

    /**
     * A Model stub that always accepts the deal being added.
     */
    private class ModelStubAcceptingDealAdded extends ModelStub {
        final ArrayList<Deal> dealsAdded = new ArrayList<>();

        @Override
        public boolean hasDeal(Deal deal) {
            requireNonNull(deal);
            return dealsAdded.stream().anyMatch(d -> d.isSameDeal(deal));
        }

        @Override
        public void addDeal(Deal deal) {
            requireNonNull(deal);
            dealsAdded.add(deal);
        }

        @Override
        public ObservableList<Deal> getFilteredDealList() {
            return FXCollections.observableArrayList(dealsAdded);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
