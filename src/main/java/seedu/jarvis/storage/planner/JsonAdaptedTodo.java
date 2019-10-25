package seedu.jarvis.storage.planner;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.model.planner.Frequency;
import seedu.jarvis.model.planner.Priority;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.planner.tasks.Todo;

/**
 * Jackson-friendly version of {@link Todo}.
 */
public class JsonAdaptedTodo extends JsonAdaptedTask {

    /**
     * Constructs a {@code JsonAdaptedTodo} with the given todo details.
     *
     * @param description Description of the todo.
     */
    @JsonCreator
    public JsonAdaptedTodo(@JsonProperty("description") String description, @JsonProperty("priority") String priority,
                           @JsonProperty("frequency") String frequency) {
        super(description, priority, frequency);
    }

    /**
     * Converts a given {@code Todo} into this class for Jackson use.
     *
     * @param todo {@code Todo} to be used to construct the {@code JsonAdaptedTodo}.
     */
    public JsonAdaptedTodo(Todo todo) {
        super(todo);
    }

    /**
     * Converts this Jackson-friendly adapted {@code Todo} into the model's {@code Task} object.
     *
     * @return {@code Task} of the Jackson-friendly adapted {@code Todo}.
     * @throws IllegalValueException If there were any data constraints violated in the adapted {@code Todo}.
     */
    @Override
    public Task toModelType() throws IllegalValueException {
        Todo todo = new Todo(description);
        updatePriorityAndFrequency(todo);
        return todo;
    }

}
