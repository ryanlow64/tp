package seedu.address.logic.parser.deal;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.deal.FindDealCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.FindCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.deal.Deal;
import seedu.address.model.deal.DealStatus;
import seedu.address.model.deal.predicates.DealBuyerNameContainsPredicate;
import seedu.address.model.deal.predicates.DealPropertyNameContainsPredicate;
import seedu.address.model.deal.predicates.DealSellerNameContainsPredicate;
import seedu.address.model.deal.predicates.DealStatusPredicate;

/**
 * Parses input arguments and creates a new FindDealCommand object
 */
public class FindDealCommandParser extends FindCommandParser<Deal> {

    private static final Logger logger = LogsCenter.getLogger(FindDealCommandParser.class);
    private static final String BLANK = "BLANK";

    /**
     * Parses the given {@code String} of arguments in the context of the FindDealCommand
     * and returns a FindDealCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindDealCommand parse(String args) throws ParseException {
        logger.info("Parsing arguments for FindDealCommand: " + args);

        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_PROPERTY_NAME, PREFIX_BUYER, PREFIX_SELLER, PREFIX_STATUS);

        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            logger.warning("Missing arguments");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDealCommand.MESSAGE_USAGE));
        }

        // Verify no duplicate prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PROPERTY_NAME, PREFIX_BUYER, PREFIX_SELLER, PREFIX_STATUS);
        logger.fine("No repeated arguments supplied");

        // Parse property name
        List<String> propertyNameKeywords = parseKeywords(
                argMultimap.getValue(PREFIX_PROPERTY_NAME).orElse(BLANK));
        logger.fine("Property name keywords: " + propertyNameKeywords);

        // Parse buyer name
        List<String> buyerNameKeywords = parseKeywords(
                argMultimap.getValue(PREFIX_BUYER).orElse(BLANK));
        logger.fine("Buyer name keywords: " + buyerNameKeywords);

        // Parse seller name
        List<String> sellerNameKeywords = parseKeywords(
                argMultimap.getValue(PREFIX_SELLER).orElse(BLANK));
        logger.fine("Seller name keywords: " + sellerNameKeywords);

        // Parse status
        DealStatus status = null;
        if (!argMultimap.getValue(PREFIX_STATUS).orElse(BLANK).equals(BLANK)) {
            try {
                String statusString = argMultimap.getValue(PREFIX_STATUS).get().toUpperCase();
                logger.fine("Attempting to parse status: " + statusString);
                status = DealStatus.valueOf(statusString);
                logger.fine("Status specified: " + status);
            } catch (IllegalArgumentException e) {
                logger.warning("Invalid status value provided: " + argMultimap.getValue(PREFIX_STATUS).get());
                throw new ParseException("Invalid status: Must be one of"
                        + " 'OPEN', 'PENDING', 'CLOSED' (case insensitive).");
            }
        }

        // Create predicates
        Predicate<Deal> propertyNamePredicate = propertyNameKeywords.isEmpty()
            ? deal -> true
            : new DealPropertyNameContainsPredicate(propertyNameKeywords);

        Predicate<Deal> buyerNamePredicate = buyerNameKeywords.isEmpty()
            ? deal -> true
            : new DealBuyerNameContainsPredicate(buyerNameKeywords);

        Predicate<Deal> sellerNamePredicate = sellerNameKeywords.isEmpty()
            ? deal -> true
            : new DealSellerNameContainsPredicate(sellerNameKeywords);

        // Start with a combined predicate of all conditions using AND logic
        // This ensures deals match ALL provided criteria, not just any one criterion
        Predicate<Deal> combinedPredicate = propertyNamePredicate
                .and(buyerNamePredicate)
                .and(sellerNamePredicate);

        // Add status predicate if provided
        if (status != null) {
            DealStatusPredicate statusPredicate = new DealStatusPredicate(status);
            combinedPredicate = combinedPredicate.and(statusPredicate);
        }

        logger.info("FindDealCommand created with combined predicates");
        return new FindDealCommand(combinedPredicate);
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
