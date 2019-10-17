package seedu.jarvis.model.planner;

import static java.util.Objects.requireNonNull;

import seedu.jarvis.model.planner.tasks.Task;

/**
 * Represents the planner feature in JARVIS
 */
public class Planner {
    protected TaskList taskList;

    /**
     * Constructs an empty planner
     */
    public Planner() {
        this.taskList = new TaskList();
    }

    /**
     * Constructs a Planner with reference from another Planner,
     * adding its commands to this Planner.
     *
     * @param planner {@code Planner} whose data this {@code Planner} is taking reference from.
     */
    public Planner(Planner planner) {
        this();
        resetData(planner);
    }

    /**
     * Retrieves all the tasks in the planner
     * @return a list of tasks stored in the planner
     */
    public TaskList getTasks() {
        return taskList;
    }

    /**
     * Adds a task to the planner
     * @param t the task to be added
     */
    public void addTask(Task t) {
        taskList.add(t);
    }

    /**
     * Determines whether the planner contains the given task
     * @param t the task in question
     * @return true if the planner already contains the task, false if
     *         it does not.
     */
    public boolean hasTask(Task t) {
        return taskList.hasTask(t);
    }

    /**
     * Resets all commands in {@code executedCommands} and {@code inverselyExecutedCommands} to the commands in the
     * given {@code Planner}.
     *
     * @param planner {@code Planner} to take reference from.
     */
    public void resetData(Planner planner) {
        requireNonNull(planner);
        this.taskList = new TaskList(planner.getTasks().getTasks());
    }

    /**
     * Checks if one planner is equal to the other
     * @param other the planner to be compared against
     * @return true if both planners are equal, false if they are not
     */
    @Override
    public boolean equals(Object other) {
        // short circuit if it is the same object.
        if (other == this) {
            return true;
        }

        // instanceof handles nulls.
        if (!(other instanceof Planner)) {
            return false;
        }

        other = (Planner) other;
        return taskList.equals(((Planner) other).getTasks());
    }
}
