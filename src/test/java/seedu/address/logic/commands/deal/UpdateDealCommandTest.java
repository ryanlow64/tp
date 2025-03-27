package seedu.address.logic.commands.deal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

public class UpdateDealCommandTest {

    private static final PropertyName VALID_PROPERTY_NAME = new PropertyName("Palm Spring");
    private static final PropertyName VALID_PROPERTY_NAME_2 = new PropertyName("Maple Villa");
    private static final Price VALID_PRICE = new Price(200L);
    private static final Price VALID_PRICE_2 = new Price(300L);
    private static final Price PRICE_LIMIT = new Price(999L);
    private static final DealStatus VALID_STATUS = DealStatus.PENDING;
    private static final DealStatus VALID_STATUS_2 = DealStatus.CLOSED;
    private static final Index INDEX_FIRST = Index.fromOneBased(1);
    private static final Index INDEX_SECOND = Index.fromOneBased(2);

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        TestModelStub model = new TestModelStub();

        Client buyer = new Client(
                new ClientName("John"),
                new Phone("12345678"),
                new Email("john@example.com"),
                new Address("Blk 123"));
        Client seller = new Client(
                new ClientName("Jane"),
                new Phone("87654321"),
                new Email("jane@example.com"),
                new Address("Blk 456"));

        ClientName buyerName = buyer.getClientName();
        ClientName sellerName = seller.getClientName();

        Deal testDeal = new Deal(VALID_PROPERTY_NAME, buyerName, sellerName, VALID_PRICE, VALID_STATUS);
        model.addDeal(testDeal);
        model.addClient(buyer);
        model.addClient(seller);

        Index dealIndex = INDEX_FIRST;
        UpdateDealCommand updateCommand = new UpdateDealCommand(dealIndex,
                                                                Optional.of(VALID_PROPERTY_NAME_2),
                                                                Optional.empty(),
                                                                Optional.empty(),
                                                                Optional.of(VALID_PRICE_2),
                                                                Optional.of(VALID_STATUS_2));

        String expectedMessage = UpdateDealCommand.MESSAGE_SUCCESS;

        Deal updatedDeal = new Deal(VALID_PROPERTY_NAME_2, buyerName, sellerName, VALID_PRICE_2, VALID_STATUS_2);

        TestModelStub expectedModel = new TestModelStub();
        expectedModel.addDeal(updatedDeal);
        expectedModel.addClient(buyer);
        expectedModel.addClient(seller);

