package seedu.address.logic.commands.deal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.deal.UpdateDealCommand.UpdateDealDescriptor;

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
import seedu.address.testutil.DealBuilder;
import seedu.address.testutil.UpdateDealDescriptorBuilder;

public class UpdateDealCommandTest {

    private static final String VALID_PROPERTY_NAME = "Palm Spring";
    private static final String VALID_PROPERTY_NAME_2 = "Maple Villa";
    private static final long VALID_PRICE = 200L;
    private static final long VALID_PRICE_2 = 300L;
    private static final long PRICE_LIMIT = 999_999L;
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

        // Use DealBuilder instead of direct Deal constructor
        Deal testDeal = new DealBuilder()
                .withPropertyName(VALID_PROPERTY_NAME)
                .withBuyer(buyerName.toString())
                .withSeller(sellerName.toString())
                .withPrice(VALID_PRICE)
                .withStatus(VALID_STATUS)
                .build();
        model.addDeal(testDeal);
        model.addClient(buyer);
        model.addClient(seller);

        Index dealIndex = INDEX_FIRST;
        UpdateDealDescriptor descriptor = new UpdateDealDescriptorBuilder()
                .withPropertyName(VALID_PROPERTY_NAME_2)
                .withPrice(VALID_PRICE_2)
                .withStatus(VALID_STATUS_2)
                .build();
        UpdateDealCommand updateCommand = new UpdateDealCommand(dealIndex, descriptor);

        String expectedMessage = UpdateDealCommand.MESSAGE_UPDATE_DEAL_SUCCESS;

        Deal updatedDeal = new DealBuilder()
                .withPropertyName(VALID_PROPERTY_NAME_2)
                .withBuyer(buyerName.toString())
                .withSeller(sellerName.toString())
                .withPrice(VALID_PRICE_2)
                .withStatus(VALID_STATUS_2)
                .build();

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

        Deal testDeal = new DealBuilder()
                .withPropertyName(VALID_PROPERTY_NAME)
                .withBuyer(buyerName.toString())
                .withSeller(sellerName.toString())
                .withPrice(VALID_PRICE)
                .withStatus(VALID_STATUS)
                .build();
        model.addDeal(testDeal);
        model.addClient(buyer);
        model.addClient(seller);

        Index dealIndex = INDEX_FIRST;
        UpdateDealDescriptor descriptor = new UpdateDealDescriptorBuilder()
                .withStatus(VALID_STATUS_2)
                .build();
        UpdateDealCommand updateCommand = new UpdateDealCommand(dealIndex, descriptor);

        String expectedMessage = String.format(UpdateDealCommand.MESSAGE_UPDATE_DEAL_SUCCESS, VALID_STATUS_2);

        Deal updatedDeal = new DealBuilder()
                .withPropertyName(VALID_PROPERTY_NAME)
                .withBuyer(buyerName.toString())
                .withSeller(sellerName.toString())
                .withPrice(VALID_PRICE)
                .withStatus(VALID_STATUS_2)
                .build();

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

        Deal testDeal = new DealBuilder()
                .withPropertyName(VALID_PROPERTY_NAME)
                .withBuyer(buyerName.toString())
                .withSeller(sellerName.toString())
                .withPrice(VALID_PRICE)
                .withStatus(VALID_STATUS)
                .build();
        model.addDeal(testDeal);

        UpdateDealDescriptor descriptor = new UpdateDealDescriptorBuilder().build();
        UpdateDealCommand updateCommand = new UpdateDealCommand(INDEX_FIRST, descriptor);

