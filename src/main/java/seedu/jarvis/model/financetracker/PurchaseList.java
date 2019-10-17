package seedu.jarvis.model.financetracker;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.model.financetracker.exceptions.PurchaseNotFoundException;
import seedu.jarvis.model.financetracker.purchase.Purchase;

/**
 * Manages list of monthly expenditures made by the user.
 */
public class PurchaseList {
    private ArrayList<Purchase> allPurchases;

    /**
     * Default constructor to be used when JARVIS starts up.
     */
    public PurchaseList(ArrayList<Purchase> allPurchases) {
        this.allPurchases = allPurchases;
    }

    //=========== Reset Methods ==================================================================================

    /**
     * Empty constructor to be used when there are no purchases previously stored by the user.
     */
    public PurchaseList() {
        this.allPurchases = new ArrayList<>();
    }

    /**
     * Constructs an PurchaseList with reference from another PurchaseList,
     * updating all existing fields from another PurchaseList.
     */
    public PurchaseList(PurchaseList purchaseList) {
        this();
        resetData(purchaseList);
    }

    /**
     * Resets all data from {@code allPurchases} from the given {@code purchaseList}.
     *
     * @param purchaseList
     */
    public void resetData(PurchaseList purchaseList) {
        requireNonNull(purchaseList);
        this.allPurchases = purchaseList.getAllPurchases();
    }

    //=========== Getter Methods ==================================================================================

    public Purchase getPurchase(int purchaseIndex) {
        try {
            Index index = Index.fromOneBased(purchaseIndex);
            return allPurchases.get(index.getZeroBased());
        } catch (IndexOutOfBoundsException e) {
            throw new PurchaseNotFoundException();
        }
    }

    public int getNumPurchases() {
        return allPurchases.size();
    }

    public ArrayList<Purchase> getAllPurchases() {
        return allPurchases;
    }

    //=========== Command Methods ==================================================================================

    /**
     * Adds a single-use purchases to the list of purchases
     *
     * @param newPurchase object from newly added single-use payment
     */
    public void addSinglePurchase(Purchase newPurchase) {
        allPurchases.add(newPurchase);
    }

    /**
     * Removes a particular purchase from the list and returns purchase.
     *
     * @param purchaseIndex
     * @return Purchase that was just deleted from the user's list of purchases
     */
    public Purchase deletePurchase(int purchaseIndex) throws PurchaseNotFoundException {
        try {
            Index index = Index.fromOneBased(purchaseIndex);
            return allPurchases.remove(index.getZeroBased());
        } catch (IndexOutOfBoundsException e) {
            throw new PurchaseNotFoundException();
        }
    }

    /**
     * Replaces the purchase {@code target} in the list with {@code editedPurchase}.
     * {@code target} must exist in the list.
     * The identity of {@code editedPurchase} must not be the same as another existing purchase in the
     * list.
     */
    public void setPurchase(Purchase target, Purchase editedPurchase) {
        requireAllNonNull(target, editedPurchase);

        int index = allPurchases.indexOf(target);
        if (index == -1) {
            throw new PurchaseNotFoundException();
        }

        allPurchases.set(index, editedPurchase);
    }

    /**
     * Calculates the total spending based on the list of purchases.
     *
     * @return double value containing total expenditure
     */
    public double totalSpending() {
        double total = 0;
        for (Purchase purchase : allPurchases) {
            total += purchase.getMoneySpent().getPurchaseAmount();
        }
        return total;
    }

    //=========== Common Methods ==================================================================================

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PurchaseList // instanceof handles nulls
                && allPurchases.equals(((PurchaseList) other).allPurchases));
    }
}
