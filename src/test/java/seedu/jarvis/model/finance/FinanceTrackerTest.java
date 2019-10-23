package seedu.jarvis.model.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.util.OptionalDouble;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.jarvis.model.financetracker.FinanceTracker;
import seedu.jarvis.model.financetracker.MonthlyLimit;
import seedu.jarvis.model.financetracker.installment.Installment;
import seedu.jarvis.model.financetracker.installment.InstallmentDescription;
import seedu.jarvis.model.financetracker.installment.InstallmentMoneyPaid;
import seedu.jarvis.model.financetracker.purchase.Purchase;
import seedu.jarvis.model.financetracker.purchase.PurchaseDescription;
import seedu.jarvis.model.financetracker.purchase.PurchaseMoneySpent;
import seedu.jarvis.testutil.finance.InstallmentBuilder;


/**
 * Tests logic of finance tracker class.
 */
public class FinanceTrackerTest {

    private FinanceTracker financeTracker;

    @BeforeEach
    public void setUp() {
        financeTracker = new FinanceTracker();
        ObservableList<Purchase> allPurchases = FXCollections.observableArrayList();
        allPurchases.add(new PurchaseStub());
        allPurchases.add(new PurchaseStub());
        allPurchases.add(new PurchaseStub());
        financeTracker.setPurchaseList(allPurchases);
        ObservableList<Installment> allInstalments = FXCollections.observableArrayList();;
        allInstalments.add(new InstallmentStub());
        allInstalments.add(new InstallmentStub());
        allInstalments.add(new InstallmentStub());
        financeTracker.setInstallmentList(allInstalments);
    }

    @Test
    public void addPurchase_normalInput_addedCorrectly() {
        financeTracker.addSinglePurchase(new PurchaseStub());
        Purchase addedPurchase = financeTracker.getPurchase(4);
        assertEquals(new PurchaseStub().getDescription(), addedPurchase.getDescription());
        assertEquals(new PurchaseStub().getMoneySpent(), addedPurchase.getMoneySpent());
        assertEquals(4, financeTracker.getTotalPurchases());
    }

    @Test
    public void deletePurchase_normalInput_deletedCorrectly() {
        Purchase deletedPurchase = financeTracker.deleteSinglePurchase(2);
        assertEquals(new PurchaseStub().getDescription(), deletedPurchase.getDescription());
        assertEquals(new PurchaseStub().getMoneySpent(), deletedPurchase.getMoneySpent());
        assertEquals(2, financeTracker.getTotalPurchases());
    }

    @Test
    public void deletePurchase_indexNonexistent_throwsError() {
        assertThrows(RuntimeException.class, () -> financeTracker.deleteSinglePurchase(4));
        assertEquals(3, financeTracker.getTotalPurchases());
    }

    @Test
    public void getPurchase_normalInput_retrievedCorrectly() {
        assertEquals(financeTracker.getPurchase(2), new PurchaseStub());
    }

    @Test
    public void getPurchase_indexNonexistent_throwsError() {
        assertThrows(RuntimeException.class, () -> financeTracker.getPurchase(5));
    }

    @Test
    public void addInstallment_normalInput_addedCorrectly() {
        financeTracker.addInstallment(new InstallmentStub());
        Installment addedInstalment = financeTracker.getInstallment(4);
        assertEquals(new InstallmentStub().getDescription(), addedInstalment.getDescription());
        assertEquals(new InstallmentStub().getMoneySpentOnInstallment(), addedInstalment.getMoneySpentOnInstallment());
        assertEquals(4, financeTracker.getTotalInstallments());
    }

    @Test
    public void deleteInstallment_normalInput_deletedCorrectly() {
        Installment deletedInstallment = financeTracker.deleteInstallment(2);
        assertEquals(new InstallmentStub().getDescription(), deletedInstallment.getDescription());
        assertEquals(new InstallmentStub().getMoneySpentOnInstallment(),
                deletedInstallment.getMoneySpentOnInstallment());
        assertEquals(2, financeTracker.getTotalInstallments());
    }

    @Test
    public void deleteInstallment_indexNonexistent_throwsError() {
        assertThrows(RuntimeException.class, () -> financeTracker.deleteInstallment(4));
        assertEquals(3, financeTracker.getTotalInstallments());
    }

    @Test
    public void editInstallment_normalInputs_editedCorrectly() {
        Installment installment = financeTracker.getInstallment(1);
        Installment editedInstallment = new InstallmentBuilder()
                                                .withDescription("Spotify")
                                                .withMoneySpent("9.50")
                                                .build();
        financeTracker.setInstallment(installment, editedInstallment);
        assertEquals(financeTracker.getInstallment(1), editedInstallment);
    }

    @Test
    public void editInstallment_nonExistentInstallment_throwsError() {
        Installment installment = new InstallmentBuilder()
                                            .withDescription("something")
                                            .build();

        assertThrows(RuntimeException.class, (

        ) -> financeTracker.setInstallment(installment, new InstallmentStub()));
    }

    @Test
    public void getInstallment_normalInput_retrievedCorrectly() {
        assertEquals(financeTracker.getInstallment(2), new InstallmentStub());
    }

    @Test
    public void getInstallment_indexNonexistent_throwsError() {
        assertThrows(RuntimeException.class, () -> financeTracker.getInstallment(5));
    }

    @Test
    public void setMonthlyLimit_normalInput_updatedCorrectly() {
        financeTracker.setMonthlyLimit(new MonthlyLimit("500.0"));
        assertEquals(OptionalDouble.of(500.0), financeTracker.getMonthlyLimit());
    }

    @Test
    public void setMonthlyLimit_negativeNumber_errorThrown() {
        assertThrows(RuntimeException.class, (

                ) -> financeTracker.setMonthlyLimit(new MonthlyLimit("-500")));
    }

    private static class PurchaseStub extends Purchase {
        public PurchaseStub() {
            super(new PurchaseDescription("lunch at deck"), new PurchaseMoneySpent("5.00"));
        }
    }

    private static class InstallmentStub extends Installment {
        public InstallmentStub() {
            super(new InstallmentDescription("Spotify subscription"), new InstallmentMoneyPaid("9.5"));
        }
    }
}


