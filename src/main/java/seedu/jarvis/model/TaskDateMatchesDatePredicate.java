package seedu.jarvis.model;

import seedu.jarvis.model.planner.tasks.Deadline;
import seedu.jarvis.model.planner.tasks.Event;
import seedu.jarvis.model.planner.tasks.Task;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Tests that the task date of a {@code Task} matches any of the
 * dates given
 */
//TODO test
public class TaskDateMatchesDatePredicate implements Predicate<Task> {
    private final List<LocalDate> dates;

    public TaskDateMatchesDatePredicate() {
        List<LocalDate> dates = new ArrayList<>();
        dates.add(LocalDate.now());
        this.dates = dates;
    }

    public TaskDateMatchesDatePredicate(LocalDate startDate) {
        int numOfDaysInAWeek = 7;
        dates = IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysInAWeek)
                .mapToObj(startDate::plusDays)
                .collect(Collectors.toList());
    }

    @Override
    public boolean test(Task task) {
        if (task instanceof Event) {
            Event event = (Event) task;
            return dates.stream()
                        .anyMatch(date -> date.compareTo(event.getStartDate()) >= 0
                                    && date.compareTo(event.getEndDate()) <= 0);
        } else if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            return dates.stream()
                        .anyMatch(date -> date.compareTo(deadline.getDueDate()) == 0);
        } else {
            //instanceof Todo
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof TaskDateMatchesDatePredicate //instanceof handles nulls
        )           && dates.equals(((TaskDateMatchesDatePredicate) other).dates); //state check
    }
}