        assertThrows(CommandException.class, () -> updateCommand.execute(model),
                UpdateDealCommand.MESSAGE_NO_CHANGES);
    }

    @Test
    public void execute_invalidDealIndex_throwsCommandException() {
        TestModelStub model = new TestModelStub();

        ClientName buyerName = new ClientName("John");
        ClientName sellerName = new ClientName("Jane");

        Deal testDeal = new DealBuilder()
                .withPropertyName(VALID_PROPERTY_NAME)
                .withBuyer(buyerName.toString())
                .withSeller(sellerName.toString())
                .withPrice(VALID_PRICE)
                .withStatus(VALID_STATUS)
                .build();
        model.addDeal(testDeal);

        // Deal index not in model
        Index outOfBoundIndex = INDEX_SECOND;
        UpdateDealDescriptor descriptor = new UpdateDealDescriptorBuilder()
                .withPropertyName(VALID_PROPERTY_NAME_2)
                .build();
        UpdateDealCommand updateCommand = new UpdateDealCommand(outOfBoundIndex, descriptor);

        assertThrows(CommandException.class, () -> updateCommand.execute(model),
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

        Deal testDeal = new DealBuilder()
                .withPropertyName(VALID_PROPERTY_NAME)
                .withBuyer(buyerName.toString())
                .withSeller(sellerName.toString())
                .withPrice(VALID_PRICE)
                .withStatus(VALID_STATUS)
                .build();
        model.addDeal(testDeal);
        model.addClient(buyer);
        model.addClient(seller);

        UpdateDealDescriptor descriptor = new UpdateDealDescriptorBuilder()
                .withPrice(PRICE_LIMIT + 1L)
                .build();
        UpdateDealCommand updateCommand = new UpdateDealCommand(INDEX_FIRST, descriptor);

        assertThrows(CommandException.class, () -> updateCommand.execute(model),
                Price.MESSAGE_CONSTRAINTS);
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

        Deal testDeal = new DealBuilder()
                .withPropertyName(VALID_PROPERTY_NAME)
                .withBuyer(clientName.toString())
                .withSeller("Jane")
                .withPrice(VALID_PRICE)
                .withStatus(VALID_STATUS)
                .build();
        model.addDeal(testDeal);

        // Both buyer and seller are the same index (same client)
        UpdateDealDescriptor descriptor = new UpdateDealDescriptorBuilder()
                .withBuyer(INDEX_FIRST)
                .withSeller(INDEX_FIRST)
                .build();
        UpdateDealCommand updateCommand = new UpdateDealCommand(INDEX_FIRST, descriptor);

        assertThrows(CommandException.class, () -> updateCommand.execute(model),
                UpdateDealCommand.MESSAGE_SAME_BUYER_SELLER);
    }

    @Test
    public void equals() {
        UpdateDealDescriptor descriptor1 = new UpdateDealDescriptorBuilder()
                .withPropertyName(VALID_PROPERTY_NAME)
                .withBuyer(INDEX_FIRST)
                .withSeller(INDEX_SECOND)
                .withPrice(VALID_PRICE_2)
                .withStatus(VALID_STATUS)
                .build();
        final UpdateDealCommand standardCommand = new UpdateDealCommand(INDEX_FIRST, descriptor1);

        UpdateDealDescriptor descriptor2 = new UpdateDealDescriptorBuilder()
                .withPropertyName(VALID_PROPERTY_NAME)
                .withBuyer(INDEX_FIRST)
                .withSeller(INDEX_SECOND)
                .withPrice(VALID_PRICE_2)
                .withStatus(VALID_STATUS)
                .build();
        UpdateDealCommand commandWithSameValues = new UpdateDealCommand(INDEX_FIRST, descriptor2);
        assertTrue(standardCommand.equals(commandWithSameValues));

        UpdateDealDescriptor differentDescriptor = new UpdateDealDescriptorBuilder()
                .withPropertyName(VALID_PROPERTY_NAME_2)
                .withBuyer(INDEX_FIRST)
                .withSeller(INDEX_SECOND)
                .withPrice(VALID_PRICE_2)
                .withStatus(VALID_STATUS)
                .build();
        assertFalse(standardCommand.equals(new UpdateDealCommand(INDEX_FIRST, differentDescriptor)));
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

