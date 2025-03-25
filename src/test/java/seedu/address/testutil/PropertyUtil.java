package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SIZE;

import seedu.address.logic.commands.property.AddPropertyCommand;
import seedu.address.logic.commands.property.EditPropertyCommand.EditPropertyDescriptor;
import seedu.address.model.property.Property;

/**
 * A utility class for Property.
 */
public class PropertyUtil {

    /**
     * Returns an add command string for adding the {@code property}.
     */
    public static String getAddCommand(Property property) {
        return AddPropertyCommand.COMMAND_WORD + " " + getPropertyDetails(property);
    }

    /**
     * Returns the part of command string for the given {@code property}'s details.
     */
    public static String getPropertyDetails(Property property) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_PROPERTY_NAME + property.getPropertyName().fullName + " ");
        sb.append(PREFIX_ADDRESS + property.getAddress().value + " ");
        sb.append(PREFIX_PRICE + String.valueOf(property.getPrice().value + " "));
        property.getSize().ifPresent(size -> sb.append(PREFIX_SIZE + size.value + " "));
        property.getDescription().ifPresent(desc -> sb.append(PREFIX_DESCRIPTION + desc.getDescription()
                .orElse("") + " "));
        sb.append(PREFIX_CLIENT_NAME + property.getOwner().fullName + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPropertyDescriptor}'s details.
     */
    public static String getEditPropertyDescriptorDetails(EditPropertyDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getPropertyName().ifPresent(name -> sb.append(PREFIX_PROPERTY_NAME).append(name.fullName)
                .append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getPrice().ifPresent(price -> sb.append(PREFIX_PRICE).append(price.value).append(" "));
        descriptor.getSize().ifPresent(sizeOpt ->
                sizeOpt.ifPresent(size -> sb.append(PREFIX_SIZE).append(size.value).append(" ")));
        descriptor.getDescription().ifPresent(descOpt ->
                descOpt.ifPresent(desc -> sb.append(PREFIX_DESCRIPTION).append(desc.getDescription().orElse(""))
                        .append(" ")));
        descriptor.getOwner().ifPresent(owner -> sb.append(PREFIX_CLIENT_NAME).append(owner.fullName).append(" "));
        return sb.toString();
    }
}
