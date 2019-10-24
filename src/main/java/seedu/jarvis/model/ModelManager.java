package seedu.jarvis.model;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.OptionalDouble;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;

import seedu.jarvis.commons.core.GuiSettings;
import seedu.jarvis.commons.core.LogsCenter;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.address.AddressBook;
import seedu.jarvis.model.address.ReadOnlyAddressBook;
import seedu.jarvis.model.address.person.Person;
import seedu.jarvis.model.cca.Cca;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.cca.ccaprogress.CcaProgressList;
import seedu.jarvis.model.course.Course;
import seedu.jarvis.model.course.CoursePlanner;
import seedu.jarvis.model.financetracker.FinanceTracker;
import seedu.jarvis.model.financetracker.MonthlyLimit;
import seedu.jarvis.model.financetracker.exceptions.InstallmentNotFoundException;
import seedu.jarvis.model.financetracker.exceptions.PurchaseNotFoundException;
import seedu.jarvis.model.financetracker.installment.Installment;
import seedu.jarvis.model.financetracker.purchase.Purchase;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.planner.TaskList;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.userprefs.ReadOnlyUserPrefs;
import seedu.jarvis.model.userprefs.UserPrefs;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final HistoryManager historyManager;
    private final AddressBook addressBook;
    private final FinanceTracker financeTracker;
    private final UserPrefs userPrefs;
    private final CoursePlanner coursePlanner;
    private final CcaTracker ccaTracker;
    private final Planner planner;


    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(CcaTracker ccaTracker, HistoryManager historyManager,
                        FinanceTracker financeTracker, ReadOnlyAddressBook addressBook,
                        ReadOnlyUserPrefs userPrefs, Planner planner, CoursePlanner coursePlanner) {
        super();
        requireAllNonNull(ccaTracker, historyManager, financeTracker, addressBook, userPrefs, planner);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.ccaTracker = new CcaTracker(ccaTracker);
        this.historyManager = new HistoryManager(historyManager);
        this.addressBook = new AddressBook(addressBook);
        this.financeTracker = new FinanceTracker(financeTracker);
        this.userPrefs = new UserPrefs(userPrefs);
        this.planner = new Planner(planner);
        this.coursePlanner = new CoursePlanner(coursePlanner);
    }

    public ModelManager() {
        this(new CcaTracker(), new HistoryManager(), new FinanceTracker(), new AddressBook(),
                new UserPrefs(), new Planner(), new CoursePlanner());
    }


    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    //=========== HistoryManager =============================================================================

    /**
     * Gets the {@code HistoryManager}.
     *
     * @return {@code HistoryManager} object.
     */
    @Override
    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    /**
     * Replaces {@code HistoryManager} data with the data in {@code HistoryManager} given as argument.
     *
     * @param historyManager {@code HistoryManager} data to be used.
     */
    @Override
    public void setHistoryManager(HistoryManager historyManager) {
        this.historyManager.resetData(historyManager);
    }

    /**
     * Gets a {@code ObservableList} of {@code Command} objects that are executed.
     *
     * @return {@code ObservableList} of {@code Command} objects.
     */
    @Override
    public ObservableList<Command> getExecutedCommandsList() {
        return historyManager.getExecutedCommandsList();
    }

    /**
     * Gets a {@code ObservableList} of {@code Command} objects that are inversely executed.
     *
     * @return {@code ObservableList} of {@code Command} objects.
     */
    @Override
    public ObservableList<Command> getInverselyExecutedCommandsList() {
        return historyManager.getInverselyExecutedCommandsList();
    }

    /**
     * Gets the number of available executed commands that can be undone.
     *
     * @return The number of available executed commands that can be undone.
     */
    @Override
    public int getAvailableNumberOfExecutedCommands() {
        return historyManager.getNumberOfAvailableExecutedCommands();
    }

    /**
     * Gets the number of available inversely executed commands that can be redone.
     *
     * @return The number of available inversely executed commands that can be redone.
     */
    @Override
    public int getAvailableNumberOfInverselyExecutedCommands() {
        return historyManager.getNumberOfAvailableInverselyExecutedCommands();
    }

    /**
     * Checks whether it is possible to roll back any commands.
     *
     * @return Whether it is possible to roll back any commands.
     */
    @Override
    public boolean canRollback() {
        return historyManager.canRollback();
    }

    /**
     * Checks whether it is possible to commit any commands.
     *
     * @return Whether it is possible to commit any commands.
     */
    @Override
    public boolean canCommit() {
        return historyManager.canCommit();
    }

    /**
     * Adds the latest executed command. {@code Command} to be added must be invertible.
     *
     * @param command {@code Command} to be added.
     */
    @Override
    public void rememberExecutedCommand(Command command) {
        historyManager.rememberExecutedCommand(command);
        historyManager.forgetAllInverselyExecutedCommands();
    }

    /**
     * Rolls back the changes made by the latest executed command by inversely executing the command.
     *
     * @return Whether the inverse execution of the latest executed command was successful.
     */
    @Override
    public boolean rollback() {
        return historyManager.rollback(this);
    }

    /**
     * Commits the changes made by the latest inversely executed command by executing the command.
     *
     * @return Whether the execution of the latest executed command was successful.
     */
    @Override
    public boolean commit() {
        return historyManager.commit(this);
    }

    //=========== FinanceTracker ================================================================================
    /**
     * Gets the {@code FinanceTracker}.
     *
     * @return {@code FinanceTracker} object.
     */
    @Override
    public FinanceTracker getFinanceTracker() {
        return financeTracker;
    }

    /**
     * Retrieves purchase at a particular index as seen on the list of finance tracker.
     *
     * @throws CommandException is thrown if purchase does not exist
     */
    @Override
    public Purchase getPurchase(int paymentIndex) {
        return financeTracker.getPurchase(paymentIndex);
    }

    /**
     * Updates the filter of the purchase list to be viewed with the new predicate.
     *
     * @param predicate to filter purchases
     */
    @Override
    public void updateFilteredPurchaseList(Predicate<Purchase> predicate) {
        financeTracker.updateFilteredPurchaseList(predicate);
    }

    /**
     * Retrieves list of all purchases with current predicate applied
     *
     * @return ObservableList
     */
    @Override
    public ObservableList<Purchase> getFilteredPurchaseList() {
        return financeTracker.getFilteredPurchaseList();
    }

    /**
     * Adds single use payment.
     *
     * @param purchase
     */
    @Override
    public void addPurchase(Purchase purchase) {
        financeTracker.addSinglePurchase(purchase);
    }

    @Override
    public void addPurchase(int zeroBasedIndex, Purchase newPurchase) {
        financeTracker.addSinglePurchase(zeroBasedIndex, newPurchase);
    }

    /**
     * Deletes single use payment.
     *
     * @param itemNumber to be deleted
     */
    @Override
    public Purchase deletePurchase(int itemNumber) throws PurchaseNotFoundException {
        return financeTracker.deleteSinglePurchase(itemNumber);
    }

    /**
     * Deletes a single use payment.
     *
     * @param purchase to be deleted
     */
    @Override
    public void deletePurchase(Purchase purchase) {
        financeTracker.deleteSinglePurchase(purchase);
    }


    /**
     * Checks for the existence of the purchase.
     *
     * @param purchase
     */
    public boolean hasPurchase(Purchase purchase) {
        return financeTracker.hasPurchase(purchase);
    }

    /**
     * Retrieves installment at a particular index as seen on the list of finance tracker.
     *
     * @throws CommandException is thrown if installment does not exist
     */
    @Override
    public Installment getInstallment(int instalIndex) throws InstallmentNotFoundException {
        return financeTracker.getInstallment(instalIndex);
    }

    /**
     * Updates the filter of the installment list to be viewed with the new predicate.
     *
     * @param predicate to filter installments
     */
    @Override
    public void updateFilteredInstallmentList(Predicate<Installment> predicate) {
        financeTracker.updateFilteredInstallmentList(predicate);
    }

    /**
     * Retrieves list of all installments with current predicate applied
     *
     * @return ObservableList
     */
    @Override
    public ObservableList<Installment> getFilteredInstallmentList() {
        return financeTracker.getFilteredInstallmentList();
    }

    /**
     * Adds installment.
     *
     * @param installment
     */
    @Override
    public void addInstallment(Installment installment) {
        financeTracker.addInstallment(installment);
    }

    @Override
    public void addInstallment(int zeroBasedIndex, Installment installment) {
        financeTracker.addInstallment(zeroBasedIndex, installment);
    }

    /**
     * Deletes installment.
     *
     * @param instalNumber
     */
    @Override
    public Installment deleteInstallment(int instalNumber) throws InstallmentNotFoundException {
        return financeTracker.deleteInstallment(instalNumber);
    }

    /**
     * Deletes installment.
     *
     * @param installment
     */
    @Override
    public void deleteInstallment(Installment installment) {
        financeTracker.deleteInstallment(installment);
    }

    /**
     * Checks for the existence of the same installment in the finance tracker.
     *
     * @param installment to be checked
     * @return boolean
     */
    @Override
    public boolean hasInstallment(Installment installment) {
        return financeTracker.hasInstallment(installment);
    }

    /**
     * Replaces the installment in the list with {@code editedInstallment}.
     * The identity of {@code editedInstallment} must not be the same as another existing installment in the
     * list.
     *
     * @param target installment to be replaced
     * @param editedInstallment installment with all fields edited according to command
     */
    @Override
    public void setInstallment(Installment target, Installment editedInstallment) {
        financeTracker.setInstallment(target, editedInstallment);
    }

    /**
     * Sets the monthly limit for spending.
     *
     * @param limit
     */
    @Override
    public void setMonthlyLimit(MonthlyLimit limit) {
        financeTracker.setMonthlyLimit(limit);
    }

    /**
     * Retrieves monthly limit if it has been set by the user.
     *
     * @return Optional containing the monthly limit
     */
    @Override
    public OptionalDouble getMonthlyLimit() {
        return financeTracker.getMonthlyLimit();
    }

    /**
     * Lists all purchases and payments from this month.
     *
     */
    @Override
    public void listSpending() {
        financeTracker.listSpending();
    }

    //=========== AddressBook ================================================================================

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    /**
     * Adds {@code Person} at a given index.
     *
     * @param zeroBasedIndex Zero-based index to add {@code Person} to.
     * @param person {@code Person} to be added.
     */
    @Override
    public void addPerson(int zeroBasedIndex, Person person) {
        addressBook.addPerson(zeroBasedIndex, person);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return addressBook.getFilteredPersonList();
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        addressBook.updateFilteredPersonList(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return historyManager.equals(other.historyManager)
                && financeTracker.equals(other.financeTracker)
                && financeTracker.getFilteredPurchaseList().equals(other.financeTracker.getFilteredPurchaseList())
                && financeTracker.getFilteredInstallmentList().equals(other.financeTracker.getFilteredInstallmentList())
                && planner.equals(other.planner)
                && addressBook.equals(other.addressBook)
                && addressBook.getFilteredPersonList().equals(other.addressBook.getFilteredPersonList())
                && userPrefs.equals(other.userPrefs)
                && coursePlanner.equals(other.coursePlanner)
                && ccaTracker.equals(other.ccaTracker);
    }


    //=========== Cca Tracker ================================================================================

    @Override
    public boolean containsCca(Cca cca) {
        return ccaTracker.containsCca(cca);
    }

    @Override
    public void addCca(Cca cca) {
        requireNonNull(cca);
        ccaTracker.addCca(cca);
    }

    @Override
    public void removeCca(Cca cca) {
        requireNonNull(cca);
        ccaTracker.removeCca(cca);
    }

    @Override
    public void updateCca(Cca toBeUpdatedCca, Cca updatedCca) {
        requireAllNonNull(toBeUpdatedCca, updatedCca);
        ccaTracker.updateCca(toBeUpdatedCca, updatedCca);
    }

    @Override
    public CcaTracker getCcaTracker() {
        return ccaTracker;
    }

    @Override
    public int getNumberOfCcas() {
        return ccaTracker.getNumberOfCcas();
    }

    @Override
    public Cca getCca(Index index) throws CommandException {
        requireNonNull(index.getZeroBased());
        return ccaTracker.getCca(index);
    }

    @Override
    public void updateFilteredCcaList(Predicate<Cca> predicate) {
        ccaTracker.updateFilteredCcaList(predicate);
    };

    @Override
    public ObservableList<Cca> getFilteredCcaList() {
        return ccaTracker.getFilteredCcaList();
    }

    @Override
    public void addProgress(Cca targetCca, CcaProgressList toAddCcaProgressList) {
        ccaTracker.addProgress(targetCca, toAddCcaProgressList);
    }

    @Override
    public void increaseProgress(Index index) {
        ccaTracker.increaseProgress(index);
    }

    //=========== Planner =============================================================

    /**
     * Retrieves the tasks stored in the planner
     * @return a list of tasks stored in the planner
     */
    @Override
    public TaskList getTasks() {
        return planner.getTasks();
    }

    /**
     * Adds a task to the planner
     * @param t the task to be added
     */
    @Override
    public void addTask(Task t) {
        planner.addTask(t);
    }

    /**
     * Adds a {@code Task} at a given {@code Index}
     *
     * @param zeroBasedIndex Zero-based index to add {@code Task} to
     * @param task {@code Task} to be added
     */
    public void addTask(int zeroBasedIndex, Task task) {
        planner.addTask(zeroBasedIndex, task);
    }

    /**
     * Determines whether the planner contains the given task
     * @param t the task in question
     * @return true if the planner already contains the task, false if
     *         it does not.
     */
    @Override
    public boolean hasTask(Task t) {
        return planner.hasTask(t);
    }

    /**
     * Retrieves the Planner object
     * @return the planner
     */
    @Override
    public Planner getPlanner() {
        return planner;
    }

    /**
     * Clears all data in a planner to return a new planner
     * @param planner {@code Planner} to take reference from.
     */
    @Override
    public void resetData(Planner planner) {
        this.planner.resetData(planner);
    }

    /**
     * Retrieves a task from the planner at the specified index
     * @param index index of the task that is being retrieved
     * @return the task at the given index
     */
    @Override
    public Task getTask(Index index) {
        return planner.getTask(index);
    }

    /**
     * Deletes a task in the planner at the specified index
     * @param index index of the task to be deleted
     */
    @Override
    public void deleteTask(Index index) {
        planner.deleteTask(index);
    }

    /**
     * Deletes the specified task in the planner
     * @param t the task to be deleted
     */
    @Override
    public void deleteTask(Task t) {
        planner.deleteTask(t);
    }

    /**
     * Retrieves the size of the planner, i.e. the number of tasks in the planner
     * @return the size of the planner
     */
    @Override
    public int size() {
        return planner.size();
    }
    //=========== Course Planner ========================================================

    @Override
    public void lookUpCourse(Course course) {
        coursePlanner.lookUpCourse(course);
    }

    @Override
    public void addCourse(Course course) {
        coursePlanner.addCourse(course);
    }

    @Override
    public void addCourse(int zeroBasedIndex, Course course) {
        coursePlanner.addCourse(zeroBasedIndex, course);
    }

    @Override
    public void deleteCourse(Course course) {
        coursePlanner.deleteCourse(course);
    }

    @Override
    public boolean hasCourse(Course course) {
        return coursePlanner.hasCourse(course);
    }

    @Override
    public ObservableList<Course> getUnfilteredCourseList() {
        return coursePlanner.getCourseList();
    }

    @Override
    public CoursePlanner getCoursePlanner() {
        return coursePlanner;
    }

    @Override
    public String getStringToDisplay() {
        return coursePlanner.getShowString();
    }
}
