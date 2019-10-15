package seedu.jarvis.logic.commands.finance;

import static java.util.Objects.requireNonNull;

import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.financetracker.Purchase;
import seedu.jarvis.model.financetracker.exceptions.PurchaseNotFoundException;

/**
 * Deletes an existing purchase identified using its displayed index in the finance tracker.
 */
public class RemovePaidCommand extends Command {

    public static final String COMMAND_WORD = "remove";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the purchase identified by the index number used in the displayed list of purchases.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PURCHASE_SUCCESS = "Deleted Purchase: %1$s";

    public static final String MESSAGE_INVERSE_SUCCESS_ADD = "New person added: %1$s";
    public static final String MESSAGE_INVERSE_PERSON_TO_ADD_ALREADY_EXIST = "Person already added: %1$s";

    public static final boolean HAS_INVERSE = true;

    private final Index targetIndex;

    private Purchase toDelete;

    /**
     * Creates a {@code RemovePaidCommand} and sets the targetIndex to the {@code Index}
     * of the {@code Purchase} to be deleted.
     *
     * @param targetIndex of the {@code Purchase} to be deleted
     */
    public RemovePaidCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Returns whether the command has an inverse execution.
     * If the command has no inverse execution, then calling {@code executeInverse}
     * will be guaranteed to always throw a {@code CommandException}.
     *
     * @return Whether the command has an inverse execution.
     */
    @Override
    public boolean hasInverseExecution() {
        return HAS_INVERSE;
    }

    /**
     * Deletes {@code Purchase} from address book.
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} of a successful delete.
     * @throws CommandException If targetIndex is >= the number of persons in address book.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            toDelete = model.getPurchase(targetIndex.getZeroBased());
            model.deletePurchase(targetIndex.getZeroBased());
            return new CommandResult(String.format(MESSAGE_DELETE_PURCHASE_SUCCESS));
        } catch (PurchaseNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_PURCHASE_DISPLAYED_INDEX);
        }
    }

    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        return null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof RemovePaidCommand
                && toDelete.equals((((RemovePaidCommand) other).toDelete)));
    }
}
