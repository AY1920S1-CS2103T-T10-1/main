package seedu.jarvis.model.planner.tasks;

/**
 * Represents a Todo task in JARVIS
 */
public class Todo extends Task {

    public Todo(String taskDes) {
        super(taskDes);
    }

    @Override
    public String toString() {
        return "Todo: " + this.taskDes + "\nPriority: " + priority + "\nFrequency: " + frequency + "\nTags:"
                + getTags().toString();
    }

    /**
     * Checks if this task is equal to another task
     * Condition for equality: same type of task && same description
     * @param other the task to be compared to
     * @return true if both tasks are equal, false if they are not
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Todo)) {
            return false;
        }

        return taskDes.equals(((Todo) other).taskDes);
    }
}
