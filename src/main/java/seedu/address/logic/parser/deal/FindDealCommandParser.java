package seedu.address.logic.parser.deal;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE_ABOVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE_BELOW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.deal.FindDealCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.FindCommandParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.ClientName;
import seedu.address.model.commons.Price;
import seedu.address.model.deal.Deal;
import seedu.address.model.deal.DealStatus;
import seedu.address.model.deal.predicates.DealBuyerNameContainsPredicate;
import seedu.address.model.deal.predicates.DealPriceAbovePredicate;
import seedu.address.model.deal.predicates.DealPriceBelowPredicate;
import seedu.address.model.deal.predicates.DealPropertyNameContainsPredicate;
import seedu.address.model.deal.predicates.DealSellerNameContainsPredicate;
import seedu.address.model.deal.predicates.DealStatusPredicate;
import seedu.address.model.property.PropertyName;

/**
 * Parses input arguments and creates a new FindDealCommand object
 */
public class FindDealCommandParser extends FindCommandParser<Deal> {

    private static final Logger logger = LogsCenter.getLogger(FindDealCommandParser.class);
    private static final String BLANK = "BLANK";

    private ClientName buyerName;
    private ClientName sellerName;
    private PropertyName propertyName;
    private Price priceBelow;
    private Price priceAbove;
    private DealStatus status;

    /**
     * Parses the given {@code String} of arguments in the context of the FindDealCommand
     * and returns a FindDealCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindDealCommand parse(String args) throws ParseException {
        logger.info("Parsing arguments for FindDealCommand: " + args);

        Prefix[] prefixes = Command.COMMAND_WORDS.get(FindDealCommand.COMMAND_WORD).toArray(Prefix[]::new);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, prefixes);
        List<Prefix> prefixesUsed = argMultimap.getPrefixes();

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            logger.warning("Missing arguments");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDealCommand.MESSAGE_USAGE));
        }

        // Verify no duplicate prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(prefixes);
        logger.fine("No repeated arguments supplied");

        // Check if prefixes used are valid
        checkPrefixesUsedAreValid(prefixesUsed);

        handleNames(argMultimap);
        handlePrices(argMultimap, prefixesUsed);
        handleDealStaus(argMultimap, prefixesUsed);

        LinkedHashMap<Prefix, Predicate<Deal>> prefixPredicateMap = getPrefixPredicateMap(prefixesUsed);
        Predicate<Deal> combinedPredicate = getCombinedPredicate(prefixPredicateMap);
        return new FindDealCommand(combinedPredicate);
    }

    @Override
    protected LinkedHashMap<Prefix, Predicate<Deal>> getPrefixPredicateMap(List<Prefix> prefixesUsed) {
        LinkedHashMap<Prefix, Predicate<Deal>> prefixPredicateMap = new LinkedHashMap<>();
        for (Prefix prefix : prefixesUsed) {
            if (prefix.equals(PREFIX_BUYER)) {
                prefixPredicateMap.put(prefix, new DealBuyerNameContainsPredicate(buyerName));
            } else if (prefix.equals(PREFIX_SELLER)) {
                prefixPredicateMap.put(prefix, new DealSellerNameContainsPredicate(sellerName));
            } else if (prefix.equals(PREFIX_STATUS)) {
                prefixPredicateMap.put(prefix, new DealStatusPredicate(status));
            } else if (prefix.equals(PREFIX_PROPERTY_NAME)) {
                prefixPredicateMap.put(prefix, new DealPropertyNameContainsPredicate(propertyName));
            } else if (prefix.equals(PREFIX_PRICE_BELOW)) {
                prefixPredicateMap.put(prefix, new DealPriceBelowPredicate(priceBelow));
            } else if (prefix.equals(PREFIX_PRICE_ABOVE)) {
                prefixPredicateMap.put(prefix, new DealPriceAbovePredicate(priceAbove));
            }
        }
        return prefixPredicateMap;
    }

    private void handleNames(ArgumentMultimap argMultimap) throws ParseException {
        buyerName = ParserUtil.parseClientName(argMultimap.getValue(PREFIX_BUYER).orElse(BLANK));
        sellerName = ParserUtil.parseClientName(argMultimap.getValue(PREFIX_SELLER).orElse(BLANK));
        propertyName = ParserUtil.parsePropertyName(argMultimap.getValue(PREFIX_PROPERTY_NAME)
            .orElse(BLANK));
    }

    private void handleDealStaus(ArgumentMultimap argMultimap, List<Prefix> prefixesUsed) throws ParseException {
        try {
            status = ParserUtil.parseDealStatus(argMultimap.getValue(PREFIX_STATUS).orElse(BLANK));
        } catch (ParseException e) {
            if (prefixesUsed.contains(PREFIX_STATUS)) {
                logger.warning("Invalid deal status provided");
                throw new ParseException(e.getMessage());
            }
        }
    }

    private void handlePrices(ArgumentMultimap argMultimap, List<Prefix> prefixesUsed) throws ParseException {
        try {
            priceBelow = ParserUtil.parsePrice(Long.parseLong(argMultimap.getValue(PREFIX_PRICE_BELOW).orElse("0")));
        } catch (ParseException | NumberFormatException e) {
            if (prefixesUsed.contains(PREFIX_PRICE_BELOW)) {
                logger.warning("Invalid price below provided");
                throw new ParseException(Price.MESSAGE_CONSTRAINTS);
            }
        }

        try {
            priceAbove = ParserUtil.parsePrice(Long.parseLong(argMultimap.getValue(PREFIX_PRICE_ABOVE).orElse("0")));
        } catch (ParseException | NumberFormatException e) {
            if (prefixesUsed.contains(PREFIX_PRICE_ABOVE)) {
                logger.warning("Invalid price above provided");
                throw new ParseException(Price.MESSAGE_CONSTRAINTS);
            }
        }
    }

    /**
     * Parses the input string into keywords if it's not blank.
     * @param input The input string to parse
     * @return A list of keywords or an empty list if input is BLANK
     */
    private List<String> parseKeywords(String input) {
        if (input.equals(BLANK)) {
            return List.of();
        }
        List<String> keywords = Arrays.asList(input.split("\\s+"));
        logger.fine("Parsed keywords from input '" + input + "': " + keywords);
        return keywords;
    }
}
