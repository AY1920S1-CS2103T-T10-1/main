package seedu.jarvis.model.cca;

import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.jarvis.model.cca.ccaprogress.CcaMilestoneList;
import seedu.jarvis.model.cca.ccaprogress.CcaProgress;
import seedu.jarvis.model.cca.exceptions.CcaProgressAlreadySetException;
import seedu.jarvis.model.cca.exceptions.MaxProgressNotSetException;

/**
 * Represents a Cca in the Jarvis parser.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Cca {

    // Identity fields
    private final CcaName name;
    private final CcaType ccaType;

    // Data fields
    private final EquipmentList equipmentList;
    private final CcaProgress ccaProgress;

    /**
     * Every field must be present and not null.
     */
    public Cca(CcaName name, CcaType ccaType, EquipmentList equipmentList, CcaProgress ccaProgress) {
        requireAllNonNull(name, ccaType, equipmentList);
        this.name = name;
        this.ccaType = ccaType;
        this.equipmentList = equipmentList;
        this.ccaProgress = ccaProgress;
    }

    /**
     * Constructor to be used when Cca is first added.
     */
    public Cca(CcaName name, CcaType ccaType, EquipmentList equipmentList) {
        requireAllNonNull(name, ccaType, equipmentList);
        this.name = name;
        this.ccaType = ccaType;
        this.equipmentList = equipmentList;
        this.ccaProgress = new CcaProgress();
    }

    public CcaName getName() {
        return name;
    }

    public CcaType getCcaType() {
        return ccaType;
    }

    public EquipmentList getEquipmentList() {
        return equipmentList;
    }

    public CcaProgress getCcaProgress() {
        return ccaProgress;
    }

    /**
     * Returns true if the cca progresslist is empty.
     *
     * @return true if the cca progresslist is empty.
     */
    public boolean ccaProgressListIsEmpty() {
        return ccaProgress.ccaProgressListIsEmpty();
    }

    /**
     * Adds a progresslist to the {@code CcaProgress}.
     */
    public void addProgress(CcaMilestoneList ccaMilestoneList) {
        if (!ccaProgressListIsEmpty()) {
            throw new CcaProgressAlreadySetException();
        }
        ccaProgress.setMilestones(ccaMilestoneList);
    }

    /**
     * Increments the progress of the {@code Cca} by 1 {@code Milestone}.
     */
    public void increaseProgress() {
        ccaProgress.increaseProgress();
    }

    /**
     * Checks if the CcaProgress is already set.
     */
    public boolean containsProgress() {
        return !ccaProgress.ccaProgressListIsEmpty();
    }

    /**
     * Checks if the CcaProgress is at max.
     */
    public boolean progressAtMaxIncrement() {
        return ccaProgress.progressAtMax();
    }

    /**
     * Gets the current progress percentage of the Cca.
     */
    public double getCcaProgressPercentage() throws MaxProgressNotSetException {
        return ccaProgress.getCcaProgressPercentage();
    }

    /**
     * Returns true if both ccas of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two ccas.
     */
    public boolean isSameCca(Cca otherCca) {
        if (otherCca == this) {
            return true;
        }

        return otherCca != null
                && otherCca.getName().equals(getName())
                && otherCca.getCcaType().equals(getCcaType());
    }

    /**
     * Returns true if both ccas have the same identity and data fields.
     * This defines a stronger notion of equality between two ccas.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Cca)) {
            return false;
        }

        Cca otherCca = (Cca) other;
        return otherCca.getName().equals(getName())
                && otherCca.getCcaType().equals(getCcaType())
                && otherCca.getEquipmentList().equals(getEquipmentList())
                && otherCca.getCcaProgress().equals(getCcaProgress());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, ccaType, equipmentList, ccaProgress);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("-")
                .append(getCcaType())
                .append(". ")
                .append(getEquipmentList());
        return builder.toString();
    }
}
