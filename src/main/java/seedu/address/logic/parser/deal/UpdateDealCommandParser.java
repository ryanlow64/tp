package seedu.address.logic.parser.deal;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.deal.UpdateDealCommand;
import seedu.address.logic.commands.deal.UpdateDealCommand.UpdateDealDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.EditCommandParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.commons.Price;
import seedu.address.model.deal.Deal;
import seedu.address.model.deal.DealStatus;

/**
 * Parses input arguments and creates a new UpdateDealCommand object
 */
public class UpdateDealCommandParser extends EditCommandParser<Deal> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateDealCommand
     * and returns an UpdateDealCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateDealCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_PROPERTY_ID, PREFIX_BUYER, PREFIX_SELLER, PREFIX_PRICE, PREFIX_STATUS);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateDealCommand.MESSAGE_USAGE),
                    pe);
        }

        // Verify no duplicate prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PROPERTY_ID, PREFIX_BUYER, PREFIX_SELLER, PREFIX_PRICE,
                PREFIX_STATUS);

        UpdateDealDescriptor updateDealDescriptor = new UpdateDealDescriptor();

        if (argMultimap.getValue(PREFIX_PROPERTY_ID).isPresent()) {
            updateDealDescriptor.setPropertyId(ParserUtil.parseIndex(argMultimap
                .getValue(PREFIX_PROPERTY_ID).get()));
        }
        if (argMultimap.getValue(PREFIX_BUYER).isPresent()) {
            updateDealDescriptor.setBuyer(ParserUtil.parseIndex(argMultimap.getValue(PREFIX_BUYER).get()));
        }
        if (argMultimap.getValue(PREFIX_SELLER).isPresent()) {
            updateDealDescriptor.setSeller(ParserUtil.parseIndex(argMultimap.getValue(PREFIX_SELLER).get()));
        }
        if (argMultimap.getValue(PREFIX_PRICE).isPresent()) {
            String priceArg = argMultimap.getValue(PREFIX_PRICE).get();
            if (!priceArg.matches("^[0-9]+$")) {
                throw new ParseException(Price.MESSAGE_CONSTRAINTS);
            }
            if (!priceArg.matches(Price.VALIDATION_REGEX)) {
                throw new ParseException(Price.MESSAGE_CONSTRAINTS);
            }
            Long priceValue = Long.valueOf(priceArg);
            updateDealDescriptor.setPrice(new Price(priceValue));
        }
        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            String statusString = argMultimap.getValue(PREFIX_STATUS).get().toUpperCase();
            try {
                updateDealDescriptor.setStatus(DealStatus.valueOf(statusString));
            } catch (IllegalArgumentException e) {
                throw new ParseException("Invalid status: Must be one of"
                        + " 'OPEN', 'PENDING', 'CLOSED' (case insensitive).");
            }
        }

        if (!updateDealDescriptor.isAnyFieldEdited()) {
            throw new ParseException(UpdateDealCommand.MESSAGE_NO_CHANGES);
        }

        return new UpdateDealCommand(index, updateDealDescriptor);
    }
}
