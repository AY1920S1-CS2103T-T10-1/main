package seedu.jarvis.commons.util.andor;

import java.util.Collection;
import java.util.List;

import seedu.jarvis.model.course.Course;

/**
 * Utility stubs to use in Unit tests in this package.
 *
 * @author ryanYtan
 */
public class AndOrStubs {
    /**
     * Stub class to emulate a Course.
     */
    static class CourseStub extends Course {
        public final String code;

        public CourseStub(String code) {
            this.code = code;
        }

        @Override
        public boolean equals(Object o) {
            CourseStub other = (CourseStub) o;
            return this.code.equals(other.code);
        }
    }

    /**
     * Stub class to emulate a Node.
     */
    static class NodeStub extends AndOrNode {
        public NodeStub(CourseStub data) {
            super(data, null, null);
        }

        public boolean hasFulfilledCondition(Collection<Course> collection) {
            return collection.contains(data);
        }

        @Override
        public String toString() {
            return "";
        }
    }

    static final List<AndOrNode> CHILDREN = List.of(
        new NodeStub(new CourseStub("aaa")),
        new NodeStub(new CourseStub("bbb")),
        new NodeStub(new CourseStub("ccc")),
        new NodeStub(new CourseStub("ddd")),
        new NodeStub(new CourseStub("eee")),
        new NodeStub(new CourseStub("fff"))
    );
}
