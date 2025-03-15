package seedu.address.ui.ListPanels;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.deal.Deal;
import seedu.address.ui.Cards.DealCard;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of deals.
 */
public class DealListPanel extends UiPart<Region> {
    private static final String FXML = "ClientListPanel.fxml"; // Reuse the client list panel FXML for now
    private final Logger logger = LogsCenter.getLogger(DealListPanel.class);

    @FXML
    private ListView<Deal> clientListView; // This ID must match the ID in the FXML file

    /**
     * Creates a {@code DealListPanel} with the given {@code ObservableList}.
     */
    public DealListPanel(ObservableList<Deal> dealList) {
        super(FXML);
        clientListView.setItems(dealList);
        clientListView.setCellFactory(listView -> new DealListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Deal} using a {@code DealCard}.
     */
    class DealListViewCell extends ListCell<Deal> {
        @Override
        protected void updateItem(Deal deal, boolean empty) {
            super.updateItem(deal, empty);

            if (empty || deal == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DealCard(deal, getIndex() + 1).getRoot());
            }
        }
    }
}