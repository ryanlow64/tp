package seedu.address.logic.commands.deal;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.client.ClientName;
import seedu.address.model.commons.Price;
import seedu.address.model.deal.Deal;
import seedu.address.model.deal.DealStatus;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyName;

/**
 * Updates the details of an existing deal in the address book.
 */
public class UpdateDealCommand extends EditCommand<Deal> {

    public static final String COMMAND_WORD = "update_deal";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates a property deal identified "
            + "by the index number used in the displayed deal list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_PROPERTY_ID + "PROPERTY_ID] "
            + "[" + PREFIX_BUYER + "BUYER_ID] "
            + "[" + PREFIX_PRICE + "PRICE] "
            + "[" + PREFIX_STATUS + "STATUS]\n"
            + "Example: " + COMMAND_WORD + " 3 "
            + PREFIX_STATUS + "CLOSED";

    public static final String MESSAGE_UPDATE_DEAL_SUCCESS = "Deal updated successfully";
    public static final String MESSAGE_NO_FIELDS_PROVIDED = "At least one field to update must be provided";
    public static final String MESSAGE_INVALID_DEAL_ID = "Invalid deal ID";
    public static final String MESSAGE_SAME_BUYER_SELLER = "Buyer and seller cannot be the same person";
    public static final String MESSAGE_DUPLICATE_DEAL = "This deal already exists in the address book";
    public static final String MESSAGE_NO_CHANGES_MADE = "No changes were made to the deal";

    private final UpdateDealDescriptor updateDealDescriptor;

    /**
     * Creates an UpdateDealCommand to update the specified {@code Deal}
     *
     * @param index of the deal in the filtered deal list to edit
     * @param updateDealDescriptor details to update the deal with
     */
    public UpdateDealCommand(Index index, UpdateDealDescriptor updateDealDescriptor) {
        super(index, updateDealDescriptor);
        this.updateDealDescriptor = new UpdateDealDescriptor(updateDealDescriptor);
    }

    /**
     * Adds a command word and its associated prefixes to the command word map.
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
        List<Deal> lastShownList = model.getFilteredDealList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_DEAL_ID);
        }

        Deal dealToUpdate = lastShownList.get(index.getZeroBased());

        // If no fields are edited, throw an exception
        if (!updateDealDescriptor.isAnyFieldEdited()) {
            throw new CommandException(MESSAGE_NO_FIELDS_PROVIDED);
        }

        Deal updatedDeal = createUpdatedDeal(dealToUpdate, model, updateDealDescriptor);

        if (dealToUpdate.equals(updatedDeal)) {
            throw new CommandException(MESSAGE_NO_CHANGES_MADE);
        }

        if (!dealToUpdate.isSameDeal(updatedDeal) && model.hasDeal(updatedDeal)) {
            throw new CommandException(MESSAGE_DUPLICATE_DEAL);
        }

        model.setDeal(dealToUpdate, updatedDeal);
        model.updateFilteredDealList(Model.PREDICATE_SHOW_ALL_DEALS);
        return new CommandResult(String.format(MESSAGE_UPDATE_DEAL_SUCCESS, updatedDeal));
    }

    /**
     * Creates and returns a {@code Deal} with the details of {@code dealToUpdate}
     * updated with {@code updateDealDescriptor}. Fields not provided in the descriptor
     * will retain their original values.
     */
    private static Deal createUpdatedDeal(Deal dealToUpdate, Model model, UpdateDealDescriptor updateDealDescriptor)
            throws CommandException {
        assert dealToUpdate != null;

        // Extract and validate property
        PropertyName updatedPropertyName;
        ClientName updatedSeller;
        if (updateDealDescriptor.getPropertyId().isPresent()) {
            Index propertyIndex = updateDealDescriptor.getPropertyId().get();
            if (propertyIndex.getZeroBased() >= model.getFilteredPropertyList().size()) {
                throw new CommandException("Invalid property ID: Index out of bounds");
            }
            Property newProperty = model.getFilteredPropertyList().get(propertyIndex.getZeroBased());
            updatedPropertyName = newProperty.getFullName();
            // Automatically update seller to match new property's owner
            updatedSeller = newProperty.getOwner();
        } else {
            updatedPropertyName = dealToUpdate.getPropertyName();
            // If property isn't being updated, handle seller update based on whether it's from EditClientCommand
            if (updateDealDescriptor.getSeller().isPresent()) {
                // Check if this is an internal update (from EditClientCommand)
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                boolean isInternalUpdate = false;
                for (StackTraceElement element : stackTrace) {
                    if (element.getClassName().contains("EditClientCommand")) {
                        isInternalUpdate = true;
                        break;
                    }
                }
                if (!isInternalUpdate) {
                    throw new CommandException("Seller cannot be manually updated."
                            + " It is automatically set based on the property owner.");
                }
                Index sellerIndex = updateDealDescriptor.getSeller().get();
                if (sellerIndex.getZeroBased() >= model.getFilteredClientList().size()) {
                    throw new CommandException("Invalid seller ID: Index out of bounds");
                }
                updatedSeller = model.getFilteredClientList().get(sellerIndex.getZeroBased()).getFullName();
            } else {
                updatedSeller = dealToUpdate.getSeller();
            }
        }

        // Extract and validate buyer
        ClientName updatedBuyer;
        if (updateDealDescriptor.getBuyer().isPresent()) {
            Index buyerIndex = updateDealDescriptor.getBuyer().get();
            if (buyerIndex.getZeroBased() >= model.getFilteredClientList().size()) {
                throw new CommandException("Invalid buyer ID: Index out of bounds");
            }
            updatedBuyer = model.getFilteredClientList().get(buyerIndex.getZeroBased()).getFullName();
        } else {
            updatedBuyer = dealToUpdate.getBuyer();
        }

        // Validate that buyer and seller are not the same person
        if (updatedBuyer.equals(updatedSeller)) {
            throw new CommandException(MESSAGE_SAME_BUYER_SELLER);
        }

        Price updatedPrice = updateDealDescriptor.getPrice().orElse(dealToUpdate.getPrice());

        // Check if price exceeds limit
        if (!Price.isValidPrice(updatedPrice.value)) {
            throw new CommandException(Price.MESSAGE_CONSTRAINTS);
        }

        DealStatus updatedStatus = updateDealDescriptor.getStatus().orElse(dealToUpdate.getStatus());
        return new Deal(updatedPropertyName, updatedBuyer, updatedSeller, updatedPrice, updatedStatus);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateDealCommand)) {
            return false;
        }

        UpdateDealCommand otherUpdateCommand = (UpdateDealCommand) other;
        return index.equals(otherUpdateCommand.index)
            && updateDealDescriptor.equals(otherUpdateCommand.updateDealDescriptor);
    }

    /**
     * Stores the details to edit the deal with. Each non-empty field value will replace the corresponding field.
     */
    public static class UpdateDealDescriptor extends EditDescriptor<Deal> {
        private Index propertyId;
        private Index buyer;
        private Index seller;
        private Price price;
        private DealStatus status;

        public UpdateDealDescriptor() {}

        /**
         * Copy constructor.
         *
         */
        public UpdateDealDescriptor(UpdateDealDescriptor toCopy) {
            setPropertyId(toCopy.propertyId);
            setBuyer(toCopy.buyer);
            setSeller(toCopy.seller);
            setPrice(toCopy.price);
            setStatus(toCopy.status);
        }

        @Override
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(propertyId, buyer, seller, price, status);
        }

        public void setPropertyId(Index propertyId) {
            this.propertyId = propertyId;
        }
        public Optional<Index> getPropertyId() {
            return Optional.ofNullable(propertyId);
        }

        public void setBuyer(Index buyer) {
            this.buyer = buyer;
        }
        public Optional<Index> getBuyer() {
            return Optional.ofNullable(buyer);
        }

        public void setSeller(Index seller) {
            this.seller = seller;
        }
        public Optional<Index> getSeller() {
            return Optional.ofNullable(seller);
        }

        public void setPrice(Price price) {
            this.price = price;
        }
        public Optional<Price> getPrice() {
            return Optional.ofNullable(price);
        }

        public void setStatus(DealStatus status) {
            this.status = status;
        }
        public Optional<DealStatus> getStatus() {
            return Optional.ofNullable(status);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof UpdateDealDescriptor otherUpdateDealDescriptor)) {
                return false;
            }

            return getPropertyId().equals(otherUpdateDealDescriptor.getPropertyId())
                    && getBuyer().equals(otherUpdateDealDescriptor.getBuyer())
                    && getSeller().equals(otherUpdateDealDescriptor.getSeller())
                    && getPrice().equals(otherUpdateDealDescriptor.getPrice())
                    && getStatus().equals(otherUpdateDealDescriptor.getStatus());
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("propertyId", propertyId)
                    .add("buyer", buyer)
                    .add("seller", seller)
                    .add("price", price)
                    .add("status", status)
                    .toString();
        }
    }
}

