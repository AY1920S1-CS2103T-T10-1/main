package seedu.jarvis.model.course;

import java.util.Objects;

/**
 * Represents a {@code String} container.
 */
public class CourseText {
    private String toDisplay;

    public void setText(String text) {
        toDisplay = text;
    }

    public String getText() {
        return toDisplay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CourseText that = (CourseText) o;
        return Objects.equals(toDisplay, that.toDisplay);
    }
}

