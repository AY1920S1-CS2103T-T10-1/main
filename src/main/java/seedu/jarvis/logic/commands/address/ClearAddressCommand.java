package seedu.jarvis.logic.commands.address;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.model.AddressModel.PREDICATE_SHOW_ALL_PERSONS;

import javafx.collections.ObservableList;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.model.AddressBook;
import seedu.jarvis.model.AddressModel;
import seedu.jarvis.model.person.Person;

/**
 * Clears the address book.
 */
public class ClearAddressCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    public static final String MESSAGE_INVERSE_SUCCESS_RESTORE = "Address book has been restored!";

    public static final boolean HAS_INVERSE = true;

    private ObservableList<Person> clearedPersons;

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
     * Clears all {@code Person} from address book.
     *
     * @param addressModel {@code AddressModel} which the command should operate on.
     * @return {@code CommandResult} of a successful clear.
     */
    @Override
    public CommandResult execute(AddressModel addressModel) {
        requireNonNull(addressModel);

        addressModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        clearedPersons = addressModel.getFilteredPersonList();
        addressModel.setAddressBook(new AddressBook());

        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Restores all {@code Person} that was cleared from address book from execution.
     * Any new {@code Person} that was added to address book after the clear execution is deleted.
     *
     * @param addressModel {@code AddressModel} which the command should inversely operate on.
     * @return {@code CommandResult} of a successful restore.
     */
    @Override
    public CommandResult executeInverse(AddressModel addressModel) {
        requireNonNull(addressModel);

        // deletes persons that were not inside address book prior to the clear execution
        addressModel.setAddressBook(new AddressBook());

        clearedPersons.forEach(addressModel::addPerson);
        return new CommandResult(MESSAGE_INVERSE_SUCCESS_RESTORE);
    }

}
