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
            LocalDateTime.of(2025, 4, 30, 17, 42),
            EventType.MEETING,
            new ClientName("Alice Pauline"),
            new PropertyName("Maple Villa Condominium"),
            new Note("nil")
    );

    public static final Event EVENT2 = new Event(
            LocalDateTime.of(2025, 11, 4, 5, 42),
            EventType.MEETING,
            new ClientName("Arnold Schwarzenegger"),
            new PropertyName("Jurong Lake Gardens HDB"),
            new Note("take photos")
    );

    public static final Event EVENT3 = new Event(
            LocalDateTime.MIN,
            EventType.OTHERS,
            new ClientName("Bob Choo"),
            new PropertyName("Punggol Waterway Ridges HDB"),
            new Note("nil")
    );

    public static final Event EVENT4 = new Event(
            LocalDateTime.of(2024, 12, 1, 6, 0),
            EventType.OTHERS,
            new ClientName("Alice Pauline"),
            new PropertyName("Marina Bay"),
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
