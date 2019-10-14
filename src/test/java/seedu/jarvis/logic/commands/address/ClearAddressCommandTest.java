package seedu.jarvis.logic.commands.address;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandInverseSuccess;
import static seedu.jarvis.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jarvis.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.address.AddressBook;
import seedu.jarvis.model.address.person.Person;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.userprefs.UserPrefs;
import seedu.jarvis.testutil.PersonBuilder;

public class ClearAddressCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
    }

    /**
     * Verifies that checking {@code ClearAddressCommand} for the availability of inverse execution returns true.
     */
    @Test
    public void hasInverseExecution() {
        ClearAddressCommand clearAddressCommand = new ClearAddressCommand();
        assertTrue(clearAddressCommand.hasInverseExecution());
    }

    @Test
    public void execute_emptyAddressBook_success() {
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearAddressCommand(), model,
                ClearAddressCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {

        model = new ModelManager(new CcaTracker(), new HistoryManager(), getTypicalAddressBook(), new UserPrefs(), 
                                 new Planner());
        Model expectedModel = new ModelManager(new CcaTracker(), new HistoryManager(), getTypicalAddressBook(),
                new UserPrefs(), new Planner());

        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearAddressCommand(), model,
                ClearAddressCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * Ensures that the {@code CommandResult} with the appropriate message is returned from a successful inverse
     * execution, that the address book is restored to all its previous data.
     */
    @Test
    public void executeInverse_success() {
        ClearAddressCommand clearAddressCommand = new ClearAddressCommand();
        model = new ModelManager(new CcaTracker(), new HistoryManager(), getTypicalAddressBook(), 
                                 new UserPrefs(), new Planner());
        Person validPerson = new PersonBuilder().build();
        model.addPerson(validPerson);
        Model expectedModel = new ModelManager(new CcaTracker(), new HistoryManager(), getTypicalAddressBook(),
                new UserPrefs(), new Planner());
        expectedModel.setAddressBook(new AddressBook());
        assertCommandSuccess(clearAddressCommand, model, ClearAddressCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.setAddressBook(getTypicalAddressBook());
        assertCommandInverseSuccess(clearAddressCommand, model, ClearAddressCommand.MESSAGE_INVERSE_SUCCESS_RESTORE,
                expectedModel);
    }

    /**
     * Tests that repeatedly executing and inversely executing command works as intended.
     */
    @Test
    public void repeatedExecutionAndInverseExecution() {
        ClearAddressCommand clearAddressCommand = new ClearAddressCommand();

        model = new ModelManager(new CcaTracker(), new HistoryManager(), getTypicalAddressBook(), 
                                 new UserPrefs(), new Planner());
        Model expectedModel = new ModelManager(new CcaTracker(), new HistoryManager(), getTypicalAddressBook(),
                new UserPrefs(), new Planner());
        Person validPerson = new PersonBuilder().build();

        int cycles = 1000;
        IntStream.range(0, cycles)
                .forEach(index -> {
                    expectedModel.setAddressBook(new AddressBook());
                    assertCommandSuccess(clearAddressCommand, model, ClearAddressCommand.MESSAGE_SUCCESS,
                            expectedModel);

                    expectedModel.setAddressBook(getTypicalAddressBook());
                    assertCommandInverseSuccess(clearAddressCommand, model,
                            ClearAddressCommand.MESSAGE_INVERSE_SUCCESS_RESTORE, expectedModel);
                });
    }
}
