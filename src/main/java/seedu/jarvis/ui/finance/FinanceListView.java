package seedu.jarvis.ui.finance;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import seedu.jarvis.logic.Logic;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.finance.installment.Installment;
import seedu.jarvis.model.finance.purchase.Purchase;
import seedu.jarvis.ui.MainWindow;
import seedu.jarvis.ui.template.View;

/**
 * A View representing the list of {@code Purchase}.
 */
public class FinanceListView extends View<AnchorPane> {

    private static final String FXML = "FinanceListView.fxml";

    @FXML
    private ListView<Purchase> purchaseListView;
    @FXML
    private ListView<Installment> installmentListView;

    public FinanceListView(MainWindow mainWindow, Logic logic, Model model) {

        super(FXML, mainWindow, logic, model);
    }

    @Override
    public void fillPage() {
        purchaseListView.setItems(model.getFilteredPurchaseList());
        purchaseListView.setCellFactory(listView -> new PurchaseListViewCell());

        installmentListView.setItems(model.getFilteredInstallmentList());
        installmentListView.setCellFactory(listView -> new InstallmentListViewCell());
    }

    /**
     * A custom {@code PurchaseListViewCell} that displays the graphics of a {@code Purchase} using a
     * {@code PurchaseCard}.
     */
    class PurchaseListViewCell extends ListCell<Purchase> {

        @Override
        protected void updateItem(Purchase purchase, boolean empty) {
            super.updateItem(purchase, empty);

            if (empty || purchase == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PurchaseCard(purchase, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * A custom {@code InstallmentListViewCell} that displays the graphics of a {@code Installment} using a
     * {@code InstallmentCard}.
     */
    class InstallmentListViewCell extends ListCell<Installment> {

        @Override
        protected void updateItem(Installment installment, boolean empty) {
            super.updateItem(installment, empty);

            if (empty || installment == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new InstallmentCard(installment, getIndex() + 1).getRoot());
            }
        }
    }
}
