package seedu.address.ui.cards;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.deal.Deal;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Deal}.
 */
public class DealCard extends UiPart<Region> {

    private static final String FXML = "DealListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Deal deal;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label title;
    @FXML
    private Label propertyName;
    @FXML
    private Label buyer;
    @FXML
    private Label seller;
    @FXML
    private Label price;
    @FXML
    private FlowPane status;

    /**
     * Creates a {@code DealCode} with the given {@code Deal} and index to display.
     */
    public DealCard(Deal deal, int displayedIndex) {
        super(FXML);
        this.deal = deal;
        id.setText(displayedIndex + ". ");
        propertyName.setText(deal.getPropertyName().fullName);
        buyer.setText("Buyer: " + deal.getBuyer().fullName);
        seller.setText("Seller: " + deal.getSeller().fullName);
        price.setText(String.format("Price: $%sk", deal.getPrice().value.toString()));
        status.getChildren().add(new Label(deal.getStatus().toString()));
    }
}
