/**
 * A utility class to help with building {@link UpdateDealDescriptor} objects.
 */
package seedu.address.testutil;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.deal.UpdateDealCommand.UpdateDealDescriptor;
import seedu.address.model.commons.Price;
import seedu.address.model.deal.DealStatus;
import seedu.address.model.property.PropertyName;

/**
 * A utility class to help with building {@link UpdateDealDescriptor} objects.
 */
public class UpdateDealDescriptorBuilder {
    private UpdateDealDescriptor descriptor;

    /**
     * Creates a {@code UpdateDealDescriptorBuilder} with a new descriptor.
     */
    public UpdateDealDescriptorBuilder() {
        descriptor = new UpdateDealDescriptor();
    }

    /**
     * Sets the {@code PropertyName} of the descriptor.
     * @param propertyName the property name.
     * @return this builder.
     */
    public UpdateDealDescriptorBuilder withPropertyName(String propertyName) {
        descriptor.setPropertyName(new PropertyName(propertyName));
        return this;
    }

    /**
     * Sets the buyer index of the descriptor.
     * @param buyerIndex the buyer index.
     * @return this builder.
     */
    public UpdateDealDescriptorBuilder withBuyer(Index buyerIndex) {
        descriptor.setBuyer(buyerIndex);
        return this;
    }

    /**
     * Sets the seller index of the descriptor.
     * @param sellerIndex the seller index.
     * @return this builder.
     */
    public UpdateDealDescriptorBuilder withSeller(Index sellerIndex) {
        descriptor.setSeller(sellerIndex);
        return this;
    }

    /**
     * Sets the price of the descriptor.
     * @param price the price value.
     * @return this builder.
     */
    public UpdateDealDescriptorBuilder withPrice(long price) {
        descriptor.setPrice(new Price(price));
        return this;
    }

    /**
     * Sets the deal status of the descriptor.
     * @param status the deal status.
     * @return this builder.
     */
    public UpdateDealDescriptorBuilder withStatus(DealStatus status) {
        descriptor.setStatus(status);
        return this;
    }

    /**
     * Builds and returns the {@code UpdateDealDescriptor}.
     * @return the descriptor.
     */
    public UpdateDealDescriptor build() {
        return descriptor;
    }
}
