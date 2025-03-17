package seedu.address.ui.Cards;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.schedule.Schedule;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Schedule}.
 */
public class ScheduleCard extends UiPart<Region> {

    private static final String FXML = "ScheduleListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Schedule schedule;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label property;
    @FXML
    private Label client;
    @FXML
    private Label dateTime;
    @FXML
    private Label note;

    /**
     * Creates a {@code ScheduleCode} with the given {@code Schedule} and index to display.
     */
    public ScheduleCard(Schedule schedule, int displayedIndex) {
        super(FXML);
        this.schedule = schedule;
        id.setText(displayedIndex + ". ");
        property.setText(schedule.getPropertyName().fullName);
        client.setText(schedule.getClientName().fullName);
        dateTime.setText(schedule.getDateTime().toString());
        note.setText(schedule.getNote().note);
    }
}
