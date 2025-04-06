package seedu.address.model.deal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.client.ClientName;
import seedu.address.model.commons.Price;
import seedu.address.model.property.PropertyName;
import seedu.address.testutil.DealBuilder;

public class DealTest {

    @Test
    public void constructor_nullParameters_throwsNullPointerException() {
        // Test primary constructor
        assertThrows(NullPointerException.class, () -> new Deal(null,
                new ClientName("John Doe"),
                new ClientName("Jane Smith"),
                new Price(100L)));

        assertThrows(NullPointerException.class, () -> new Deal(
                new PropertyName("Ocean View"),
                null,
                new ClientName("Jane Smith"),
                new Price(100L)));

        assertThrows(NullPointerException.class, () -> new Deal(
                new PropertyName("Ocean View"),
                new ClientName("John Doe"),
                null,
                new Price(100L)));

        assertThrows(NullPointerException.class, () -> new Deal(
                new PropertyName("Ocean View"),
                new ClientName("John Doe"),
                new ClientName("Jane Smith"),
                null));

        // Test secondary constructor
        assertThrows(NullPointerException.class, () -> new Deal(null,
                new ClientName("John Doe"),
                new ClientName("Jane Smith"),
                new Price(100L),
                DealStatus.PENDING));

        assertThrows(NullPointerException.class, () -> new Deal(
                new PropertyName("Ocean View"),
                null,
                new ClientName("Jane Smith"),
                new Price(100L),
                DealStatus.PENDING));

        assertThrows(NullPointerException.class, () -> new Deal(
                new PropertyName("Ocean View"),
                new ClientName("John Doe"),
                null,
                new Price(100L),
                DealStatus.PENDING));

        assertThrows(NullPointerException.class, () -> new Deal(
                new PropertyName("Ocean View"),
                new ClientName("John Doe"),
                new ClientName("Jane Smith"),
                null,
                DealStatus.PENDING));

        assertThrows(NullPointerException.class, () -> new Deal(
                new PropertyName("Ocean View"),
                new ClientName("John Doe"),
                new ClientName("Jane Smith"),
                new Price(100L),
                null));
    }

    @Test
    public void constructor_defaultStatus_success() {
        Deal deal = new Deal(
                new PropertyName("Ocean View"),
                new ClientName("John Doe"),
                new ClientName("Jane Smith"),
                new Price(100L));

        assertEquals(DealStatus.PENDING, deal.getStatus());
    }

    @Test
    public void constructor_customStatus_success() {
        Deal deal = new Deal(
                new PropertyName("Ocean View"),
                new ClientName("John Doe"),
                new ClientName("Jane Smith"),
                new Price(100L),
                DealStatus.CLOSED);

        assertEquals(DealStatus.CLOSED, deal.getStatus());
    }

    @Test
    public void getters_success() {
        PropertyName propertyName = new PropertyName("Ocean View");
        ClientName buyer = new ClientName("John Doe");
        ClientName seller = new ClientName("Jane Smith");
        Price price = new Price(100L);
        DealStatus status = DealStatus.OPEN;

        Deal deal = new Deal(propertyName, buyer, seller, price, status);

        assertEquals(propertyName, deal.getPropertyName());
        assertEquals(buyer, deal.getBuyer());
        assertEquals(seller, deal.getSeller());
        assertEquals(price, deal.getPrice());
        assertEquals(status, deal.getStatus());
    }

    @Test
    public void isSameDeal() {
        // Same object -> returns true
        Deal deal = new DealBuilder().build();
        assertTrue(deal.isSameDeal(deal));

        // Null -> returns false
        assertFalse(deal.isSameDeal(null));

        // Same property, buyer, and seller -> returns true
        Deal otherDeal = new DealBuilder()
                .withPropertyName("Maple Villa Condominium")
                .withBuyer("Amy Bee")
                .withSeller("John Doe")
                .withPrice(200L)
                .withStatus(DealStatus.CLOSED)
                .build();
        assertTrue(deal.isSameDeal(otherDeal));

        // Different property -> returns false
        otherDeal = new DealBuilder().withPropertyName("Different Property").build();
        assertFalse(deal.isSameDeal(otherDeal));

        // Different buyer -> returns false
        otherDeal = new DealBuilder().withBuyer("Different Buyer").build();
        assertFalse(deal.isSameDeal(otherDeal));

        // Different seller -> returns false
        otherDeal = new DealBuilder().withSeller("Different Seller").build();
        assertFalse(deal.isSameDeal(otherDeal));
    }

    @Test
    public void equals() {
        // Same object -> returns true
        Deal deal = new DealBuilder().build();
        assertTrue(deal.equals(deal));

        // Null -> returns false
        assertFalse(deal.equals(null));

        // Different type -> returns false
        assertFalse(deal.equals(new Object()));

        // Same values -> returns true
        Deal dealCopy = new DealBuilder()
                .withPropertyName("Maple Villa Condominium")
                .withBuyer("Amy Bee")
                .withSeller("John Doe")
                .withPrice(100L)
                .withStatus(DealStatus.PENDING)
                .build();
        assertTrue(deal.equals(dealCopy));

        // Different property -> returns false
        Deal otherDeal = new DealBuilder().withPropertyName("Different Property").build();
        assertFalse(deal.equals(otherDeal));

        // Different buyer -> returns false
        otherDeal = new DealBuilder().withBuyer("Different Buyer").build();
        assertFalse(deal.equals(otherDeal));

        // Different seller -> returns false
        otherDeal = new DealBuilder().withSeller("Different Seller").build();
        assertFalse(deal.equals(otherDeal));

        // Different price -> returns false
        otherDeal = new DealBuilder().withPrice(200L).build();
        assertFalse(deal.equals(otherDeal));

        // Different status -> returns false
        otherDeal = new DealBuilder().withStatus(DealStatus.CLOSED).build();
        assertFalse(deal.equals(otherDeal));
    }

    @Test
    public void toStringMethod() {
        Deal deal = new DealBuilder().build();
        String expected = "Property: Maple Villa Condominium Buyer: Amy Bee Seller: John Doe "
                + "Price: 100 Status: PENDING";
        assertEquals(expected, deal.toString());
    }
}
