package seedu.address.logic.commands.deal;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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
            + PREFIX_BUYER + "BUYER_NAME "
            + PREFIX_SELLER + "SELLER_NAME "
            + PREFIX_PRICE + "PRICE "
            + "[" + PREFIX_STATUS + "STATUS]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PROPERTY_NAME + "Sunset Villa "
            + PREFIX_BUYER + "John Doe "
            + PREFIX_SELLER + "Jane Smith "
            + PREFIX_PRICE + "690000 "
            + PREFIX_STATUS + "closed";

    public static final String MESSAGE_SUCCESS = "New deal added successfully:"
            + " Property: %1$s, Buyer: %2$s, Seller: %3$s, Price $%4$d, Status %5$s";
    public static final String MESSAGE_DUPLICATE_DEAL = "This deal already exists in the address book";
    public static final String MESSAGE_INVALID_PROPERTY = "Invalid property name.";
    public static final String MESSAGE_INVALID_BUYER = "Invalid buyer name.";
    public static final String MESSAGE_INVALID_SELLER = "Invalid seller name.";
    public static final String MESSAGE_SAME_BUYER_SELLER = "Buyer and seller cannot be the same person.";
    public static final String MESSAGE_PROPERTY_ALREADY_IN_DEAL = "This property is already involved in another deal.";

    private final PropertyName propertyName;
    private final ClientName buyer;
    private final ClientName seller;
    private final Price price;
    private final DealStatus status;

    /**
     * Creates an AddDealCommand to add the specified deal
     */
    public AddDealCommand(PropertyName propertyName, ClientName buyer, ClientName seller,
                          Price price, DealStatus status) {
        requireNonNull(propertyName);
        requireNonNull(buyer);
        requireNonNull(seller);
        requireNonNull(price);
        requireNonNull(status);
        this.propertyName = propertyName;
        this.buyer = buyer;
        this.seller = seller;
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

        // Validate buyer name
        if (!ClientName.isValidClientName(buyer.toString())) {
            throw new CommandException(MESSAGE_INVALID_BUYER);
        }

        // Validate seller name
        if (!ClientName.isValidClientName(seller.toString())) {
            throw new CommandException(MESSAGE_INVALID_SELLER);
        }

        // Validate that buyer and seller are not the same person
        if (buyer.equals(seller)) {
            throw new CommandException(MESSAGE_SAME_BUYER_SELLER);
        }

        Deal toAdd = new Deal(propertyName, buyer, seller, price, status);

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
                buyer.toString(),
                seller.toString(),
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
                && buyer.equals(otherAddDealCommand.buyer)
                && seller.equals(otherAddDealCommand.seller)
                && price.value.equals(otherAddDealCommand.price.value)
                && status.equals(otherAddDealCommand.status);
    }
}
