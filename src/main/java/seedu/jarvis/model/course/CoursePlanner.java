package seedu.jarvis.model.course;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.logic.commands.course.CheckCommand.MESSAGE_CANNOT_TAKE_COURSE;
import static seedu.jarvis.logic.commands.course.CheckCommand.MESSAGE_CAN_TAKE_COURSE;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.jarvis.commons.util.andor.AndOrTree;

/**
 * Wraps all data for the Course Planner.
 */
public class CoursePlanner {
    private UniqueCourseList uniqueCourseList;
    private CourseText courseText;

    public CoursePlanner() {
        uniqueCourseList = new UniqueCourseList();
        courseText = new CourseText();
    }

    public CoursePlanner(CoursePlanner coursePlanner) {
        this();
        resetData(coursePlanner);
    }

    /**
     * Resets the existing data of this {@code CoursePlanner} with {@code newData}.
     */
    public void resetData(CoursePlanner newData) {
        requireNonNull(newData);
        setUniqueCourseList(newData.getCourseList());
        setCourseText(newData.getCourseText().getText());
    }

    public UniqueCourseList getUniqueCourseList() {
        return uniqueCourseList;
    }

    public CourseText getCourseText() {
        return courseText;
    }

    public void setUniqueCourseList(List<Course> courses) {
        uniqueCourseList.setCourses(courses);
    }

    public void setCourseText(String text) {
        courseText.setText(text);
    }

    public void lookUpCourse(Course course) {
        courseText.setText(course.toDisplayableString());
    }

    /**
     * Updates Course Planner on whether the user can take this course.
     *
     * @param tree {@code AndOrTree}
     */
    public void checkCourse(AndOrTree<Course> tree) {
        Course course = tree.getRoot();
        String message = tree.fulfills(getCourseList())
            ? String.format(MESSAGE_CAN_TAKE_COURSE, course)
            : String.format(MESSAGE_CANNOT_TAKE_COURSE, course);
        courseText.setText(message + "\n" + tree.toString());
    }

    public boolean hasCourse(Course course) {
        return uniqueCourseList.contains(course);
    }

    public void addCourse(Course course) {
        uniqueCourseList.add(course);
    }

    public void addCourse(int zeroBasedIndex, Course course) {
        uniqueCourseList.add(zeroBasedIndex, course);
    }

    public void deleteCourse(Course course) {
        uniqueCourseList.remove(course);
    }

    public void setCourses(List<Course> courses) {
        uniqueCourseList.setCourses(courses);
    }

    public ObservableList<Course> getCourseList() {
        return uniqueCourseList.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CoursePlanner that = (CoursePlanner) o;
        return Objects.equals(uniqueCourseList, that.uniqueCourseList)
                && Objects.equals(courseText, that.courseText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueCourseList, courseText);
    }
}
