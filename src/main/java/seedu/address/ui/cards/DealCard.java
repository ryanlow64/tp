package seedu.address.ui.cards;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.deal.Deal;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Deal}.
 */
public class DealCard extends UiPart<Region> {

    private static final String FXML = "DealListCard.fxml";

    public final Deal deal;

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
     * Creates a {@code DealCard} with the given {@code Deal} and index to display.
     */
    public DealCard(Deal deal, int displayedIndex) {
        super(FXML);
        this.deal = deal;
        id.setText(displayedIndex + ". ");
        name.setText(deal.getPropertyName().toString());
        address.setText("Buyer: " + deal.getBuyer().toString()
                + " | Seller: " + deal.getSeller().toString());
        phone.setText("Price: $" + deal.getPrice().value);
        email.setText("Status: " + deal.getStatus());
        // Make the card visually separate for now
        cardPane.setStyle("-fx-background-color: #f0f8ff;"); // Light blue background
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DealCard)) {
            return false;
        }

        // state check
        DealCard card = (DealCard) other;
        return id.getText().equals(card.id.getText())
                && deal.equals(card.deal);
    }
}
