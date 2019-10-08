package seedu.jarvis.model.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.model.financetracker.FinanceTracker;
import seedu.jarvis.model.financetracker.Instalment;
import seedu.jarvis.model.financetracker.InstalmentList;
import seedu.jarvis.model.financetracker.Purchase;
import seedu.jarvis.model.financetracker.PurchaseList;

/**
 * Tests logic of finance tracker class.
 */
public class FinanceTrackerTest {

    private FinanceTracker financeTracker;

    @BeforeEach
    public void setUp() {
        financeTracker = new FinanceTracker();
        ArrayList<Purchase> allPurchases = new ArrayList<>();
        allPurchases.add(new PurchaseStub());
        allPurchases.add(new PurchaseStub());
        allPurchases.add(new PurchaseStub());
        financeTracker.setPurchaseList(new PurchaseList(allPurchases));
        ArrayList<Instalment> allInstalments = new ArrayList<>();
        allInstalments.add(new InstalmentStub());
        allInstalments.add(new InstalmentStub());
        allInstalments.add(new InstalmentStub());
        financeTracker.setInstalmentList(new InstalmentList(allInstalments));
    }

    @Test
    public void addPayment_normalInput_addedCorrectly() {
        financeTracker.addSinglePayment(new PurchaseStub2());
        Purchase addedPurchase = financeTracker.getPayment(4);
        assertEquals(new PurchaseStub2().getDescription(), addedPurchase.getDescription());
        assertEquals(new PurchaseStub2().getMoneySpent(), addedPurchase.getMoneySpent());
        assertEquals(4, financeTracker.getTotalPurchases());
    }

    @Test
    public void deletePayment_normalInput_deletedCorrectly() {
        Purchase deletedPurchase = financeTracker.deleteSinglePayment(2);
        assertEquals(new PurchaseStub().getDescription(), deletedPurchase.getDescription());
        assertEquals(new PurchaseStub().getMoneySpent(), deletedPurchase.getMoneySpent());
        assertEquals(2, financeTracker.getTotalPurchases());
    }

    @Test
    public void deletePayment_indexNonexistent_throwsError() {
        assertThrows(IndexOutOfBoundsException.class, () -> financeTracker.deleteSinglePayment(4));
        assertEquals(3, financeTracker.getTotalPurchases());
    }

    @Test
    public void addInstalment_normalInput_addedCorrectly() {
        financeTracker.addInstalment(new InstalmentStub());
        Instalment addedInstalment = financeTracker.getInstalment(4);
        assertEquals(new InstalmentStub().getDescription(), addedInstalment.getDescription());
        assertEquals(new InstalmentStub().getMoneySpentOnInstallment(), addedInstalment.getMoneySpentOnInstallment());
        assertEquals(4, financeTracker.getTotalInstalments());
    }

    @Test
    public void deleteInstalment_normalInput_deletedCorrectly() {
        Instalment deletedInstalment = financeTracker.deleteInstalment(2);
        assertEquals(new InstalmentStub().getDescription(), deletedInstalment.getDescription());
        assertEquals(new InstalmentStub().getMoneySpentOnInstallment(),
                deletedInstalment.getMoneySpentOnInstallment());
        assertEquals(2, financeTracker.getTotalInstalments());
    }

    @Test
    public void deleteInstalment_indexNonexistent_throwsError() {
        assertThrows(IndexOutOfBoundsException.class, () -> financeTracker.deleteInstalment(4));
        assertEquals(3, financeTracker.getTotalInstalments());
    }

    @Test
    public void editInstalment_normalInputs_editedCorrectly() {
        financeTracker.editInstalment(1, "Student price Spotify subscription", 7.50);
        assertEquals("Student price Spotify subscription",
                financeTracker.getInstalment(1).getDescription());
        assertEquals(7.50, financeTracker.getInstalment(1).getMoneySpentOnInstallment());
    }

    @Test
    public void editInstalment_indexNonexistent_throwsError() {
        assertThrows(IndexOutOfBoundsException.class, (

        ) -> financeTracker.editInstalment(5, "Spotify", 9.50));
    }

    @Test
    public void editInstalment_emptyDescription_throwsError() {
        assertThrows(NullPointerException.class, (

            ) -> financeTracker.editInstalment(3, null, 9.50));
    }

    @Test
    public void setMonthlyLimit_normalInput_updatedCorrectly() {
        financeTracker.setMonthlyLimit(500.0);
        assertEquals(500.0, financeTracker.getMonthlyLimit());
    }

}

class PurchaseStub2 extends Purchase {
    public PurchaseStub2() {
        super("lunch at deck", 5.00);
    }
}
