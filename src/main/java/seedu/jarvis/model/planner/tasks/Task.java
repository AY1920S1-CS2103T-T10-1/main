package seedu.jarvis.model.planner.tasks;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import seedu.jarvis.model.address.tag.Tag;
import seedu.jarvis.model.planner.Frequency;
import seedu.jarvis.model.planner.Priority;

/**
 * Represents a task object in JARVIS
 */
public abstract class Task {
    //add t/TASK TYPE/TASK DES [d/DATE] [#TAG]... [p/PRIORITY LEVEL] [r/FREQ]

    public static final String EVENT = "event";
    public static final String DEADLINE = "deadline";
    public static final String TODO = "todo";
    protected static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    protected String taskDes;
    protected Priority priority = null;
    protected Frequency frequency = null;
    protected Set<Tag> tags = new HashSet<>();

    public Task(String taskDes) {
        this.taskDes = taskDes;
    }

    /**
     * Gets the task description of this {@code Todo}.
     * @return {@code String} containing the description of this {@code Todo}.
     */
    public String getTaskDescription() {
        return taskDes;
    }

    /**
     * Gets the {@code Priority} of this {@code Todo}.
     * @return {@code Priority} of the {@code Todo}.
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * Gets the {@code Frequency} of the {@Code Todo}.
     * @return {@code Frequency} of the {@code Todo}.
     */
    public Frequency getFrequency() {
        return frequency;
    }

    public abstract String toString();

    /**
     * Checks if this task is equal to another task
     * Condition for equality: same type of task && same description
     * @param other the task to be compared to
     * @return true if both tasks are equal, false if they are not
     */
    @Override
    public abstract boolean equals(Object other);

    /**
     * Sets the Priority Level of a Task
     * @param priority User input priority level
     */
    public void addPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * Sets the frequency level of a Task, i.e. how regularly a Task occurs.
     * @param freq Frequency level of a task
     */
    public void addFrequency(Frequency freq) {
        frequency = freq;
    }

    /**
     * Adds a Tag to the set of Tags attached to each Task
     * @param t Tag to be added
     */
    public void addTag(Tag t) {
        this.tags.add(t);
    }

    /**
     * Retrieves all the tags tagged to a particular task
     * @return a set of Tags
     */
    protected Set<Tag> getTags() {
        return this.tags;
    }

    /**
     * Retrieves the DateTimeFormatter used for Event and Deadline tasks
     */
    public static DateTimeFormatter getDateFormat() {
        return dateFormat;
    }

    /**
     * To show all the attributes (priority, frequency and tags) present in a task
     * It will not return any attributes if the task does not contain any, i.e. it will not show null
     * @return a string of all the attributes present in a task.
     */
    protected String attributesString() {
        String priority = this.priority == null ? "" : "\nPriority: " + this.priority;
        String frequency = this.frequency == null ? "" : "\nFrequency: " + this.frequency;
        String tags = this.tags.isEmpty() ? "" : "\nTags: " + getTags();

        return priority + frequency + tags;
    }
}
