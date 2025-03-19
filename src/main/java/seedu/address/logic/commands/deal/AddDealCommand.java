package seedu.address.logic.commands.deal;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.client.ClientName;
import seedu.address.model.commons.Price;
import seedu.address.model.deal.Deal;
import seedu.address.model.deal.DealStatus;
import seedu.address.model.property.PropertyName;

/**
 * Adds a deal to the address book.
 */
public class AddDealCommand extends Command {

    public static final String COMMAND_WORD = "add_deal";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a property deal to the address book. "
            + "Parameters: "
            + PREFIX_PROPERTY_NAME + "PROPERTY_NAME "
            + PREFIX_BUYER + "BUYER_ID "
            + PREFIX_SELLER + "SELLER_ID "
            + PREFIX_PRICE + "PRICE "
            + "[" + PREFIX_STATUS + "STATUS]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PROPERTY_NAME + "Sunset Villa "
            + PREFIX_BUYER + "1 "
            + PREFIX_SELLER + "2 "
            + PREFIX_PRICE + "100 "
            + PREFIX_STATUS + "closed";

    public static final String MESSAGE_SUCCESS = "New deal added successfully:"
            + " Property: %1$s, Buyer: %2$s, Seller: %3$s, Price $%4$d, Status %5$s";
    public static final String MESSAGE_DUPLICATE_DEAL = "This deal already exists in the address book";
    public static final String MESSAGE_INVALID_PROPERTY = "Invalid property name.";
    public static final String MESSAGE_INVALID_BUYER_ID = "Invalid buyer ID.";
    public static final String MESSAGE_INVALID_SELLER_ID = "Invalid seller ID.";
    public static final String MESSAGE_SAME_BUYER_SELLER = "Buyer and seller cannot be the same person.";
    public static final String MESSAGE_PROPERTY_ALREADY_IN_DEAL = "This property is already involved in another deal.";
    public static final String MESSAGE_PRICE_EXCEEDS_LIMIT = "Price exceeds the limit of 999.99";

    private final PropertyName propertyName;
    private final Index buyerId;
    private final Index sellerId;
    private final Price price;
    private final DealStatus status;

    /**
     * Creates an AddDealCommand to add a deal with the specified details
     */
    public AddDealCommand(PropertyName propertyName, Index buyerId, Index sellerId,
                          Price price, DealStatus status) {
        requireNonNull(propertyName);
        requireNonNull(buyerId);
        requireNonNull(sellerId);
        requireNonNull(price);
        requireNonNull(status);
        this.propertyName = propertyName;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.price = price;
        this.status = status;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Validate property name
        if (!PropertyName.isValidPropertyName(propertyName.toString())) {
            throw new CommandException(MESSAGE_INVALID_PROPERTY);
        }

        // Fetch clients by index
        List<Client> clientList = model.getFilteredClientList();
        // Check if buyer ID is valid
        if (buyerId.getZeroBased() >= clientList.size()) {
            throw new CommandException(MESSAGE_INVALID_BUYER_ID);
        }
        Client buyer = clientList.get(buyerId.getZeroBased());
        ClientName buyerName = buyer.getClientName();
        // Check if seller ID is valid
        if (sellerId.getZeroBased() >= clientList.size()) {
            throw new CommandException(MESSAGE_INVALID_SELLER_ID);
        }
        Client seller = clientList.get(sellerId.getZeroBased());
        ClientName sellerName = seller.getClientName();

        // Validate that buyer and seller are not the same person
        if (buyerId.equals(sellerId)) {
            throw new CommandException(MESSAGE_SAME_BUYER_SELLER);
        }

        // Check if price exceeds limit
        if (price.value > 999_990_000) {
            throw new CommandException(MESSAGE_PRICE_EXCEEDS_LIMIT);
        }

        Deal toAdd = new Deal(propertyName, buyerName, sellerName, price, status);

        if (model.hasDeal(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_DEAL);
        }

        // Check if the property is already involved in another deal
        boolean propertyAlreadyInDeal = model.getFilteredDealList().stream()
                .anyMatch(existingDeal -> existingDeal.getPropertyName().equals(propertyName));
        if (propertyAlreadyInDeal) {
            throw new CommandException(MESSAGE_PROPERTY_ALREADY_IN_DEAL);
        }

        model.addDeal(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                propertyName.toString(),
                buyerName.toString(),
                sellerName.toString(),
                price.value,
                status));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddDealCommand otherAddDealCommand)) {
            return false;
        }

        return propertyName.equals(otherAddDealCommand.propertyName)
                && buyerId.equals(otherAddDealCommand.buyerId)
                && sellerId.equals(otherAddDealCommand.sellerId)
                && price.value.equals(otherAddDealCommand.price.value)
                && status.equals(otherAddDealCommand.status);
    }
}
