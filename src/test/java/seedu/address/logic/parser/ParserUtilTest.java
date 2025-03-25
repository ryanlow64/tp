package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.ClientName;
import seedu.address.model.client.Email;
import seedu.address.model.client.Phone;
import seedu.address.model.commons.Address;
import seedu.address.model.commons.Price;
import seedu.address.model.property.Description;
import seedu.address.model.property.PropertyName;
import seedu.address.model.property.Size;

public class ParserUtilTest {
    private static final String INVALID_CLIENTNAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";

    private static final String VALID_CLIENTNAME = "Rachel Walker";
    private static final String VALID_PHONE = "12345678";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    void parseDateTime_validInput_success() throws ParseException {
        assertEquals(LocalDateTime.of(1889, 4, 20, 17, 42),
                ParserUtil.parseDateTime("20-04-1889 1742"));

        assertEquals(LocalDateTime.of(2024, 2, 29, 0, 0),
                ParserUtil.parseDateTime("29-02-2024 0000"));
    }

    @Test
    void parseDateTime_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDateTime("arnold schwarzenegger"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDateTime("30/04/2025 1742"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDateTime("30-04-25 1742"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDateTime("30 Apr 2025 1742"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDateTime("30-04-2025 17:42"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDateTime("30-04-2025 faketime"));

        assertThrows(ParseException.class, () -> ParserUtil.parseDateTime("29-02-2025 1742"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDateTime("31-04-2025 1742"));
    }

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_CLIENT, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_CLIENT, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseClientName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseClientName((String) null));
    }

    @Test
    public void parseClientName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseClientName(INVALID_CLIENTNAME));
    }

    @Test
    public void parseClientName_validValueWithoutWhitespace_returnsClientName() throws Exception {
        ClientName expectedClientName = new ClientName(VALID_CLIENTNAME);
        assertEquals(expectedClientName, ParserUtil.parseClientName(VALID_CLIENTNAME));
    }

    @Test
    public void parseClientName_validValueWithWhitespace_returnsTrimmedClientName() throws Exception {
        String clientNameWithWhitespace = WHITESPACE + VALID_CLIENTNAME + WHITESPACE;
        ClientName expectedClientName = new ClientName(VALID_CLIENTNAME);
        assertEquals(expectedClientName, ParserUtil.parseClientName(clientNameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseSize_validSize_success() throws ParseException {
        String validSize1 = "1000";
        String validSize2 = "500";

        assertEquals(Optional.of(new Size(validSize1)), ParserUtil.parseSize(validSize1));
        assertEquals(Optional.of(new Size(validSize2)), ParserUtil.parseSize(validSize2));
    }

    @Test
    public void parseSize_nullSize_failure() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSize(null));
    }

    @Test
    public void parseDescription_validDescription_success() throws ParseException {
        String validDescription1 = "Spacious 4-bedroom house";
        String validDescription2 = "Cozy 2-bedroom apartment";

        assertEquals(Optional.of(new Description(validDescription1)), ParserUtil.parseDescription(validDescription1));
        assertEquals(Optional.of(new Description(validDescription2)), ParserUtil.parseDescription(validDescription2));
    }

    @Test
    public void parseDescription_nullDescription_failure() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDescription(null));
    }

    @Test
    public void parsePropertyName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePropertyName(null));
    }

    @Test
    public void parsePropertyName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePropertyName(""));
    }

    @Test
    public void parsePropertyName_validValue_success() throws Exception {
        String validPropertyName = "Sunset Villa";
        assertEquals(new PropertyName(validPropertyName), ParserUtil.parsePropertyName(validPropertyName));
    }

    @Test
    public void parsePrice_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePrice(null));
    }

    @Test
    public void parsePrice_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePrice(0L));
    }

    @Test
    public void parsePrice_validValue_success() throws Exception {
        assertEquals(new Price(3000L), ParserUtil.parsePrice(3000L));
    }

    @Test
    public void parseOwner_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseOwner(null));
    }

    @Test
    public void parseOwner_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseOwner("J@ne"));
    }

    @Test
    public void parseOwner_validValue_success() throws Exception {
        String validOwner = "Jane Doe";
        assertEquals(new ClientName(validOwner), ParserUtil.parseOwner(validOwner));
    }

    @Test
    public void parseEventType_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEventType(null));
    }

    @Test
    public void parseEventType_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEventType("holiday"));
    }

    @Test
    public void parseNote_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNote(null));
    }

    @Test
    public void parseNote_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNote("   "));
    }
}
