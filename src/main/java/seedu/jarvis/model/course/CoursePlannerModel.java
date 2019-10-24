package seedu.jarvis.model.course;

import javafx.collections.ObservableList;
import seedu.jarvis.commons.util.andor.AndOrTree;

/**
 * The API of Course Planner component.
 */
public interface CoursePlannerModel {
    /**
     * Looks up a course.
     */
    void lookUpCourse(Course course);

    /**
     * Checks if the user can take this course.
     */
    void checkCourse(AndOrTree<Course> course);

    /**
     * Adds a course into the list.
     */
    void addCourse(Course course);

    /**
     * Adds a course based on the given zero-based index.
     */
    void addCourse(int zeroBasedIndex, Course course);

    /**
     * Removes a course and deletes it from the list.
     */
    void deleteCourse(Course course);

    /**
     * Returns true if a course exists inside the course planner.
     */
    boolean hasCourse(Course course);

    /**
     * Returns an unmodifiable list.
     */
    ObservableList<Course> getUnfilteredCourseList();

    /**
     * Returns a displayable {@code String} to the user.
     */
    String getCourseText();

    /**
     * Gets this Model's course planner
     */
    CoursePlanner getCoursePlanner();
}
