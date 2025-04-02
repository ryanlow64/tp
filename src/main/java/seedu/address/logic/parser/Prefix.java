package seedu.address.logic.parser;

/**
 * A prefix that marks the beginning of an argument in an arguments string.
 * E.g. 't/' in 'add James t/ friend'.
 */
public class Prefix {
    protected final String prefix;
    private final boolean canBeConditional;

    /**
     * Creates a prefix with the specified string.
     *
     * @param prefix The string to be used as the prefix.
     */
    public Prefix(String prefix) {
        this(prefix, false);
    }

    /**
     * Creates a conditional prefix with the specified string.
     *
     * @param prefix The string to be used as the prefix.
     * @param canBeConditional Whether this prefix is conditional.
     */
    public Prefix(String prefix, boolean canBeConditional) {
        this.prefix = prefix;
        this.canBeConditional = canBeConditional;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean canBeConditional() {
        return canBeConditional;
    }

    public boolean isConditional() {
        return this instanceof ConditionalPrefix;
    }

    /**
     * Returns the prefix with the AND conditional.
     */
    public Prefix getAndPrefix() {
        if (!canBeConditional) {
            throw new IllegalStateException("Prefix cannot be conditional");
        }
        return new ConditionalPrefix(prefix, Conditional.AND);
    }

    /**
     * Returns the prefix with the OR conditional.
     */
    public Prefix getOrPrefix() {
        if (!canBeConditional) {
            throw new IllegalStateException("Prefix cannot be conditional");
        }
        return new ConditionalPrefix(prefix, Conditional.OR);
    }

    /**
     * Returns whether this prefix is an AND conditional prefix.
     */
    public boolean isAndPrefix() {
        return this instanceof ConditionalPrefix && ((ConditionalPrefix) this).conditional == Conditional.AND;
    }

    /**
     * Returns whether this prefix is an OR conditional prefix.
     */
    public boolean isOrPrefix() {
        return this instanceof ConditionalPrefix && ((ConditionalPrefix) this).conditional == Conditional.OR;
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

    private static class ConditionalPrefix extends Prefix {
        public final Conditional conditional;

        public ConditionalPrefix(String prefix, Conditional conditional) {
            super(prefix, true);
            this.conditional = conditional;
        }

        @Override
        public String getPrefix() {
            return conditional + "_" + prefix;
        }

        @Override
        public String toString() {
            return getPrefix();
        }
    }
}
