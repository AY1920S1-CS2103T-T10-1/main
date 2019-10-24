package seedu.jarvis.model.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.logic.commands.course.CheckCommand.MESSAGE_CANNOT_TAKE_COURSE;
import static seedu.jarvis.logic.commands.course.CheckCommand.MESSAGE_CAN_TAKE_COURSE;
import static seedu.jarvis.testutil.course.TypicalCourses.MA1521;
import static seedu.jarvis.testutil.course.TypicalCourses.getTypicalCoursePlanner;
import static seedu.jarvis.testutil.course.TypicalCourses.getTypicalCourses;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.jarvis.commons.util.CourseUtil;
import seedu.jarvis.commons.util.andor.AndOrTree;
import seedu.jarvis.model.course.exceptions.DuplicateCourseException;


public class CoursePlannerTest {
    private final CoursePlanner coursePlanner = new CoursePlanner();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), coursePlanner.getCourseList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> coursePlanner.resetData(null));
    }

    @Test
    public void resetData_withValidCoursePlanner_replacesData() {
        CoursePlanner newData = getTypicalCoursePlanner();
        coursePlanner.resetData(newData);
        assertEquals(newData, coursePlanner);
    }

    @Test
    public void resetData_withDuplicateCourses_throwsDuplicateCourseException() {
        List<Course> courses = getTypicalCourses();
        courses.addAll(getTypicalCourses()); // append duplicates
        CoursePlannerStub newData = new CoursePlannerStub(courses);
        assertThrows(DuplicateCourseException.class, () -> coursePlanner.resetData(newData));
    }

    @Test
    public void hasCourse_nullCourse_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> coursePlanner.hasCourse(null));
    }

    @Test
    public void hasCourse_courseNotInList_returnsFalse() {
        assertFalse(coursePlanner.hasCourse(MA1521));
    }

    @Test
    public void hasCourse_courseInList_returnsTrue() {
        coursePlanner.addCourse(MA1521);
        assertTrue(coursePlanner.hasCourse(MA1521));
    }

    @Test
    public void getCourseList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> {
            coursePlanner.getCourseList().remove(0);
        });
    }

    @Test
    public void checkCourse_doesNotSatisfyPrereqs_cannotTakeCourse() {
        Course actualCourse = CourseUtil.getCourse("CS2040").get();
        AndOrTree<Course> tree = AndOrTree.buildTree(
            actualCourse.toString(),
            actualCourse.getPrereqTree().toString(), (c) -> CourseUtil.getCourse(c).orElse(null)
        );
        coursePlanner.checkCourse(tree);
        assertEquals(coursePlanner.getCourseText().getText(),
                String.format(MESSAGE_CANNOT_TAKE_COURSE, actualCourse) + "\n" + tree.toString());
    }

    @Test
    public void checkCourse_tookTheCourse_cannotTakeCourse() {
        Course actualCourse = CourseUtil.getCourse("CS2040").get();
        AndOrTree<Course> tree = AndOrTree.buildTree(
            actualCourse.toString(),
            actualCourse.getPrereqTree().toString(), (c) -> CourseUtil.getCourse(c).orElse(null)
        );
        coursePlanner.addCourse(actualCourse);
        coursePlanner.checkCourse(tree);
        assertEquals(coursePlanner.getCourseText().getText(),
            String.format(MESSAGE_CAN_TAKE_COURSE, actualCourse) + "\n" + tree.toString());
    }

    @Test
    public void checkCourse_satisfyPrereqs_cannotTakeCourse() {
        Course actualCourse = CourseUtil.getCourse("CS2040").get();
        AndOrTree<Course> tree = AndOrTree.buildTree(
            actualCourse.toString(),
            actualCourse.getPrereqTree().toString(), (c) -> CourseUtil.getCourse(c).orElse(null)
        );
        coursePlanner.addCourse(CourseUtil.getCourse("CS1010").get());
        coursePlanner.checkCourse(tree);
        assertEquals(coursePlanner.getCourseText().getText(),
            String.format(MESSAGE_CAN_TAKE_COURSE, actualCourse) + "\n" + tree.toString());
    }

    private static class CoursePlannerStub extends CoursePlanner {
        private final ObservableList<Course> courses = FXCollections.observableArrayList();

        CoursePlannerStub(Collection<Course> courses) {
            this.courses.setAll(courses);
        }

        @Override
        public ObservableList<Course> getCourseList() {
            return courses;
        }
    }
}
