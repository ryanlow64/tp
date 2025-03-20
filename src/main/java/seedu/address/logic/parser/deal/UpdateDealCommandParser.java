package seedu.address.logic.parser.deal;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEAL_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.deal.UpdateDealCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.commons.Price;
import seedu.address.model.deal.DealStatus;
import seedu.address.model.property.PropertyName;

/**
 * Parses input arguments and creates a new UpdateDealCommand object
 */
public class UpdateDealCommandParser implements Parser<UpdateDealCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateDealCommand
     * and returns an UpdateDealCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateDealCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DEAL_ID, PREFIX_PROPERTY_NAME, PREFIX_BUYER, PREFIX_SELLER,
                        PREFIX_PRICE, PREFIX_STATUS);

        // Deal ID is required
        if (!argMultimap.getValue(PREFIX_DEAL_ID).isPresent() || argMultimap.getPreamble().length() > 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateDealCommand.MESSAGE_USAGE));
        }

        // Parse deal index
        Index dealIndex;
        try {
            dealIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_DEAL_ID).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format("Invalid deal ID: %s", pe.getMessage()));
        }

        // Verify at least one field to update is provided
        boolean hasAtLeastOneField = argMultimap.getValue(PREFIX_PROPERTY_NAME).isPresent()
                || argMultimap.getValue(PREFIX_BUYER).isPresent()
                || argMultimap.getValue(PREFIX_SELLER).isPresent()
                || argMultimap.getValue(PREFIX_PRICE).isPresent()
                || argMultimap.getValue(PREFIX_STATUS).isPresent();

        if (!hasAtLeastOneField) {
            throw new ParseException(UpdateDealCommand.MESSAGE_NO_CHANGES);
        }

        // Parse optional fields
        Optional<PropertyName> propertyName = parsePropertyName(argMultimap);
        Optional<Index> buyerId = parseBuyerId(argMultimap);
        Optional<Index> sellerId = parseSellerId(argMultimap);
        Optional<Price> price = parsePrice(argMultimap);
        Optional<DealStatus> status = parseStatus(argMultimap);

        return new UpdateDealCommand(dealIndex, propertyName, buyerId, sellerId, price, status);
    }

    /**
     * Parses a {@code Optional<String>} property name into an {@code Optional<PropertyName>}
     * if the property name is present.
     */
    private Optional<PropertyName> parsePropertyName(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getValue(PREFIX_PROPERTY_NAME).isPresent()) {
            return Optional.empty();
        }

        String propertyNameStr = argMultimap.getValue(PREFIX_PROPERTY_NAME).get();
        if (!PropertyName.isValidPropertyName(propertyNameStr)) {
            throw new ParseException(PropertyName.MESSAGE_CONSTRAINTS);
        }
        return Optional.of(new PropertyName(propertyNameStr));
    }

    /**
     * Parses a {@code Optional<String>} buyer ID into an {@code Optional<Index>}
     * if the buyer ID is present.
     */
    private Optional<Index> parseBuyerId(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getValue(PREFIX_BUYER).isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(ParserUtil.parseIndex(argMultimap.getValue(PREFIX_BUYER).get()));
        } catch (ParseException pe) {
            throw new ParseException(String.format("Invalid buyer ID: %s", pe.getMessage()));
        }
    }

    /**
     * Parses a {@code Optional<String>} seller ID into an {@code Optional<Index>}
     * if the seller ID is present.
     */
    private Optional<Index> parseSellerId(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getValue(PREFIX_SELLER).isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(ParserUtil.parseIndex(argMultimap.getValue(PREFIX_SELLER).get()));
        } catch (ParseException pe) {
            throw new ParseException(String.format("Invalid seller ID: %s", pe.getMessage()));
        }
    }

    /**
     * Parses a {@code Optional<String>} price into an {@code Optional<Price>}
     * if the price is present.
     */
    private Optional<Price> parsePrice(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getValue(PREFIX_PRICE).isPresent()) {
            return Optional.empty();
        }

        String priceString = argMultimap.getValue(PREFIX_PRICE).get();
        try {
            Long priceValue = Long.parseLong(priceString);
            if (!Price.isValidPrice(priceValue)) {
                throw new ParseException(Price.MESSAGE_CONSTRAINTS);
            }
            if (priceValue > 999_990_000) {
                throw new ParseException("Price exceeds the limit of 999.99");
            }
            return Optional.of(new Price(priceValue));
        } catch (NumberFormatException e) {
            throw new ParseException(Price.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code Optional<String>} status into an {@code Optional<DealStatus>}
     * if the status is present.
     */
    private Optional<DealStatus> parseStatus(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            return Optional.empty();
        }

        String statusString = argMultimap.getValue(PREFIX_STATUS).get().toUpperCase();
        try {
            // Convert to the format expected by the enum (e.g., "in_negotiation" to "IN_NEGOTIATION")
            if (statusString.equals("IN NEGOTIATION")) {
                statusString = "IN_NEGOTIATION";
            }
            return Optional.of(DealStatus.valueOf(statusString));
        } catch (IllegalArgumentException e) {
            throw new ParseException("Invalid status: Must be one of 'PENDING', 'CLOSED', 'IN_NEGOTIATION'.");
        }
    }
}
