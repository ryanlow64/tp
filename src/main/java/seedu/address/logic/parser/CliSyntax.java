package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* General Prefix definitions */
    public static final Prefix PREFIX_CLIENT_NAME = new Prefix("name/");
    public static final Prefix PREFIX_CLIENT_ID = new Prefix("cid/");
    public static final Prefix PREFIX_EVENT_START = new Prefix("at/");
    public static final Prefix PREFIX_EVENT_NOTE = new Prefix("note/");
    public static final Prefix PREFIX_PROPERTY_ID = new Prefix("pid/");
    public static final Prefix PREFIX_PRICE = new Prefix("price/");
    public static final Prefix PREFIX_SIZE = new Prefix("size/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("desc/");

    /* Conditional Prefixes */
    public static final Prefix PREFIX_ADDRESS = new Prefix("addr/", true);
    public static final Prefix PREFIX_EMAIL = new Prefix("email/", true);
    public static final Prefix PREFIX_PHONE = new Prefix("phone/", true);
    public static final Prefix PREFIX_OWNER = new Prefix("owner/", true);
    public static final Prefix PREFIX_KEYWORDS = new Prefix("name_keywords/", true);
    public static final Prefix PREFIX_PROPERTY_NAME = new Prefix("prop/", true);
    public static final Prefix PREFIX_BUYER = new Prefix("buyer/", true);
    public static final Prefix PREFIX_SELLER = new Prefix("seller/", true);
    public static final Prefix PREFIX_STATUS = new Prefix("status/", true);
    public static final Prefix PREFIX_PRICE_BELOW = new Prefix("price_</", true);
    public static final Prefix PREFIX_PRICE_ABOVE = new Prefix("price_>/", true);
    public static final Prefix PREFIX_SIZE_BELOW = new Prefix("size_</", true);
    public static final Prefix PREFIX_SIZE_ABOVE = new Prefix("size_>/", true);
    public static final Prefix PREFIX_EVENT_ABOUT = new Prefix("about/", true);
    public static final Prefix PREFIX_EVENT_WITH = new Prefix("with/", true);
    public static final Prefix PREFIX_EVENT_TYPE = new Prefix("etype/", true);
    public static final Prefix PREFIX_EVENT_BEFORE = new Prefix("before/", true);
    public static final Prefix PREFIX_EVENT_AFTER = new Prefix("after/", true);
}
