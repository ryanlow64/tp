package seedu.address.logic.parser;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public abstract class FindCommandParser<T> implements Parser<FindCommand<T>> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public abstract FindCommand<T> parse(String args) throws ParseException;

    /**
     * Returns a mapping of prefixes to their corresponding predicates for filtering results
     * based on the parsed input arguments.
     *
     * @param prefixesUsed The list of prefixes used in the command.
     * @return a linked hash map that maps each prefix to a predicate evaluating objects of type T
     * @throws ParseException if an error occurs while parsing the arguments
     */
    protected abstract LinkedHashMap<Prefix, Predicate<T>> getPrefixPredicateMap(List<Prefix> prefixesUsed)
            throws ParseException;

    /**
     * Checks if the prefixes used are valid.
     *
     * @param prefixesUsed The list of prefixes used in the command.
     * @throws ParseException If the prefixes used are invalid.
     */
    protected static void checkPrefixesUsedAreValid(List<Prefix> prefixesUsed) throws ParseException {
        List<Prefix> validPrefixes = prefixesUsed.stream()
            .filter(prefix -> !prefix.getPrefix().isEmpty())
            .toList();

        if (validPrefixes.isEmpty()) {
            throw new ParseException("No valid prefixes used");
        }

        Prefix firstPrefix = validPrefixes.get(0);
        if (firstPrefix.isConnective()) {
            throw new ParseException("First prefix used cannot be connective");
        }

        boolean allAnd = true;
        boolean allOr = true;
        for (int i = 1; i < validPrefixes.size(); i++) {
            Prefix currentPrefix = validPrefixes.get(i);
            if (!currentPrefix.isConnective()) {
                throw new ParseException("Subsequent prefixes used after the first must be connective");
            }
            if (currentPrefix.isAndPrefix()) {
                allOr = false;
            } else if (currentPrefix.isOrPrefix()) {
                allAnd = false;
            }
        }

        if (!allAnd && !allOr) {
            throw new ParseException("Cannot mix AND and OR connective prefixes");
        }
    }

    /**
     * Combines the predicates based on the prefixes used.
     *
     * @param prefixPredicateMap A map of prefixes to predicates.
     */
    protected static <T> Predicate<T> getCombinedPredicate(LinkedHashMap<Prefix, Predicate<T>> prefixPredicateMap) {
        Predicate<T> combinedPredicate = null;
        for (Prefix prefix : prefixPredicateMap.keySet()) {
            combinedPredicate = combinePredicates(combinedPredicate, prefix, prefixPredicateMap.get(prefix));
        }
        return combinedPredicate;
    }

    /**
     * Combines the current predicate with the next predicate based on the prefix.
     *
     * @param currentPredicate The current predicate.
     * @param prefix The prefix indicating the type of combination (AND/OR).
     * @param nextPredicate The next predicate to combine with.
     * @return The combined predicate.
     */
    private static <T> Predicate<T> combinePredicates(Predicate<T> currentPredicate, Prefix prefix,
                                                        Predicate<T> nextPredicate) {
        if (currentPredicate == null && !prefix.isConnective()) {
            return nextPredicate;
        } else if (currentPredicate != null && prefix.isAndPrefix()) {
            return currentPredicate.and(nextPredicate);
        } else if (currentPredicate != null && prefix.isOrPrefix()) {
            return currentPredicate.or(nextPredicate);
        } else {
            throw new IllegalStateException("Invalid state: currentPredicate is null and prefix is connective");
        }
    }
}
