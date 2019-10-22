package seedu.jarvis.model.planner;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.model.planner.tasks.Task;

/**
 * The API of the PlannerModel component
 */
public interface PlannerModel {

    /**
     * Retrieves the tasks stored in the planner
     * @return a list of tasks stored in the planner
     */
    TaskList getTasks();

    /**
     * Adds a task to the planner
     * @param t Task to be added to the planner
     */
    void addTask(Task t);

    /**
     * Adds a {@code Task} at a given {@code Index}
     *
     * @param zeroBasedIndex Zero-based index to add {@code Task} to
     * @param task {@code Task} to be added
     */
    void addTask(int zeroBasedIndex, Task task);

    /**
     * Determines whether the planner contains the given task
     * @param t the task in question
     * @return true if the planner already contains the task, false if
     *         it does not.
     */
    boolean hasTask(Task t);

    /**
     * Retrieves the planner object
     * @return the planner object
     */
    Planner getPlanner();

    /**
     * Resets all commands in {@code executedCommands} and {@code inverselyExecutedCommands} to the commands in the
     * given {@code Planner}.
     *
     * @param planner {@code Planner} to take reference from.
     */
    void resetData(Planner planner);

    /**
     * Retrieves the task at the specified index
     *
     * @param index index of the task that is being retrieved
     * @return the task at the specified index
     */
    Task getTask(Index index);

    /**
     * Deletes the task at the specified index
     *
     * @param index index of the task to be deleted
     */
    void deleteTask(Index index);

    /**
     * Deletes the specified task from the planner
     * @param t the task to be deleted
     */
    void deleteTask(Task t);

    /**
     * Retrieves the size of the planner, i.e. the number of tasks in the planner
     * @return the size of the planner
     */
    int size();

    /**
     * Looks through all the tasks in the planner to find the tasks that
     * match the keywords in the predicate
     *
     * @param predicate contains a list of keywords
     * @return a {@code TaskList} of all the tasks in the planner that match
     * any of the given keywords
     */
    TaskList find(TaskDesContainsKeywordsPredicate predicate);
}
