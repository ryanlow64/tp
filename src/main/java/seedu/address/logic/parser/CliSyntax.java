package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_CLIENT_NAME = new Prefix("name/");
    public static final Prefix PREFIX_CLIENT_ID = new Prefix("cid/");
    public static final Prefix PREFIX_PHONE = new Prefix("phone/");
    public static final Prefix PREFIX_EMAIL = new Prefix("email/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("addr/");
    public static final Prefix PREFIX_EVENT_TYPE = new Prefix("etype/");
    public static final Prefix PREFIX_EVENT_START = new Prefix("at/");
    public static final Prefix PREFIX_EVENT_NOTE = new Prefix("note/");
    public static final Prefix PREFIX_EVENT_WITH = new Prefix("with/");
    public static final Prefix PREFIX_EVENT_ABOUT = new Prefix("about/");
    public static final Prefix PREFIX_PROPERTY_NAME = new Prefix("prop/");
    public static final Prefix PREFIX_PROPERTY_ID = new Prefix("pid/");
    public static final Prefix PREFIX_PRICE = new Prefix("price/");
    public static final Prefix PREFIX_SIZE = new Prefix("size/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("desc/");
    public static final Prefix PREFIX_SELLER = new Prefix("seller/");
    public static final Prefix PREFIX_BUYER = new Prefix("buyer/");
    public static final Prefix PREFIX_STATUS = new Prefix("status/");
    public static final Prefix PREFIX_DEAL_ID = new Prefix("dealId/");
}
