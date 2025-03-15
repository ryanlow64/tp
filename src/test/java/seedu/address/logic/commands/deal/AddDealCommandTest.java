package seedu.address.logic.commands.deal;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ModelStub;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.client.Client;
import seedu.address.model.commons.Price;
import seedu.address.model.deal.Deal;
import seedu.address.model.deal.DealStatus;

public class AddDealCommandTest {

    @Test
    public void constructor_nullDeal_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddDealCommand(null, null, null, null, null));
    }

    @Test
    public void execute_dealAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingDealAdded modelStub = new ModelStubAcceptingDealAdded();
        Index propertyId = Index.fromOneBased(1);
        Index buyerId = Index.fromOneBased(1);
        Index sellerId = Index.fromOneBased(2);
        Price price = new Price(500000);
        DealStatus status = DealStatus.PENDING;
        Deal validDeal = new Deal(propertyId, buyerId, sellerId, price, status);

        CommandResult commandResult = new AddDealCommand(propertyId, buyerId, sellerId, price, status)
                .execute(modelStub);

        assertEquals(String.format(AddDealCommand.MESSAGE_SUCCESS,
                propertyId.getOneBased(),
                buyerId.getOneBased(),
                sellerId.getOneBased(),
                price.value,
                status),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validDeal), modelStub.dealsAdded);
    }

    @Test
    public void execute_duplicateDeal_throwsCommandException() {
        Index propertyId = Index.fromOneBased(1);
        Index buyerId = Index.fromOneBased(1);
        Index sellerId = Index.fromOneBased(2);
        Price price = new Price(500000);
        DealStatus status = DealStatus.PENDING;
        Deal validDeal = new Deal(propertyId, buyerId, sellerId, price, status);
        AddDealCommand addDealCommand = new AddDealCommand(propertyId, buyerId, sellerId, price, status);
        ModelStub modelStub = new ModelStubWithDeal(validDeal);

        CommandException thrown = assertThrows(CommandException.class, () -> addDealCommand.execute(modelStub));
        assertEquals(AddDealCommand.MESSAGE_DUPLICATE_DEAL, thrown.getMessage());
    }

    @Test
    public void execute_sameBuyerAndSeller_throwsCommandException() {
        Index propertyId = Index.fromOneBased(1);
        Index buyerId = Index.fromOneBased(1);
        Index sellerId = Index.fromOneBased(1); // Same as buyer
        Price price = new Price(500000);
        DealStatus status = DealStatus.PENDING;
        AddDealCommand addDealCommand = new AddDealCommand(propertyId, buyerId, sellerId, price, status);
        ModelStub modelStub = new ModelStubWithClients();

        CommandException thrown = assertThrows(CommandException.class, () -> addDealCommand.execute(modelStub));
        assertEquals(AddDealCommand.MESSAGE_SAME_BUYER_SELLER, thrown.getMessage());
    }

    @Test
    public void execute_propertyAlreadyInDeal_throwsCommandException() {
        Index propertyId = Index.fromOneBased(1);
        Index buyerId = Index.fromOneBased(1);
        Index sellerId = Index.fromOneBased(2);
        Price price = new Price(500000);
        DealStatus status = DealStatus.PENDING;
        Deal existingDeal = new Deal(propertyId, buyerId, sellerId, price, status);
        // Different buyer and seller, but same property
        Index newBuyerId = Index.fromOneBased(3);
        Index newSellerId = Index.fromOneBased(4);
        AddDealCommand addDealCommand = new AddDealCommand(propertyId, newBuyerId, newSellerId, price, status);
        ModelStub modelStub = new ModelStubWithDealAndClients(existingDeal);

        CommandException thrown = assertThrows(CommandException.class, () -> addDealCommand.execute(modelStub));
        assertEquals(AddDealCommand.MESSAGE_PROPERTY_ALREADY_IN_DEAL, thrown.getMessage());
    }

    @Test
    public void equals() {
        Index propertyId1 = Index.fromOneBased(1);
        Index buyerId1 = Index.fromOneBased(1);
        Index sellerId1 = Index.fromOneBased(2);
        Price price1 = new Price(500000);
        DealStatus status1 = DealStatus.PENDING;
        Index propertyId2 = Index.fromOneBased(2);
        Index buyerId2 = Index.fromOneBased(3);
        Index sellerId2 = Index.fromOneBased(4);
        Price price2 = new Price(600000);
        DealStatus status2 = DealStatus.CLOSED;
        AddDealCommand addDealCommand1 = new AddDealCommand(propertyId1, buyerId1, sellerId1, price1, status1);
        AddDealCommand addDealCommand2 = new AddDealCommand(propertyId2, buyerId2, sellerId2, price2, status2);

        // same object -> returns true
        assertTrue(addDealCommand1.equals(addDealCommand1));

        // same values -> returns true
        AddDealCommand addDealCommand1Copy = new AddDealCommand(propertyId1, buyerId1, sellerId1, price1, status1);
        assertTrue(addDealCommand1.equals(addDealCommand1Copy));

        // different types -> returns false
        assertFalse(addDealCommand1.equals(1));

        // null -> returns false
        assertFalse(addDealCommand1.equals(null));

        // different deal -> returns false
        assertFalse(addDealCommand1.equals(addDealCommand2));
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
            return this.deal.equals(deal);
        }
        @Override
        public ObservableList<Client> getFilteredClientList() {
            // Return a list with enough elements to pass the index checks
            return FXCollections.observableArrayList();
        }
        @Override
        public ObservableList<Deal> getFilteredDealList() {
            return FXCollections.observableArrayList(deal);
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
        
        @Override
        public ObservableList<Client> getFilteredClientList() {
            // Return a list with enough elements to pass the index checks
            return FXCollections.observableArrayList(
                new Client(), new Client(), new Client(), new Client(), new Client()
            );
        }
        
        @Override
        public ObservableList<Deal> getFilteredDealList() {
            return FXCollections.observableArrayList();
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
            return this.deal.equals(deal);
        }
        
        @Override
        public ObservableList<Client> getFilteredClientList() {
            // Return a list with enough elements to pass the index checks
            return FXCollections.observableArrayList(
                new Client(), new Client(), new Client(), new Client(), new Client()
            );
        }
        
        @Override
        public ObservableList<Deal> getFilteredDealList() {
            return FXCollections.observableArrayList(deal);
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
            return dealsAdded.stream().anyMatch(deal::equals);
        }

        @Override
        public void addDeal(Deal deal) {
            requireNonNull(deal);
            dealsAdded.add(deal);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
        
        @Override
        public ObservableList<Client> getFilteredClientList() {
            // Return a list with enough elements to pass the index checks
            return FXCollections.observableArrayList(
                new Client(), new Client(), new Client(), new Client(), new Client()
            );
        }
        
        @Override
        public ObservableList<Deal> getFilteredDealList() {
            return FXCollections.observableArrayList(dealsAdded);
        }
    }
}
