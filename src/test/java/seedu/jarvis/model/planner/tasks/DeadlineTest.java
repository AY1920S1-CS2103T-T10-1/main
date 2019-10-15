package seedu.jarvis.model.planner.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.address.tag.Tag;
import seedu.jarvis.model.planner.Frequency;
import seedu.jarvis.model.planner.Priority;


class DeadlineTest {

    @Test
    void addPriority_validInput_success() {
        LocalDate due = LocalDate.parse("10/10/2019", Task.getDateFormat());
        Deadline d = new Deadline("homework", due);
        d.addPriority(Priority.HIGH);
        assertNotNull(d.priority);
    }

    @Test
    void addFrequency_validInput_success() {
        LocalDate due = LocalDate.parse("10/10/2019", Task.getDateFormat());
        Deadline d = new Deadline("homework", due);
        d.addFrequency(Frequency.MONTHLY);
        assertNotNull(d.frequency);
    }

    @Test
    void addTag_validInput_success() {
        LocalDate due = LocalDate.parse("10/10/2019", Task.getDateFormat());
        Deadline d = new Deadline("homework", due);
        Tag t = new Tag("school");
        d.addTag(t);
        assertNotNull(d.tags);
    }

    @Test
    void getTags_validInput_success() {
        LocalDate due = LocalDate.parse("10/10/2019", Task.getDateFormat());
        Deadline d = new Deadline("homework", due);
        Tag t = new Tag("school");
        d.addTag(t);
        assertTrue(d.getTags().contains(t));
    }

    @Test
    void isEqual_validInput_true() {
        LocalDate deadlineOneCal = LocalDate.parse("10/10/2019", Task.getDateFormat());
        LocalDate deadlineTwoCal = LocalDate.parse("10/10/2019", Task.getDateFormat());
        Deadline deadlineOne = new Deadline("borrow book", deadlineOneCal);
        Deadline deadlineTwo = new Deadline("borrow book", deadlineTwoCal);
        assertTrue(deadlineOne.isEqual(deadlineTwo));
    }

    @Test
    void isEqual_validInput_false() {
        LocalDate deadlineOneCal = LocalDate.parse("10/10/2019", Task.getDateFormat());
        LocalDate deadlineTwoCal = LocalDate.parse("10/10/2019", Task.getDateFormat());
        Deadline deadlineOne = new Deadline("borrow hello", deadlineOneCal);
        Deadline deadlineTwo = new Deadline("borrow book", deadlineTwoCal);
        assertFalse(deadlineOne.isEqual(deadlineTwo));
    }

    @Test
    void getDueDate() {
        LocalDate due = LocalDate.parse("10/10/2019", Task.getDateFormat());
        Deadline d = new Deadline("homework", due);
        assertEquals(due, d.getDueDate());
    }
}
