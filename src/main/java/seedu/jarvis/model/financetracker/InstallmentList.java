package seedu.jarvis.model.financetracker;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.model.address.person.exceptions.DuplicateInstallmentException;
import seedu.jarvis.model.financetracker.exceptions.InstallmentNotFoundException;
import seedu.jarvis.model.financetracker.installment.Installment;

/**
 * Manages a list of instalments saved by the user.
 */
public class InstallmentList {
    private ArrayList<Installment> allInstallments;
    private double totalMoneySpentOnInstallments;

    /**
     * Default constructor to be used when JARVIS starts up.
     */
    public InstallmentList(ArrayList<Installment> allInstallments) {
        this.allInstallments = allInstallments;
        this.totalMoneySpentOnInstallments = calculateTotalInstallmentSpending();
    }

    //=========== Reset Methods ==================================================================================

    /**
     * Empty constructor to be used when there are no instalments previously stored by the user.
     */
    public InstallmentList() {
        allInstallments = new ArrayList<>();
        totalMoneySpentOnInstallments = 0;
    }

    /**
     * Constructs an InstallmentList with reference from another InstallmentList,
     * updating all existing fields from another InstallmentList.
     */
    public InstallmentList(InstallmentList installmentList) {
        this();
        resetData(installmentList);
    }

    /**
     * Resets all data from {@code allInstallments} and {@code totalMoneySpentOnInstallments}
     * from the given {@code installmentList}.
     *
     * @param installmentList
     */
    public void resetData(InstallmentList installmentList) {
        requireNonNull(installmentList);
        this.allInstallments = installmentList.getAllInstallments();
        this.totalMoneySpentOnInstallments = installmentList.getTotalMoneySpentOnInstallments();
    }

    //=========== Getter Methods ==================================================================================

    public double getTotalMoneySpentOnInstallments() {
        return totalMoneySpentOnInstallments;
    }

    /**
     * Retrieves the installment at that particular index.
     *
     * @param installmentNumber of the installment to be retrieved as seen on the list of installments
     * @return Installment
     * @throws InstallmentNotFoundException if the index is greater than the number of installments
     */
    public Installment getInstallment(int installmentNumber) throws InstallmentNotFoundException {
        try {
            Index index = Index.fromOneBased(installmentNumber);
            return allInstallments.get(index.getZeroBased());
        } catch (IndexOutOfBoundsException e) {
            throw new InstallmentNotFoundException();
        }
    }

    public int getNumInstallments() {
        return allInstallments.size();
    }

    public ArrayList<Installment> getAllInstallments() {
        return allInstallments;
    }

    //=========== Command Methods ==================================================================================

    /**
     * Add installment to the list of installments
     * @param newInstallment to be added
     */
    public void addInstallment(Installment newInstallment) {
        allInstallments.add(newInstallment);
        totalMoneySpentOnInstallments = calculateTotalInstallmentSpending();
    }

    /**
     * User requests to edit a particular instalment based on its index. Both description and money spent can be edited.
     *
     * @param installmentNumber of the installment to be edited
     * @param description of the installment to be edited
     * @param value of the installment to be edited
     */
    public void editInstallment(int installmentNumber, String description, double value) {
        if (installmentNumber < 1) {
            throw new InstallmentNotFoundException();
        }

        requireNonNull(description);
        Index index = Index.fromOneBased(installmentNumber);
        allInstallments.get(index.getZeroBased()).editDescription(description);
        allInstallments.get(index.getZeroBased()).editAmount(value);
        totalMoneySpentOnInstallments = calculateTotalInstallmentSpending();
    }

    /**
     * Replaces the installment {@code target} in the list with {@code editedInstallment}.
     *
     * {@code target} must exist in the list.
     * The identity of {@code editedInstallment} must not be the same as another existing installment in the
     * list.
     */
    public void setInstallment(Installment target, Installment editedInstallment) {
        requireAllNonNull(target, editedInstallment);

        int index = allInstallments.indexOf(target);
        if (index == -1) {
            throw new InstallmentNotFoundException();
        }

        if (!target.isSameInstallment(editedInstallment) && contains(editedInstallment)) {
            throw new DuplicateInstallmentException();
        }

        allInstallments.set(index, editedInstallment);
        totalMoneySpentOnInstallments = calculateTotalInstallmentSpending();
    }

    /**
     * Checks for the existence of the installment that has already been added to avoid duplicates in the list.
     *
     * @param installment that is to be newly added
     * @return boolean checking the existence of the same installment
     */
    public boolean hasInstallment(Installment installment) {
        return this.contains(installment);
    }

    /**
     * Returns true if the list contains an equivalent installment as the given argument.
     */
    private boolean contains(Installment toCheck) {
        requireNonNull(toCheck);
        return allInstallments.stream().anyMatch(toCheck::isSameInstallment);
    }


    /**
     * Deletes instalment from the list of instalments based on the instalment number.
     *
     * @param installmentNumber of the instalment in the list
     * @return Instalment object that has been removed from the list
     * @throws InstallmentNotFoundException if the installment does not exist
     */
    public Installment deleteInstallment(int installmentNumber) throws InstallmentNotFoundException {
        try {
            Index index = Index.fromOneBased(installmentNumber);
            totalMoneySpentOnInstallments = calculateTotalInstallmentSpending();
            return allInstallments.remove(index.getZeroBased());
        } catch (IndexOutOfBoundsException e) {
            throw new InstallmentNotFoundException();
        }
    }

    /**
     * Calculates the total monthly spending from all instalments currently subscribed to by the user.
     *
     * @return double containing the total money spent to be included in monthly expenditure
     */
    private double calculateTotalInstallmentSpending() {
        double amount = 0;
        for (Installment instalment : allInstallments) {
            amount += instalment.getMoneySpentOnInstallment().getInstallmentMoneyPaid();
        }
        return amount;
    }

    //=========== Common Methods ==================================================================================

    @Override
    public String toString() {
        String lstInstallments = "Here are your current subscriptions: + \n";
        int index = 1;
        for (Installment installment : allInstallments) {
            lstInstallments += index + ". " + installment.toString();
        }
        return lstInstallments;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InstallmentList // instanceof handles nulls
                && allInstallments.equals(((InstallmentList) other).allInstallments));
    }
}
