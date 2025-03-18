package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    // Prefix definitions
    public static final Prefix PREFIX_CLIENT_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_EVENT_TYPE = new Prefix("etype/");
    public static final Prefix PREFIX_PROPERTY_NAME = new Prefix("pn/");
    public static final Prefix PREFIX_EVENT_START = new Prefix("at/");
    public static final Prefix PREFIX_EVENT_NOTE = new Prefix("note/");
}
