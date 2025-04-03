package seedu.address.logic.parser;

/**
 * A prefix that marks the beginning of an argument in an arguments string.
 * E.g. 't/' in 'add James t/ friend'.
 */
public class Prefix {
    protected final String prefix;
    private final boolean canBeConnective;

    /**
     * Creates a prefix with the specified string.
     *
     * @param prefix The string to be used as the prefix.
     */
    public Prefix(String prefix) {
        this(prefix, false);
    }

    /**
     * Creates a connective prefix with the specified string.
     *
     * @param prefix The string to be used as the prefix.
     * @param canBeConnective Whether this prefix is connective.
     */
    public Prefix(String prefix, boolean canBeConnective) {
        this.prefix = prefix;
        this.canBeConnective = canBeConnective;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean canBeConnective() {
        return canBeConnective;
    }

    public boolean isConnective() {
        return this instanceof ConnectivePrefix;
    }

    /**
     * Returns the prefix with the AND connective.
     */
    public Prefix getAndPrefix() {
        if (!canBeConnective) {
            throw new IllegalStateException("Prefix cannot be connective");
        }
        return new ConnectivePrefix(prefix, Connective.AND);
    }

    /**
     * Returns the prefix with the OR connective.
     */
    public Prefix getOrPrefix() {
        if (!canBeConnective) {
            throw new IllegalStateException("Prefix cannot be connective");
        }
        return new ConnectivePrefix(prefix, Connective.OR);
    }

    /**
     * Returns whether this prefix is an AND connective prefix.
     */
    public boolean isAndPrefix() {
        return this instanceof ConnectivePrefix && ((ConnectivePrefix) this).connective == Connective.AND;
    }

    /**
     * Returns whether this prefix is an OR connective prefix.
     */
    public boolean isOrPrefix() {
        return this instanceof ConnectivePrefix && ((ConnectivePrefix) this).connective == Connective.OR;
    }

    @Override
    public String toString() {
        return getPrefix();
    }

    @Override
    public int hashCode() {
        return prefix == null ? 0 : prefix.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Prefix otherPrefix)) {
            return false;
        }

        return prefix.equals(otherPrefix.prefix);
    }

    private static class ConnectivePrefix extends Prefix {
        public final Connective connective;

        public ConnectivePrefix(String prefix, Connective connective) {
            super(prefix, true);
            this.connective = connective;
        }

        @Override
        public String getPrefix() {
            return connective + "_" + prefix;
        }

        @Override
        public String toString() {
            return getPrefix();
        }
    }
}
