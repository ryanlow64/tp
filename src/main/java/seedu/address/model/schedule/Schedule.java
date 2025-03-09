package seedu.address.model.schedule;

import java.time.LocalDateTime;
import java.util.Optional;

import seedu.address.commons.core.index.Index;

/**
 * Represents a Schedule in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 * TODO: Implement this class
 * TODO: make all fields final
 */
public class Schedule {

    private Index propertyID;
    private Index clientID;
    private LocalDateTime dateTime;
    private Optional<Note> note;

}
