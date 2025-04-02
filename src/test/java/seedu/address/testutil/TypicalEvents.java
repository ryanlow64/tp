package seedu.address.testutil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.client.ClientName;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventType;
import seedu.address.model.event.Note;
import seedu.address.model.property.PropertyName;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final Event EVENT1 = new Event(
            EventType.MEETING,
            new PropertyName("Maple Villa Condominium"),
            new ClientName("Alice Pauline"),
            LocalDateTime.of(2025, 4, 30, 17, 42),
            new Note("nil")
    );

    public static final Event EVENT2 = new Event(
            EventType.MEETING,
            new PropertyName("Jurong Lake Gardens HDB"),
            new ClientName("Arnold Schwarzenegger"),
            LocalDateTime.of(2025, 11, 4, 5, 42),
            new Note("take photos")
    );

    public static final Event EVENT3 = new Event(
            EventType.OTHERS,
            new PropertyName("Punggol Waterway Ridges HDB"),
            new ClientName("Bob Choo"),
            LocalDateTime.MIN,
            new Note("nil")
    );

    public static final Event EVENT4 = new Event(
            EventType.OTHERS,
            new PropertyName("Marina Bay"),
            new ClientName("Alice Pauline"),
            LocalDateTime.of(2024, 12, 1, 6, 0),
            new Note("nil")
    );

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(EVENT1, EVENT2, EVENT3, EVENT4));
    }

    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Event event : getTypicalEvents()) {
            ab.addEvent(event);
        }
        return ab;
    }
}
