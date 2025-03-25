package seedu.address.ui.cards;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.client.Client;
import seedu.address.ui.UiPart;

/**
 * A UI component that displays information of a {@code Client}.
 */
public class ClientCard extends UiPart<Region> {

    private static final String FXML = "ClientListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Client client;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;

    /**
     * Creates a {@code ClientCode} with the given {@code Client} and index to display.
     * Thanks to samuelneo for the idea of using emojis to represent phone and email.
     * See here: <a href="https://github.com/AY2425S2-CS2103T-T10-1/tp" />
     */
    public ClientCard(Client client, int displayedIndex) {
        super(FXML);
        this.client = client;
        id.setText(displayedIndex + ". ");
        name.setText(client.getClientName().fullName);
        phone.setText("\uD83D\uDCDE " + client.getPhone().value);
        address.setText("\uD83C\uDFE0 " + client.getAddress().value);
        email.setText("\uD83D\uDCE7 " + client.getEmail().value);
    }
}
