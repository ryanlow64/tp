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
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ModelStub;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.client.Client;
import seedu.address.model.client.ClientName;
import seedu.address.model.client.Email;
import seedu.address.model.client.Phone;
import seedu.address.model.commons.Address;
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

        // Create client objects
        ClientName buyerName = new ClientName("John Doe");
        ClientName sellerName = new ClientName("Jane Smith");
        Client buyer = new Client(buyerName, new Phone("91234567"),
                new Email("john@example.com"), new Address("123 Buyer St"));
        Client seller = new Client(sellerName, new Phone("91234568"),
                new Email("jane@example.com"), new Address("456 Seller St"));

        // Use indices
        Index buyerId = Index.fromOneBased(1);
        Index sellerId = Index.fromOneBased(2);

        // Add clients to model
        modelStub.addClient(buyer);
        modelStub.addClient(seller);

        Price price = new Price(500000L);
        DealStatus status = DealStatus.PENDING;

        Deal validDeal = new Deal(propertyName, buyerName, sellerName, price, status);
        CommandResult commandResult = new AddDealCommand(propertyName, buyerId, sellerId, price, status)
                .execute(modelStub);

        assertEquals(String.format(AddDealCommand.MESSAGE_SUCCESS,
                propertyName.toString(),
                buyerName.toString(),
                sellerName.toString(),
                price.value,
                status),
                commandResult.getFeedbackToUser());
        assertEquals(1, modelStub.dealsAdded.size());
        assertTrue(modelStub.dealsAdded.get(0).isSameDeal(validDeal));
    }

    @Test
    public void execute_duplicateDeal_throwsCommandException() {
        PropertyName propertyName = new PropertyName("Test Property");
        ClientName buyerName = new ClientName("John Doe");
        ClientName sellerName = new ClientName("Jane Smith");
        Client buyer = new Client(buyerName, new Phone("91234567"),
                new Email("john@example.com"), new Address("123 Buyer St"));
        Client seller = new Client(sellerName, new Phone("91234568"),
                new Email("jane@example.com"), new Address("456 Seller St"));

        // Use indices
        Index buyerId = Index.fromOneBased(1);
        Index sellerId = Index.fromOneBased(2);

        Price price = new Price(500000);
        DealStatus status = DealStatus.PENDING;

        Deal validDeal = new Deal(propertyName, buyerName, sellerName, price, status);
        AddDealCommand addDealCommand = new AddDealCommand(propertyName, buyerId, sellerId, price, status);
        ModelStubWithDeal modelStub = new ModelStubWithDeal(validDeal);
        modelStub.addClient(buyer);
        modelStub.addClient(seller);

        CommandException exception = assertThrows(CommandException.class, (
                ) -> addDealCommand.execute(modelStub));
        assertEquals(AddDealCommand.MESSAGE_DUPLICATE_DEAL, exception.getMessage());
    }

    @Test
    public void execute_sameBuyerAndSeller_throwsCommandException() {
        PropertyName propertyName = new PropertyName("Test Property");
        ClientName personName = new ClientName("Same Person");
        Client person = new Client(personName, new Phone("91234567"),
                new Email("same@example.com"), new Address("123 Same St"));

        // Use same index for buyer and seller
        Index personId = Index.fromOneBased(1);

        Price price = new Price(500000);
        DealStatus status = DealStatus.PENDING;

        AddDealCommand addDealCommand = new AddDealCommand(propertyName, personId, personId, price, status);
        ModelStubWithClients modelStub = new ModelStubWithClients();
        modelStub.addClient(person);

        CommandException exception = assertThrows(CommandException.class, (
                ) -> addDealCommand.execute(modelStub));
        assertEquals(AddDealCommand.MESSAGE_SAME_BUYER_SELLER, exception.getMessage());
    }

    @Test
    public void execute_propertyAlreadyInDeal_throwsCommandException() {
        PropertyName propertyName = new PropertyName("Test Property");
        // First set of clients
        ClientName buyer1Name = new ClientName("John Doe");
        ClientName seller1Name = new ClientName("Jane Smith");
        Client buyer1 = new Client(buyer1Name, new Phone("91234567"),
                new Email("john@example.com"), new Address("123 Buyer St"));
        Client seller1 = new Client(seller1Name, new Phone("91234568"),
                new Email("jane@example.com"), new Address("456 Seller St"));

        // Second set of clients
        ClientName buyer2Name = new ClientName("Alice Brown");
        ClientName seller2Name = new ClientName("Bob Wilson");
        Client buyer2 = new Client(buyer2Name, new Phone("91234569"),
                new Email("alice@example.com"), new Address("789 Buyer St"));
        Client seller2 = new Client(seller2Name, new Phone("91234570"),
                new Email("bob@example.com"), new Address("012 Seller St"));

        // Use indices
        Index buyer1Id = Index.fromOneBased(1);
        Index seller1Id = Index.fromOneBased(2);
        Index buyer2Id = Index.fromOneBased(3);
        Index seller2Id = Index.fromOneBased(4);

        Price price = new Price(500000);
        DealStatus status = DealStatus.PENDING;

        Deal existingDeal = new Deal(propertyName, buyer1Name, seller1Name, price, status);
        AddDealCommand addDealCommand = new AddDealCommand(propertyName, buyer2Id, seller2Id, price, status);
        ModelStubWithDealAndClients modelStub = new ModelStubWithDealAndClients(existingDeal);
        modelStub.addClient(buyer1);
        modelStub.addClient(seller1);
        modelStub.addClient(buyer2);
        modelStub.addClient(seller2);

        CommandException exception = assertThrows(CommandException.class, (
                ) -> addDealCommand.execute(modelStub));
        assertEquals(AddDealCommand.MESSAGE_PROPERTY_ALREADY_IN_DEAL, exception.getMessage());
    }

    @Test
    public void equals() {
        PropertyName property1 = new PropertyName("Property 1");
        PropertyName property2 = new PropertyName("Property 2");
        // Use indices for clients
        Index buyer1Id = Index.fromOneBased(1);
        Index buyer2Id = Index.fromOneBased(2);
        Index seller1Id = Index.fromOneBased(3);
        Index seller2Id = Index.fromOneBased(4);
        Price price1 = new Price(500000);
        Price price2 = new Price(600000);
        DealStatus status1 = DealStatus.PENDING;
        DealStatus status2 = DealStatus.CLOSED;

        AddDealCommand addFirstCommand = new AddDealCommand(property1, buyer1Id, seller1Id, price1, status1);
        AddDealCommand addSecondCommand = new AddDealCommand(property2, buyer2Id, seller2Id, price2, status2);

        // same object -> returns true
        assertTrue(addFirstCommand.equals(addFirstCommand));

        // same values -> returns true
        AddDealCommand addFirstCommandCopy = new AddDealCommand(property1, buyer1Id, seller1Id, price1, status1);
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
        private final ArrayList<Client> clients = new ArrayList<>();

        ModelStubWithDeal(Deal deal) {
            requireNonNull(deal);
            this.deal = deal;
        }

        @Override
        public boolean hasDeal(Deal deal) {
            requireNonNull(deal);
            return this.deal.isSameDeal(deal);
        }

        @Override
        public void addClient(Client client) {
            requireNonNull(client);
            clients.add(client);
        }

        @Override
        public ObservableList<Client> getFilteredClientList() {
            return FXCollections.observableArrayList(clients);
        }
    }

    /**
     * A Model stub that contains clients but no deals.
     */
    private class ModelStubWithClients extends ModelStub {
        private final ArrayList<Client> clients = new ArrayList<>();

        @Override
        public boolean hasDeal(Deal deal) {
            return false;
        }

        @Override
        public void addClient(Client client) {
            requireNonNull(client);
            clients.add(client);
        }

        @Override
        public ObservableList<Client> getFilteredClientList() {
            return FXCollections.observableArrayList(clients);
        }
    }

    /**
     * A Model stub that contains both clients and a deal.
     */
    private class ModelStubWithDealAndClients extends ModelStub {
        private final Deal deal;
        private final ArrayList<Client> clients = new ArrayList<>();

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

        @Override
        public void addClient(Client client) {
            requireNonNull(client);
            clients.add(client);
        }

        @Override
        public ObservableList<Client> getFilteredClientList() {
            return FXCollections.observableArrayList(clients);
        }
    }

    /**
     * A Model stub that always accepts the deal being added.
     */
    private class ModelStubAcceptingDealAdded extends ModelStub {
        final ArrayList<Deal> dealsAdded = new ArrayList<>();
        final ArrayList<Client> clients = new ArrayList<>();

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
        public void addClient(Client client) {
            requireNonNull(client);
            clients.add(client);
        }

        @Override
        public ObservableList<Deal> getFilteredDealList() {
            return FXCollections.observableArrayList(dealsAdded);
        }

        @Override
        public ObservableList<Client> getFilteredClientList() {
            return FXCollections.observableArrayList(clients);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
