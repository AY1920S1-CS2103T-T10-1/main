package seedu.jarvis.logic;

import java.nio.file.Path;

import javafx.beans.value.ObservableStringValue;
import javafx.collections.ObservableList;
import seedu.jarvis.commons.core.GuiSettings;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.address.ReadOnlyAddressBook;
import seedu.jarvis.model.address.person.Person;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.course.Course;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

        /**
     * Returns a view of the executed commands.
     */
    ObservableList<Command> getExecutedCommandsList();

    /**
     * Returns a view of the inversely executed commands.
     */
    ObservableList<Command> getInverselyExecutedCommandsList();

    // Planner =============================================================

    /**
     * Returns an unmodifiable view of the filtered list of tasks
     */
    ObservableList<Task> getFilteredTaskList();

    //TODO jdocs
    ObservableList<Task> getUnfilteredTaskList();

    // Course Planner ====================================================================

    /**
     * Returns an unmodifiable view of the unfiltered list of courses.
     */
    ObservableList<Course> getUnfilteredCourseList();

    /**
     * Returns the text displayed to the user in the Course Planner.
     */
    ObservableStringValue getCourseTextDisplay();
}
