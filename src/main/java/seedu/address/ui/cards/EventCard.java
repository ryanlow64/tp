package seedu.address.ui.cards;

import java.time.LocalDateTime;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.event.Event;
import seedu.address.ui.UiPart;

/**
 * A UI component that displays information of a {@code Event}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Event event;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label dateTime;
    @FXML
    private Label eventType;
    @FXML
    private Label client;
    @FXML
    private Label property;
    @FXML
    private Label note;

    /**
     * Creates a {@code EventCode} with the given {@code Event} and index to display.
     */
    public EventCard(Event event, int displayedIndex) {
        super(FXML);
        assert event != null : "Event should not be null";
        assert displayedIndex > 0 : "Index should be greater than 0";
        this.event = event;
        id.setText(displayedIndex + ". ");
        dateTime.setText(event.getDateTime().format(ParserUtil.DATE_FORMAT_TEXT));
        if (event.getDateTime().isBefore(LocalDateTime.now())) {
            dateTime.setStyle("-fx-text-fill: #ef1414;");
        }
        eventType.setText("Type: " + event.getEventType().toFormattedString());
        client.setText("With: " + event.getClientName().fullName);
        property.setText("About: " + event.getPropertyName().fullName);
        String noteValue = event.getNote().toString();
        note.setText("Note: " + (noteValue.equals("N/A") ? "-" : noteValue));
    }
}
