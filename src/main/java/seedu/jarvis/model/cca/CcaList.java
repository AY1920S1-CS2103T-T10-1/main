package seedu.jarvis.model.cca;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.cca.ccaprogress.CcaProgressList;
import seedu.jarvis.model.cca.exceptions.CcaNotFoundException;
import seedu.jarvis.model.cca.exceptions.DuplicateCcaException;

/**
 * Represents list of Cccas.
 */
public class CcaList {

    private ObservableList<Cca> internalCcaList = FXCollections.observableArrayList();
    private final ObservableList<Cca> internalUnmodifiableCcaList =
            FXCollections.unmodifiableObservableList(internalCcaList);

    /**
     * Default constructor to be used when JARVIS starts up.
     */
    public CcaList(){

    }

    /**
     * Constructor to be used if there already exists a list of CCAs.
     *
     * @param internalCcaList
     */
    public CcaList(ObservableList<Cca> internalCcaList) {
        requireNonNull(internalCcaList);
        this.internalCcaList = internalCcaList;
    }

    //// list overwrite operations
    public void setCcas(List<Cca> ccaList) {
        requireAllNonNull(ccaList);

        internalCcaList.setAll(ccaList);
    }

    public ObservableList<Cca> getInternalCcaList() {
        return internalCcaList;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Cca> asUnmodifiableObservableList() {
        return internalUnmodifiableCcaList;
    }

    /// Getters and setter
    /**
     * Returns the {@Cca} based on its {@code index}
     *
     * @return the {@Cca} based on its {@code index}.
     */
    public Cca getCca(Index index) throws CommandException {
        requireNonNull(index);

        if (index.getZeroBased() >= internalCcaList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CCA_DISPLAYED_INDEX);
        }

        return internalCcaList.get(index.getZeroBased());
    }

    /**
     * Returns true if the list contains an equivalent cca as the given argument.
     */
    public boolean containsCca(Cca toCheck) {
        requireNonNull(toCheck);
        return internalCcaList.stream().anyMatch(toCheck::isSameCca);
    }

    /**
     * Returns the number of {@code Cca} in the current {@code CcaList}.
     *
     * @return size of the current internalCcaList.
     */
    public int size() {
        return internalCcaList.size();
    }

    /**
     * Adds an cca to the cca list.
     *
     * @param cca
     */
    public void addCca(Cca cca) {
        requireNonNull(cca);
        if (internalCcaList.contains(cca)) {
            throw new DuplicateCcaException();
        }

        internalCcaList.add(cca);
    }

    /**
     * Updates the cca name.
     *
     * @param toBeUpdatedCca
     * @param updatedCca
     */
    public void updateCca(Cca toBeUpdatedCca, Cca updatedCca) {
        requireAllNonNull(toBeUpdatedCca, updatedCca);
        int toBeUpdatedCcaIndex = internalCcaList.indexOf(toBeUpdatedCca);
        if (toBeUpdatedCcaIndex == -1) {
            throw new CcaNotFoundException();
        }

        if (!toBeUpdatedCca.isSameCca(updatedCca) && containsCca(updatedCca)) {
            throw new DuplicateCcaException();
        }

        internalCcaList.set(toBeUpdatedCcaIndex, updatedCca);
    }

    public void addProgress(Cca toBeUpdatedCca, CcaProgressList ccaProgressList) {
        requireAllNonNull(toBeUpdatedCca, ccaProgressList);

        toBeUpdatedCca.addProgress(ccaProgressList);
    }

    /**
     * Removes {@code toBeRemovedCca} from the {@code CcaList}.
     */
    public void removeCca(Cca toBeRemovedCca) {
        requireNonNull(toBeRemovedCca);
        if (!internalCcaList.contains(toBeRemovedCca)) {
            throw new CcaNotFoundException();
        }

        internalCcaList.remove(toBeRemovedCca);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CcaList // instanceof handles nulls
                && internalCcaList.equals(((CcaList) other).internalCcaList));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the ccas in the ccas list: ");
        for (Cca cca : internalCcaList) {
            sb.append(cca);
            sb.append("\n");
        }
        return sb.toString();
    }

}
