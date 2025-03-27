package seedu.address.logic.commands.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OWNER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SIZE;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.property.Property;

/**
 * Adds a property to the address book.
 */
public class AddPropertyCommand extends AddCommand<Property> {

    public static final String COMMAND_WORD = "add_property";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a property to the address book. "
            + "Parameters: "
            + PREFIX_PROPERTY_NAME + "PROPERTY "
            + PREFIX_OWNER + "OWNER "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_PRICE + "PRICE (in S$ thousands) "
            + PREFIX_SIZE + "SIZE (in square feet) "
            + PREFIX_DESCRIPTION + "DESCRIPTION \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PROPERTY_NAME + "Maple Villa Condominium "
            + PREFIX_OWNER + "Amy Bee "
            + PREFIX_ADDRESS + "123 Maple Street "
            + PREFIX_PRICE + "2400 "
            + PREFIX_SIZE + "1000 "
            + PREFIX_DESCRIPTION + "Spacious 4-bedroom home ";

    public static final String MESSAGE_SUCCESS = "New property added: %1$s";
    public static final String MESSAGE_DUPLICATE_PROPERTY = "This property already exists in the address book";

    /**
     * Creates an AddCommand to add the specified {@code Property}
     */
    public AddPropertyCommand(Property property) {
        super(property);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasProperty(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PROPERTY);
        }

        model.addProperty(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.formatProperty(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPropertyCommand otherAddCommand)) {
            return false;
        }

        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
