package seedu.address.logic.parser.deal;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.deal.FindDealCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
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
public class FindDealCommandParser implements Parser<FindDealCommand> {

    private static final String BLANK = "BLANK";

    /**
     * Parses the given {@code String} of arguments in the context of the FindDealCommand
     * and returns a FindDealCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindDealCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_PROPERTY_NAME, PREFIX_BUYER, PREFIX_SELLER, PREFIX_STATUS);

        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDealCommand.MESSAGE_USAGE));
        }

        // Verify no duplicate prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PROPERTY_NAME, PREFIX_BUYER, PREFIX_SELLER, PREFIX_STATUS);

        // Parse property name
        List<String> propertyNameKeywords = parseKeywords(
                argMultimap.getValue(PREFIX_PROPERTY_NAME).orElse(BLANK));

        // Parse buyer name
        List<String> buyerNameKeywords = parseKeywords(
                argMultimap.getValue(PREFIX_BUYER).orElse(BLANK));

        // Parse seller name
        List<String> sellerNameKeywords = parseKeywords(
                argMultimap.getValue(PREFIX_SELLER).orElse(BLANK));

        // Parse status
        DealStatus status = null;
        if (!argMultimap.getValue(PREFIX_STATUS).orElse(BLANK).equals(BLANK)) {
            try {
                String statusString = argMultimap.getValue(PREFIX_STATUS).get().toUpperCase();
                // Handle "IN NEGOTIATION" special case
                if (statusString.equals("IN NEGOTIATION")) {
                    statusString = "IN_NEGOTIATION";
                }
                status = DealStatus.valueOf(statusString);
            } catch (IllegalArgumentException e) {
                throw new ParseException("Invalid status: Must be one of 'PENDING', 'CLOSED', 'IN_NEGOTIATION'.");
            }
        }

        // Create predicates
        Predicate<Deal> propertyNamePredicate = new DealPropertyNameContainsPredicate(propertyNameKeywords);
        Predicate<Deal> buyerNamePredicate = new DealBuyerNameContainsPredicate(buyerNameKeywords);
        Predicate<Deal> sellerNamePredicate = new DealSellerNameContainsPredicate(sellerNameKeywords);
        
        // Start with a combined predicate of name-based searches
        Predicate<Deal> combinedPredicate = propertyNamePredicate
                .or(buyerNamePredicate)
                .or(sellerNamePredicate);

        // Add status predicate if provided
        if (status != null) {
            DealStatusPredicate statusPredicate = new DealStatusPredicate(status);
            combinedPredicate = combinedPredicate.or(statusPredicate);
        }

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
        return Arrays.asList(input.split("\\s+"));
    }
} 