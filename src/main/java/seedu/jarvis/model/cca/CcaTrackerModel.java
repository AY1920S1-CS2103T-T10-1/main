package seedu.jarvis.model.cca;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.exceptions.CommandException;

/**
 * The API of the CcaTrackerModel component
 *
 */
public interface CcaTrackerModel {

    /**
     * Checks if cca tracker contains the given cca.
     */
    public void contains(Cca cca);

    /**
     * Adds the given cca {@code cca} to the cca tracker.
     *
     */
    public void addCca(Cca cca);

    /**
     * Removes the given cca {@code cca} from the cca tracker.
     */
    public void removeCca(Cca cca);

    /**
     * Updates the given cca {@code toBeUpdatedCca} with the given {@code updatedCca}.
     *
     * @param toBeUpdatedCca
     * @param updatedCca
     */
    public void updateCca(Cca toBeUpdatedCca, Cca updatedCca);

    /**
     * Checks if the cca tracker already has the given {@code cca}.
     *
     * @return true if the cca tracker already has the cca.
     */
    public boolean hasCca(Cca cca);

    /**
     * Returns the CcaTracker {@Code CcaTracker}.
     */
    public CcaTracker getCcaTracker();

    /**
     * Returns the number of {@code Cca} in the CcaTracker.
     *
     * @return the number of {@code Cca}.
     */
    public int getNumberOfCcas();

    /**
     * Return the {@code Cca} based on its index.
     *
     * @return the {@code Cca} based on its index.
     */
    public Cca getCca(Index index) throws CommandException;
}
