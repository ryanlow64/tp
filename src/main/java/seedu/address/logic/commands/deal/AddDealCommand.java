package seedu.address.logic.commands.deal;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.client.ClientName;
import seedu.address.model.commons.Price;
import seedu.address.model.deal.Deal;
import seedu.address.model.deal.DealStatus;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyName;

/**
 * Adds a deal to REconnect.
 */
public class AddDealCommand extends Command {

    public static final String COMMAND_WORD = "add_deal";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a property deal to REconnect. "
            + "Parameters: "
            + PREFIX_PROPERTY_ID + "PROPERTY_ID "
            + PREFIX_BUYER + "BUYER_ID "
            + PREFIX_PRICE + "PRICE "
            + "[" + PREFIX_STATUS + "STATUS]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PROPERTY_ID + "1 "
            + PREFIX_BUYER + "1 "
            + PREFIX_PRICE + "100 "
            + PREFIX_STATUS + "closed";

    public static final String MESSAGE_SUCCESS = "New deal added successfully:"
            + " Property: %1$s, Buyer: %2$s, Seller: %3$s, Price $%4$d, Status %5$s";
    public static final String MESSAGE_DUPLICATE_DEAL = "This deal already exists in REconnect";
    public static final String MESSAGE_INVALID_PROPERTY_ID = "Invalid property ID.";
    public static final String MESSAGE_INVALID_BUYER_ID = "Invalid buyer ID.";
    public static final String MESSAGE_SAME_BUYER_SELLER = "Buyer and seller cannot be the same person.";
    public static final String MESSAGE_PROPERTY_ALREADY_IN_DEAL = "This property is already involved in another deal.";
    public static final String MESSAGE_PRICE_EXCEEDS_LIMIT = "Price should only contain positive numbers"
                                                            + " (in S$ thousands) between 3 to 6 digits";

    private static final Logger logger = LogsCenter.getLogger(AddDealCommand.class);

    private final Index propertyId;
    private final Index buyerId;
    private final Price price;
    private final DealStatus status;

    /**
     * Creates an AddDealCommand to add a deal with the specified details
     */
    public AddDealCommand(Index propertyId, Index buyerId, Price price, DealStatus status) {
        requireNonNull(propertyId);
        requireNonNull(buyerId);
        requireNonNull(price);
        requireNonNull(status);
        this.propertyId = propertyId;
        this.buyerId = buyerId;
        this.price = price;
        this.status = status;
        logger.fine("Created AddDealCommand with propertyId: " + propertyId.getOneBased()
                + ", buyerId: " + buyerId.getOneBased()
                + ", price: " + price.value
                + ", status: " + status);
    }

    /**
     * Adds the command word to the command word list.
     */
    public static void addCommandWord() {
        Prefix[] prefixes = {
            PREFIX_PROPERTY_ID,
            PREFIX_BUYER,
            PREFIX_PRICE,
            PREFIX_STATUS
        };
        initialiseCommandWord(COMMAND_WORD, prefixes);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Executing AddDealCommand");

        // Get the property by ID
        List<Property> propertyList = model.getFilteredPropertyList();
        logger.fine("PropertyList size: " + propertyList.size() + ", requested propertyId: "
                + propertyId.getOneBased());
        if (propertyId.getZeroBased() >= propertyList.size()) {
            logger.warning("Invalid property ID: " + propertyId.getOneBased());
            throw new CommandException(MESSAGE_INVALID_PROPERTY_ID);
        }
        Property property = propertyList.get(propertyId.getZeroBased());
        PropertyName propertyName = property.getFullName();
        logger.fine("Found property: " + propertyName);

        // Get the seller from the property's owner
        ClientName sellerName = property.getOwner();
        logger.fine("Seller name: " + sellerName);

        // Fetch buyer by index
        List<Client> clientList = model.getFilteredClientList();
        logger.fine("ClientList size: " + clientList.size() + ", requested buyerId: " + buyerId.getOneBased());
        // Check if buyer ID is valid
        if (buyerId.getZeroBased() >= clientList.size()) {
            logger.warning("Invalid buyer ID: " + buyerId.getOneBased());
            throw new CommandException(MESSAGE_INVALID_BUYER_ID);
        }
        Client buyer = clientList.get(buyerId.getZeroBased());
        ClientName buyerName = buyer.getFullName();
        logger.fine("Found buyer: " + buyerName);

        // Validate that buyer and seller are not the same person
        if (buyerName.equals(sellerName)) {
            logger.warning("Attempted to create deal where buyer and seller are the same: " + buyerName);
            throw new CommandException(MESSAGE_SAME_BUYER_SELLER);
        }

        // Check if price exceeds limit
        if (!Price.isValidPrice(price.value)) {
            logger.warning("Price format is invalid: " + price.value);
            throw new CommandException(MESSAGE_PRICE_EXCEEDS_LIMIT);
        }

        Deal toAdd = new Deal(propertyName, buyerName, sellerName, price, status);
        logger.fine("Created deal: " + toAdd);

        if (model.hasDeal(toAdd)) {
            logger.warning("Duplicate deal detected: " + toAdd);
            throw new CommandException(MESSAGE_DUPLICATE_DEAL);
        }

        // Check if the property is already involved in another deal
        boolean propertyAlreadyInDeal = model.getFilteredDealList().stream()
                .anyMatch(existingDeal -> existingDeal.getPropertyName().equals(propertyName));
        if (propertyAlreadyInDeal) {
            logger.warning("Property already in another deal: " + propertyName);
            throw new CommandException(MESSAGE_PROPERTY_ALREADY_IN_DEAL);
        }

        model.addDeal(toAdd);
        logger.info("Added deal successfully: " + toAdd);

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

        return propertyId.equals(otherAddDealCommand.propertyId)
                && buyerId.equals(otherAddDealCommand.buyerId)
                && price.value.equals(otherAddDealCommand.price.value)
                && status.equals(otherAddDealCommand.status);
    }
}
