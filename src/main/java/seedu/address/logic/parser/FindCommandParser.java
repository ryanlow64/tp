package seedu.address.logic.parser;

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
     * @throws ParseException if the user input does not conform the expected format
     */
    public abstract FindCommand<T> parse(String args) throws ParseException;

    protected static void checkPrefixesUsedAreValid(List<Prefix> prefixesUsed) throws ParseException {
        List<Prefix> validPrefixes = prefixesUsed.stream()
            .filter(prefix -> !prefix.getPrefix().isEmpty())
            .toList();

        Prefix firstPrefix = validPrefixes.get(0);
        if (firstPrefix.isConditional()) {
            throw new ParseException("First prefix used cannot be conditional");
        }

        boolean allAnd = true;
        boolean allOr = true;
        for (int i = 1; i < validPrefixes.size(); i++) {
            Prefix currentPrefix = validPrefixes.get(i);
            if (!currentPrefix.isConditional()) {
                throw new ParseException("Subsequent prefixes used after the first must be conditional");
            }
            if (currentPrefix.isAndPrefix()) {
                allOr = false;
            } else if (currentPrefix.isOrPrefix()) {
                allAnd = false;
            }
        }

        if (!allAnd && !allOr) {
            throw new ParseException("Cannot mix AND and OR conditional prefixes");
        }
    }

    protected static <T> Predicate<T> getCombinedPredicate(Predicate<T> currentPredicate, Prefix prefix,
                                                        Predicate<T> nextPredicate) {
        if (currentPredicate == null && !prefix.isConditional()) {
            return nextPredicate;
        } else if (currentPredicate != null && prefix.isAndPrefix()) {
            return currentPredicate.and(nextPredicate);
        } else if (currentPredicate != null && prefix.isOrPrefix()) {
            return currentPredicate.or(nextPredicate);
        } else {
            throw new IllegalStateException("Invalid state: currentPredicate is null and prefix is conditional");
        }
    }
}
