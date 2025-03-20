package seedu.address.logic.commands.deal;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEAL_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.List;
import java.util.Optional;

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
 * Updates the details of a property deal in the address book.
 */
public class UpdateDealCommand extends Command {

    public static final String COMMAND_WORD = "update_deal";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates a property deal in the address book. "
            + "Parameters: "
            + PREFIX_DEAL_ID + "DEAL_ID "
            + "[" + PREFIX_PROPERTY_NAME + "PROPERTY_NAME] "
            + "[" + PREFIX_BUYER + "BUYER_ID] "
            + "[" + PREFIX_SELLER + "SELLER_ID] "
            + "[" + PREFIX_PRICE + "PRICE] "
            + "[" + PREFIX_STATUS + "STATUS]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DEAL_ID + "3 "
            + PREFIX_STATUS + "CLOSED";

    public static final String MESSAGE_SUCCESS = "Deal updated successfully";
    public static final String MESSAGE_STATUS_SUCCESS = "Deal status updated to '%1$s' successfully";
    public static final String MESSAGE_NO_CHANGES = "At least one field to update must be provided";
    public static final String MESSAGE_INVALID_DEAL_ID = "Invalid deal ID";
    public static final String MESSAGE_INVALID_PROPERTY = "Invalid property name";
    public static final String MESSAGE_INVALID_BUYER_ID = "Invalid buyer ID";
    public static final String MESSAGE_INVALID_SELLER_ID = "Invalid seller ID";
    public static final String MESSAGE_DUPLICATE_DEAL = "This deal already exists in the address book";
    public static final String MESSAGE_SAME_BUYER_SELLER = "Buyer and seller cannot be the same person";
    public static final String MESSAGE_PROPERTY_ALREADY_IN_DEAL = "This property is already involved in another deal";
    public static final String MESSAGE_PRICE_EXCEEDS_LIMIT = "Price exceeds the limit of 999.99";

    private final Index dealIndex;
    private final Optional<PropertyName> propertyName;
    private final Optional<Index> buyerId;
    private final Optional<Index> sellerId;
    private final Optional<Price> price;
    private final Optional<DealStatus> status;

    /**
     * Creates an UpdateDealCommand to update the specified property deal with the provided fields.
     * At least one field must be present.
     */
    public UpdateDealCommand(Index dealIndex, Optional<PropertyName> propertyName, Optional<Index> buyerId,
                           Optional<Index> sellerId, Optional<Price> price, Optional<DealStatus> status) {
        requireNonNull(dealIndex);
        this.dealIndex = dealIndex;
        this.propertyName = propertyName;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.price = price;
        this.status = status;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Check if at least one field is provided for update
        if (!propertyName.isPresent() && !buyerId.isPresent() && !sellerId.isPresent()
                && !price.isPresent() && !status.isPresent()) {
            throw new CommandException(MESSAGE_NO_CHANGES);
        }

        List<Deal> lastShownList = model.getFilteredDealList();

        // Check if deal index is valid
        if (dealIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_DEAL_ID);
        }

        Deal dealToUpdate = lastShownList.get(dealIndex.getZeroBased());
        // Prepare updated values

        PropertyName updatedPropertyName = propertyName.orElse(dealToUpdate.getPropertyName());
        ClientName updatedBuyerName = dealToUpdate.getBuyer();
        ClientName updatedSellerName = dealToUpdate.getSeller();
        Price updatedPrice = price.orElse(dealToUpdate.getPrice());
        DealStatus updatedStatus = status.orElse(dealToUpdate.getStatus());

        // Process buyer and seller if they need to be updated
        List<Client> clientList = model.getFilteredClientList();

        // Update buyer if requested
        if (buyerId.isPresent()) {
            if (buyerId.get().getZeroBased() >= clientList.size()) {
                throw new CommandException(MESSAGE_INVALID_BUYER_ID);
            }
            Client buyer = clientList.get(buyerId.get().getZeroBased());
            updatedBuyerName = buyer.getClientName();

            // Check if buyer and seller are the same (if seller id is not changing)
            if (!sellerId.isPresent() && buyer.getClientName().equals(dealToUpdate.getSeller())) {
                throw new CommandException(MESSAGE_SAME_BUYER_SELLER);
            }
        }

        // Update seller if requested
        if (sellerId.isPresent()) {
            if (sellerId.get().getZeroBased() >= clientList.size()) {
                throw new CommandException(MESSAGE_INVALID_SELLER_ID);
            }
            Client seller = clientList.get(sellerId.get().getZeroBased());
            updatedSellerName = seller.getClientName();

            // Check if buyer and seller are the same (if buyer id was not changed)
            if (!buyerId.isPresent() && seller.getClientName().equals(dealToUpdate.getBuyer())) {
                throw new CommandException(MESSAGE_SAME_BUYER_SELLER);
            }
        }

        // Check if buyer and seller are the same (if both are being updated)
        if (buyerId.isPresent() && sellerId.isPresent() && buyerId.equals(sellerId)) {
            throw new CommandException(MESSAGE_SAME_BUYER_SELLER);
        }

        // Validate property name if it's being updated
        if (propertyName.isPresent() && !PropertyName.isValidPropertyName(updatedPropertyName.toString())) {
            throw new CommandException(MESSAGE_INVALID_PROPERTY);
        }

        // Check if price exceeds limit
        if (price.isPresent() && updatedPrice.value > 999_990_000) {
            throw new CommandException(MESSAGE_PRICE_EXCEEDS_LIMIT);
        }

        // Create the updated deal
        Deal updatedDeal = new Deal(updatedPropertyName, updatedBuyerName, updatedSellerName,
                updatedPrice, updatedStatus);

        // Check for duplicates (but exclude the deal being updated)
        if (!dealToUpdate.isSameDeal(updatedDeal) && model.hasDeal(updatedDeal)) {
            throw new CommandException(MESSAGE_DUPLICATE_DEAL);
        }

        // Check if the property is already involved in another deal when the property name is changing
        if (propertyName.isPresent() && !updatedPropertyName.equals(dealToUpdate.getPropertyName())) {
            boolean propertyAlreadyInDeal = model.getFilteredDealList().stream()
                    .filter(d -> !d.equals(dealToUpdate))
                    .anyMatch(d -> d.getPropertyName().equals(updatedPropertyName));
            if (propertyAlreadyInDeal) {
                throw new CommandException(MESSAGE_PROPERTY_ALREADY_IN_DEAL);
            }
        }

        model.setDeal(dealToUpdate, updatedDeal);
        model.updateFilteredDealList(Model.PREDICATE_SHOW_ALL_DEALS);

        // Customize the message if only the status was updated
        if (status.isPresent() && !propertyName.isPresent() && !buyerId.isPresent()
                && !sellerId.isPresent() && !price.isPresent()) {
            return new CommandResult(String.format(MESSAGE_STATUS_SUCCESS, updatedStatus));
        } else {
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UpdateDealCommand)) {
            return false;
        }

        UpdateDealCommand otherUpdateDealCommand = (UpdateDealCommand) other;
        return dealIndex.equals(otherUpdateDealCommand.dealIndex)
                && propertyName.equals(otherUpdateDealCommand.propertyName)
                && buyerId.equals(otherUpdateDealCommand.buyerId)
                && sellerId.equals(otherUpdateDealCommand.sellerId)
                && price.equals(otherUpdateDealCommand.price)
                && status.equals(otherUpdateDealCommand.status);
    }
}
