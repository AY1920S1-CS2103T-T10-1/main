package seedu.jarvis.logic.parser.course;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CliSyntax.CourseSyntax.PREFIX_COURSE;

import java.util.Optional;

import seedu.jarvis.commons.exceptions.CourseNotFoundException;
import seedu.jarvis.commons.util.CourseUtil;
import seedu.jarvis.logic.commands.course.AddCourseCommand;
import seedu.jarvis.logic.parser.ArgumentMultimap;
import seedu.jarvis.logic.parser.ArgumentTokenizer;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.course.Course;

/**
 * Parses input arguments and creates a new AddCourseCommand object.
 */
public class AddCourseCommandParser implements Parser<AddCourseCommand> {
    @Override
    public AddCourseCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_COURSE);
        Optional<String> arg = argMultimap.getValue(PREFIX_COURSE);

        if (arg.isEmpty()) {
            throw new ParseException(String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, AddCourseCommand.COMMAND_WORD));
        }

        String courseCode = arg.get();

        try {
            Course course = CourseUtil.getCourse(courseCode);
            return new AddCourseCommand(course);
        } catch (CourseNotFoundException e) {
            throw new ParseException(courseCode + " could not be found!");
        }
    }
}
