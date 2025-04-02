package seedu.address.logic.parser.property;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEYWORDS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OWNER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE_ABOVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE_BELOW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SIZE_ABOVE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SIZE_BELOW;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.property.FindPropertyCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.FindCommandParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.event.FindEventCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.commons.Price;
import seedu.address.model.property.Property;
import seedu.address.model.property.Size;
import seedu.address.model.property.predicates.PropertyAddressContainsPredicate;
import seedu.address.model.property.predicates.PropertyNameContainsKeywordsPredicate;
import seedu.address.model.property.predicates.PropertyOwnerContainsKeywordsPredicate;
import seedu.address.model.property.predicates.PropertyPriceAbovePredicate;
import seedu.address.model.property.predicates.PropertyPriceBelowPredicate;
import seedu.address.model.property.predicates.PropertySizeAbovePredicate;
import seedu.address.model.property.predicates.PropertySizeBelowPredicate;

/**
 * Parses input arguments and creates a new FindPropertyCommand object.
 */
public class FindPropertyCommandParser extends FindCommandParser<Property> {

    private static final Logger logger = LogsCenter.getLogger(FindEventCommandParser.class);
    private static final String BLANK = "-";

    private List<String> nameKeywords;
    private List<String> ownerKeywords;
    private String addressContains;
    private Price priceBelow;
    private Price priceAbove;
    private Size sizeBelow;
    private Size sizeAbove;

    /**
     * Parses the given {@code String} of arguments in the context of the FindPropertyCommand
     * and returns a FindPropertyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPropertyCommand parse(String args) throws ParseException {
        logger.info("Parsing arguments for FindPropertyCommand: " + args);

        Prefix[] prefixes = Command.COMMAND_WORDS.get(FindPropertyCommand.COMMAND_WORD).toArray(Prefix[]::new);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, prefixes);
        List<Prefix> prefixesUsed = argMultimap.getPrefixes();

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPropertyCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(prefixes);
        logger.fine("No repeated arguments supplied");

        checkPrefixesUsedAreValid(prefixesUsed);

        handleStrings(argMultimap, prefixesUsed);
        handlePrices(argMultimap, prefixesUsed);
        handleSizes(argMultimap, prefixesUsed);

        LinkedHashMap<Prefix, Predicate<Property>> prefixPredicateMap = getPrefixPredicateMap(prefixesUsed);
        Predicate<Property> combinedPredicate = getCombinedPredicate(prefixPredicateMap);
        return new FindPropertyCommand(combinedPredicate);
    }

    @Override
    protected LinkedHashMap<Prefix, Predicate<Property>> getPrefixPredicateMap(List<Prefix> prefixesUsed) {
        LinkedHashMap<Prefix, Predicate<Property>> prefixPredicateMap = new LinkedHashMap<>();
        for (Prefix prefix : prefixesUsed) {
            if (prefix.equals(PREFIX_KEYWORDS)) {
                prefixPredicateMap.put(prefix, new PropertyNameContainsKeywordsPredicate(nameKeywords));
            } else if (prefix.equals(PREFIX_ADDRESS)) {
                prefixPredicateMap.put(prefix, new PropertyAddressContainsPredicate(addressContains));
            } else if (prefix.equals(PREFIX_OWNER)) {
                prefixPredicateMap.put(prefix, new PropertyOwnerContainsKeywordsPredicate(ownerKeywords));
            } else if (prefix.equals(PREFIX_PRICE_BELOW)) {
                prefixPredicateMap.put(prefix, new PropertyPriceBelowPredicate(priceBelow));
            } else if (prefix.equals(PREFIX_PRICE_ABOVE)) {
                prefixPredicateMap.put(prefix, new PropertyPriceAbovePredicate(priceAbove));
            } else if (prefix.equals(PREFIX_SIZE_BELOW)) {
                prefixPredicateMap.put(prefix, new PropertySizeBelowPredicate(sizeBelow));
            } else if (prefix.equals(PREFIX_SIZE_ABOVE)) {
                prefixPredicateMap.put(prefix, new PropertySizeAbovePredicate(sizeAbove));
            }
        }
        return prefixPredicateMap;
    }

    private void handleSizes(ArgumentMultimap argMultimap, List<Prefix> prefixesUsed) throws ParseException {
        String sizeBelowString = argMultimap.getValue(PREFIX_SIZE_BELOW).orElse("");
        if (prefixesUsed.contains(PREFIX_SIZE_BELOW) && sizeBelowString.isEmpty()) {
            throw new ParseException("No size below provided");
        } else if (prefixesUsed.contains(PREFIX_SIZE_BELOW)) {
            try {
                sizeBelow = ParserUtil.parseSize(sizeBelowString).orElse(null);
            } catch (ParseException | NullPointerException e) {
                logger.warning("Invalid size below provided");
                throw new ParseException(Size.MESSAGE_CONSTRAINTS);
            }
        }

        String sizeAboveString = argMultimap.getValue(PREFIX_SIZE_ABOVE).orElse("");
        if (prefixesUsed.contains(PREFIX_SIZE_ABOVE) && sizeAboveString.isEmpty()) {
            throw new ParseException("No size above provided");
        } else if (prefixesUsed.contains(PREFIX_SIZE_ABOVE)) {
            try {
                sizeAbove = ParserUtil.parseSize(sizeAboveString).orElse(null);
            } catch (ParseException | NullPointerException e) {
                logger.warning("Invalid size above provided");
                throw new ParseException(Size.MESSAGE_CONSTRAINTS);
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

    private void handleStrings(ArgumentMultimap argMultimap, List<Prefix> prefixesUsed) throws ParseException {
        String nameContains = argMultimap.getValue(PREFIX_KEYWORDS).orElse(BLANK).trim();
        nameKeywords = Arrays.stream(nameContains.split("\\s+"))
            .filter(keyword -> !keyword.isBlank())
            .toList();
        if (prefixesUsed.contains(PREFIX_KEYWORDS) && nameKeywords.isEmpty()) {
            throw new ParseException("No keywords provided");
        }

        addressContains = argMultimap.getValue(PREFIX_ADDRESS).orElse(BLANK).trim();
        if (prefixesUsed.contains(PREFIX_ADDRESS) && addressContains.isEmpty()) {
            throw new ParseException("No address provided");
        }

        String ownerName = argMultimap.getValue(PREFIX_OWNER).orElse(BLANK).trim();
        ownerKeywords = Arrays.stream(ownerName.split("\\s+"))
            .filter(keyword -> !keyword.isBlank())
            .toList();
        if (prefixesUsed.contains(PREFIX_OWNER) && ownerKeywords.isEmpty()) {
            throw new ParseException("No owner name provided");
        }
    }
}
