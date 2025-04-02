package seedu.address.logic.parser.client;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEYWORDS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.client.FindClientCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.FindCommandParser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.event.FindEventCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Client;
import seedu.address.model.client.predicates.ClientAddressContainsPredicate;
import seedu.address.model.client.predicates.ClientEmailContainsPredicate;
import seedu.address.model.client.predicates.ClientNameContainsKeywordsPredicate;
import seedu.address.model.client.predicates.ClientPhoneContainsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindClientCommandParser extends FindCommandParser<Client> {

    private static final Logger logger = LogsCenter.getLogger(FindEventCommandParser.class);
    private static final String BLANK = "(blank)";

    private List<String> nameKeywords;
    private String addressContains;
    private String emailContains;
    private String phoneContains;

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FindClientCommand parse(String args) throws ParseException {
        logger.info("Parsing arguments for FindClientCommand: " + args);

        Prefix[] prefixes = Command.COMMAND_WORDS.get(FindClientCommand.COMMAND_WORD).toArray(Prefix[]::new);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, prefixes);
        List<Prefix> prefixesUsed = argMultimap.getPrefixes();

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindClientCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(prefixes);
        logger.fine("No repeated arguments supplied");

        checkPrefixesUsedAreValid(prefixesUsed);

        handleName(argMultimap, prefixesUsed);
        handleStrings(argMultimap, prefixesUsed);

        LinkedHashMap<Prefix, Predicate<Client>> prefixPredicateMap = getPrefixPredicateMap(prefixesUsed);
        Predicate<Client> combinedPredicate = getCombinedPredicate(prefixPredicateMap);
        return new FindClientCommand(combinedPredicate);
    }

    @Override
    protected LinkedHashMap<Prefix, Predicate<Client>> getPrefixPredicateMap(List<Prefix> prefixesUsed) {
        LinkedHashMap<Prefix, Predicate<Client>> prefixPredicateMap = new LinkedHashMap<>();
        for (Prefix prefix : prefixesUsed) {
            if (prefix.equals(PREFIX_KEYWORDS)) {
                prefixPredicateMap.put(prefix, new ClientNameContainsKeywordsPredicate(nameKeywords));
            } else if (prefix.equals(PREFIX_ADDRESS)) {
                prefixPredicateMap.put(prefix, new ClientAddressContainsPredicate(addressContains));
            } else if (prefix.equals(PREFIX_EMAIL)) {
                prefixPredicateMap.put(prefix, new ClientEmailContainsPredicate(emailContains));
            } else if (prefix.equals(PREFIX_PHONE)) {
                prefixPredicateMap.put(prefix, new ClientPhoneContainsPredicate(phoneContains));
            }
        }
        return prefixPredicateMap;
    }

    private void handleStrings(ArgumentMultimap argMultimap, List<Prefix> prefixesUsed) throws ParseException {
        addressContains = argMultimap.getValue(PREFIX_ADDRESS).orElse(BLANK).trim();
        if (prefixesUsed.contains(PREFIX_ADDRESS) && addressContains.isEmpty()) {
            throw new ParseException("No address provided");
        }
        logger.fine("Address: " + addressContains);

        emailContains = argMultimap.getValue(PREFIX_EMAIL).orElse(BLANK).trim();
        if (prefixesUsed.contains(PREFIX_EMAIL) && emailContains.isEmpty()) {
            throw new ParseException("No email provided");
        }
        logger.fine("Email: " + emailContains);

        phoneContains = argMultimap.getValue(PREFIX_PHONE).orElse(BLANK).trim();
        if (prefixesUsed.contains(PREFIX_PHONE) && phoneContains.isEmpty()) {
            throw new ParseException("No phone number provided");
        }
        logger.fine("Phone: " + phoneContains);
    }

    private void handleName(ArgumentMultimap argMultimap, List<Prefix> prefixesUsed) throws ParseException {
        String nameContains = argMultimap.getValue(PREFIX_KEYWORDS).orElse(BLANK).trim();
        nameKeywords = Arrays.stream(nameContains.split("\\s+"))
                .filter(keyword -> !keyword.isBlank())
                .toList();
        if (prefixesUsed.contains(PREFIX_KEYWORDS) && nameKeywords.isEmpty()) {
            throw new ParseException("No keywords provided");
        }
        logger.fine("Name: " + nameContains);
    }
}