        // Create a modified assertCommandSuccess to avoid model comparison
        try {
            CommandResult result = updateCommand.execute(model);
            assertEquals(expectedMessage, result.getFeedbackToUser());
            assertEquals(FXCollections.observableList(expectedModel.getFilteredDealList()),
                        FXCollections.observableList(model.getFilteredDealList()));
        } catch (CommandException e) {
            fail("Command should not fail with: " + e.getMessage());
        }
    }

    @Test
    public void execute_statusOnlySpecified_success() {
        TestModelStub model = new TestModelStub();

        Client buyer = new Client(
                new ClientName("John"),
                new Phone("12345678"),
                new Email("john@example.com"),
                new Address("Blk 123"));
        Client seller = new Client(
                new ClientName("Jane"),
                new Phone("87654321"),
                new Email("jane@example.com"),
                new Address("Blk 456"));

        ClientName buyerName = buyer.getClientName();
        ClientName sellerName = seller.getClientName();

        Deal testDeal = new Deal(VALID_PROPERTY_NAME, buyerName, sellerName, VALID_PRICE, VALID_STATUS);
        model.addDeal(testDeal);
        model.addClient(buyer);
        model.addClient(seller);

        Index dealIndex = INDEX_FIRST;
        UpdateDealCommand updateCommand = new UpdateDealCommand(dealIndex,
                                                                Optional.empty(),
                                                                Optional.empty(),
                                                                Optional.empty(),
                                                                Optional.empty(),
                                                                Optional.of(VALID_STATUS_2));

        String expectedMessage = String.format(UpdateDealCommand.MESSAGE_STATUS_SUCCESS, VALID_STATUS_2);

        Deal updatedDeal = new Deal(VALID_PROPERTY_NAME, buyerName, sellerName, VALID_PRICE, VALID_STATUS_2);

        TestModelStub expectedModel = new TestModelStub();
        expectedModel.addDeal(updatedDeal);
        expectedModel.addClient(buyer);
        expectedModel.addClient(seller);

        // Create a modified assertCommandSuccess to avoid model comparison
        try {
            CommandResult result = updateCommand.execute(model);
            assertEquals(expectedMessage, result.getFeedbackToUser());
            assertEquals(FXCollections.observableList(expectedModel.getFilteredDealList()),
                        FXCollections.observableList(model.getFilteredDealList()));
        } catch (CommandException e) {
            fail("Command should not fail with: " + e.getMessage());
        }
    }

    @Test
    public void execute_noFieldsSpecified_throwsCommandException() {
        TestModelStub model = new TestModelStub();

        ClientName buyerName = new ClientName("John");
        ClientName sellerName = new ClientName("Jane");

        Deal testDeal = new Deal(VALID_PROPERTY_NAME, buyerName, sellerName, VALID_PRICE, VALID_STATUS);
        model.addDeal(testDeal);

        UpdateDealCommand updateCommand = new UpdateDealCommand(INDEX_FIRST,
                                                              Optional.empty(),
                                                              Optional.empty(),
                                                              Optional.empty(),
                                                              Optional.empty(),
                                                              Optional.empty());

        assertThrows(CommandException.class, (
                     ) -> updateCommand.execute(model),
                     UpdateDealCommand.MESSAGE_NO_CHANGES);
    }

    @Test
    public void execute_invalidDealIndex_throwsCommandException() {
        TestModelStub model = new TestModelStub();

        ClientName buyerName = new ClientName("John");
        ClientName sellerName = new ClientName("Jane");

        Deal testDeal = new Deal(VALID_PROPERTY_NAME, buyerName, sellerName, VALID_PRICE, VALID_STATUS);
        model.addDeal(testDeal);

        // Deal index not in model
        Index outOfBoundIndex = INDEX_SECOND;
        UpdateDealCommand updateCommand = new UpdateDealCommand(outOfBoundIndex,
                                                              Optional.of(VALID_PROPERTY_NAME_2),
                                                              Optional.empty(),
                                                              Optional.empty(),
                                                              Optional.empty(),
                                                              Optional.empty());

        assertThrows(CommandException.class, (
                ) -> updateCommand.execute(model),
                     UpdateDealCommand.MESSAGE_INVALID_DEAL_ID);
    }

    @Test
    public void execute_priceExceedsLimit_throwsCommandException() {
        TestModelStub model = new TestModelStub();

        Client buyer = new Client(
                new ClientName("John"),
                new Phone("12345678"),
                new Email("john@example.com"),
                new Address("Blk 123"));
        Client seller = new Client(
                new ClientName("Jane"),
                new Phone("87654321"),
                new Email("jane@example.com"),
                new Address("Blk 456"));

        ClientName buyerName = buyer.getClientName();
        ClientName sellerName = seller.getClientName();

        Deal testDeal = new Deal(VALID_PROPERTY_NAME, buyerName, sellerName, VALID_PRICE, VALID_STATUS);
        model.addDeal(testDeal);
        model.addClient(buyer);
        model.addClient(seller);

        // Setting a price that exceeds the limit - using the literal value from UpdateDealCommand
        Price priceExceedsLimit = new Price(999_990_001L); // Just above the 999_990_000 limit
        UpdateDealCommand updateCommand = new UpdateDealCommand(INDEX_FIRST,
                                                             Optional.empty(),
                                                             Optional.empty(),
                                                             Optional.empty(),
                                                             Optional.of(priceExceedsLimit),
                                                             Optional.empty());

        assertThrows(CommandException.class, (
                     ) -> updateCommand.execute(model),
                     UpdateDealCommand.MESSAGE_PRICE_EXCEEDS_LIMIT);
    }

    @Test
    public void execute_buyerSellerSamePerson_throwsCommandException() {
        TestModelStubWithClients model = new TestModelStubWithClients();

        Client client = new Client(
                new ClientName("John"),
                new Phone("12345678"),
                new Email("john@example.com"),
                new Address("Blk 123"));
        model.addClient(client);

        ClientName clientName = client.getClientName();

        Deal testDeal = new Deal(VALID_PROPERTY_NAME, clientName, new ClientName("Jane"), VALID_PRICE, VALID_STATUS);
        model.addDeal(testDeal);

        // Both buyer and seller are the same index (same client)
        UpdateDealCommand updateCommand = new UpdateDealCommand(INDEX_FIRST,
                                                             Optional.empty(),
                                                             Optional.of(INDEX_FIRST),
                                                             Optional.of(INDEX_FIRST),
                                                             Optional.empty(),
                                                             Optional.empty());

        assertThrows(CommandException.class, (
                ) -> updateCommand.execute(model),
                     UpdateDealCommand.MESSAGE_SAME_BUYER_SELLER);
    }

    @Test
    public void equals() {
        final UpdateDealCommand standardCommand = new UpdateDealCommand(INDEX_FIRST,
                                                                     Optional.of(VALID_PROPERTY_NAME),
                                                                     Optional.of(INDEX_FIRST),
                                                                     Optional.of(INDEX_SECOND),
                                                                     Optional.of(VALID_PRICE),
                                                                     Optional.of(VALID_STATUS));

        // same values -> returns true
        UpdateDealCommand commandWithSameValues = new UpdateDealCommand(INDEX_FIRST,
                                                                     Optional.of(VALID_PROPERTY_NAME),
                                                                     Optional.of(INDEX_FIRST),
                                                                     Optional.of(INDEX_SECOND),
                                                                     Optional.of(VALID_PRICE),
                                                                     Optional.of(VALID_STATUS));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new AddDealCommand(
                Index.fromOneBased(1),
                Index.fromOneBased(1),
                new Price(1000L),
                DealStatus.PENDING)));

        // different index -> returns false
        assertFalse(standardCommand.equals(new UpdateDealCommand(INDEX_SECOND,
                                                              Optional.of(VALID_PROPERTY_NAME),
                                                              Optional.of(INDEX_FIRST),
                                                              Optional.of(INDEX_SECOND),
                                                              Optional.of(VALID_PRICE),
                                                              Optional.of(VALID_STATUS))));

        // different property name -> returns false
        assertFalse(standardCommand.equals(new UpdateDealCommand(INDEX_FIRST,
                                                              Optional.of(VALID_PROPERTY_NAME_2),
                                                              Optional.of(INDEX_FIRST),
                                                              Optional.of(INDEX_SECOND),
                                                              Optional.of(VALID_PRICE),
                                                              Optional.of(VALID_STATUS))));

        // different price -> returns false
        assertFalse(standardCommand.equals(new UpdateDealCommand(INDEX_FIRST,
                                                              Optional.of(VALID_PROPERTY_NAME),
                                                              Optional.of(INDEX_FIRST),
                                                              Optional.of(INDEX_SECOND),
                                                              Optional.of(VALID_PRICE_2),
                                                              Optional.of(VALID_STATUS))));

        // different status -> returns false
        assertFalse(standardCommand.equals(new UpdateDealCommand(INDEX_FIRST,
                                                              Optional.of(VALID_PROPERTY_NAME),
                                                              Optional.of(INDEX_FIRST),
                                                              Optional.of(INDEX_SECOND),
                                                              Optional.of(VALID_PRICE),
                                                              Optional.of(VALID_STATUS_2))));
    }

    /**
     * A Model stub that contains a single deal and supports adding and updating deals.
     */
    private static class TestModelStub extends ModelStub {
        private final List<Deal> deals = new ArrayList<>();
        private final List<Client> clients = new ArrayList<>();

        @Override
        public boolean hasDeal(Deal deal) {
            return deals.contains(deal);
        }

        @Override
        public void addDeal(Deal deal) {
            deals.add(deal);
        }

        @Override
        public void setDeal(Deal target, Deal editedDeal) {
            int index = deals.indexOf(target);
            if (index >= 0) {
                deals.set(index, editedDeal);
            }
        }

        public Deal getDeal(Index index) {
            if (index.getZeroBased() >= deals.size()) {
                throw new IndexOutOfBoundsException();
            }
            return deals.get(index.getZeroBased());
        }

        @Override
        public ObservableList<Deal> getFilteredDealList() {
            return FXCollections.observableList(deals);
        }

        @Override
        public void updateFilteredDealList(java.util.function.Predicate<Deal> predicate) {
            // Do nothing since this is a stub
        }

        @Override
        public boolean hasClient(Client client) {
            return clients.contains(client);
        }

        @Override
        public void addClient(Client client) {
            clients.add(client);
        }

        @Override
        public ObservableList<Client> getFilteredClientList() {
            return FXCollections.observableList(clients);
        }

        public Client getClient(Index index) {
            if (index.getZeroBased() >= clients.size()) {
                throw new IndexOutOfBoundsException();
            }
            return clients.get(index.getZeroBased());
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            AddressBook addressBook = new AddressBook();
            for (Client client : clients) {
                addressBook.addClient(client);
            }
            for (Deal deal : deals) {
                addressBook.addDeal(deal);
            }
            return addressBook;
        }
    }

    /**
     * A Model stub with clients but no deals.
     */
    private static class TestModelStubWithClients extends TestModelStub {
    }
}

