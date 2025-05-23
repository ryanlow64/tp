package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.client.Client;
import seedu.address.model.deal.Deal;
import seedu.address.model.event.Event;
import seedu.address.model.property.Property;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX = "The client index provided is invalid";
    public static final String MESSAGE_INVALID_EVENT_DISPLAYED_INDEX = "The event index provided is invalid";
    public static final String MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX = "The property index provided is invalid";
    public static final String MESSAGE_CLIENTS_LISTED_OVERVIEW =
            "{0,choice,0#{0} clients listed|1#1 client listed|1<{0} clients listed}";
    public static final String MESSAGE_PROPERTIES_LISTED_OVERVIEW =
            "{0,choice,0#{0} properties listed|1#1 property listed|1<{0} properties listed}";
    public static final String MESSAGE_EVENTS_LISTED_OVERVIEW =
            "{0,choice,0#{0} events listed|1#1 event listed|1<{0} events listed}";
    public static final String MESSAGE_DEALS_LISTED_OVERVIEW =
            "{0,choice,0#{0} deals listed|1#1 deal listed|1<{0} deals listed}";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_EVENT_IN_PAST = "Event cannot be before 01-01-2025 0000.";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code client} for display to the user.
     */
    public static String formatClient(Client client) {
        return new StringBuilder(String.valueOf(client.getFullName()))
                .append("; Phone: ")
                .append(client.getPhone())
                .append("; Email: ")
                .append(client.getEmail())
                .append("; Address: ")
                .append(client.getAddress())
                .toString();
    }

    /**
     * Formats the {@code property} for display to the user.
     */
    public static String formatProperty(Property property) {
        return new StringBuilder(String.valueOf(property.getFullName()))
                .append("; Address: ")
                .append(property.getAddress())
                .append("; Price: S$")
                .append(property.getPrice()).append(" thousand")
                .append("; Size: ")
                .append(property.getSize().isPresent() ? property.getSize().get() : "N/A")
                .append(" square feet")
                .append("; Description: ")
                .append(property.getDescription().isPresent() ? property.getDescription().get() : "N/A")
                .append("; Owner: ")
                .append(property.getOwner())
                .toString();
    }

    /**
     * Formats the {@code Event} for display to the user.
     */
    public static String formatEvent(Event event) {
        return new StringBuilder(event.getEventType().toFormattedString())
                .append(" at ")
                .append(event.getDateTime().format(ParserUtil.DATE_FORMAT_TEXT))
                .append("; Client: ")
                .append(event.getClientName())
                .append("; Property: ")
                .append(event.getPropertyName())
                .append("; Notes: ")
                .append(event.getNote().toString())
                .toString();
    }

    /**
     * Formats the {@code deal} for display to the user.
     */
    public static String formatDeal(Deal deal) {
        return new StringBuilder("Property: ")
                .append(deal.getPropertyName())
                .append("; Buyer: ")
                .append(deal.getBuyer())
                .append("; Seller: ")
                .append(deal.getSeller())
                .append("; Price: S$")
                .append(deal.getPrice().value).append(" thousand")
                .append("; Status: ")
                .append(deal.getStatus())
                .toString();
    }
}
