package seedu.address.logic.commands.deal;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.commons.Price;
import seedu.address.model.deal.Deal;
import seedu.address.model.deal.DealStatus;

/**
 * Adds a deal to the address book.
 */
public class AddDealCommand extends Command {

    public static final String COMMAND_WORD = "add_deal";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a property deal to the address book. "
            + "Parameters: "
            + PREFIX_PROPERTY_ID + "PROPERTY_ID "
            + PREFIX_BUYER + "BUYER_ID "
            + PREFIX_SELLER + "SELLER_ID "
            + PREFIX_PRICE + "PRICE "
            + "[" + PREFIX_STATUS + "STATUS]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PROPERTY_ID + "4 "
            + PREFIX_BUYER + "5 "
            + PREFIX_SELLER + "6 "
            + PREFIX_PRICE + "690000 "
            + PREFIX_STATUS + "closed";

    public static final String MESSAGE_SUCCESS = "New deal added successfully:"
            + " Property ID %1$d, Buyer ID %2$d, Seller ID %3$d, Price $%4$d, Status %5$s";
    public static final String MESSAGE_DUPLICATE_DEAL = "This deal already exists in the address book";
    public static final String MESSAGE_INVALID_PROPERTY_ID = "Invalid property ID.";
    public static final String MESSAGE_INVALID_BUYER_ID = "Invalid buyer ID.";
    public static final String MESSAGE_INVALID_SELLER_ID = "Invalid seller ID.";
    public static final String MESSAGE_SAME_BUYER_SELLER = "Buyer and seller cannot be the same person.";
    public static final String MESSAGE_PROPERTY_ALREADY_IN_DEAL = "This property is already involved in another deal.";

    private final Index propertyId;
    private final Index buyerId;
    private final Index sellerId;
    private final Price price;
    private final DealStatus status;

    /**
     * Creates an AddDealCommand to add the specified deal
     */
    public AddDealCommand(Index propertyId, Index buyerId, Index sellerId, Price price, DealStatus status) {
        requireNonNull(propertyId);
        requireNonNull(buyerId);
        requireNonNull(sellerId);
        requireNonNull(price);
        requireNonNull(status);
        this.propertyId = propertyId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.price = price;
        this.status = status;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Validate property ID exists
        // For now, we'll skip property validation until property list is implemented
        // TODO: Update this when property list is implemented
        // if (propertyId.getZeroBased() >= model.getFilteredPropertyList().size()) {
        //     throw new CommandException(MESSAGE_INVALID_PROPERTY_ID);
        // }

        // Validate buyer ID exists
        if (buyerId.getZeroBased() >= model.getFilteredClientList().size()) {
            throw new CommandException(MESSAGE_INVALID_BUYER_ID);
        }

        // Validate seller ID exists
        if (sellerId.getZeroBased() >= model.getFilteredClientList().size()) {
            throw new CommandException(MESSAGE_INVALID_SELLER_ID);
        }
        // Validate that buyer and seller are not the same person
        if (buyerId.equals(sellerId)) {
            throw new CommandException(MESSAGE_SAME_BUYER_SELLER);
        }

        Deal toAdd = new Deal(propertyId, buyerId, sellerId, price, status);

        if (model.hasDeal(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_DEAL);
        }
        // Check if the property is already involved in another deal
        boolean propertyAlreadyInDeal = model.getFilteredDealList().stream()
                .anyMatch(existingDeal -> existingDeal.getPropertyId().equals(propertyId));
        if (propertyAlreadyInDeal) {
            throw new CommandException(MESSAGE_PROPERTY_ALREADY_IN_DEAL);
        }

        model.addDeal(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                propertyId.getOneBased(),
                buyerId.getOneBased(),
                sellerId.getOneBased(),
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
                && sellerId.equals(otherAddDealCommand.sellerId)
                && price.value.equals(otherAddDealCommand.price.value)
                && status.equals(otherAddDealCommand.status);
    }
}
