package seedu.jarvis.logic.parser.planner;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_DATE;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_FREQ;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_PRIORITY;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_TAG;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_TASK_DES;
import static seedu.jarvis.logic.parser.CliSyntax.PlannerSyntax.PREFIX_TASK_TYPE;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import seedu.jarvis.logic.commands.planner.AddTaskCommand;
import seedu.jarvis.logic.parser.ArgumentMultimap;
import seedu.jarvis.logic.parser.ArgumentTokenizer;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.ParserUtil;
import seedu.jarvis.logic.parser.Prefix;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.address.tag.Tag;
import seedu.jarvis.model.planner.Frequency;
import seedu.jarvis.model.planner.Priority;
import seedu.jarvis.model.planner.tasks.Task;

/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    @Override
    public AddTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TASK_TYPE, PREFIX_TASK_DES, PREFIX_DATE,
                                                                    PREFIX_FREQ, PREFIX_PRIORITY, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_TASK_TYPE, PREFIX_TASK_DES)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        if (isEventOrDeadline(argMultimap) && !arePrefixesPresent(argMultimap, PREFIX_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        Priority priority = null;
        if (arePrefixesPresent(argMultimap, PREFIX_PRIORITY)) {
            priority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get());
        }

        Frequency frequency = null;
        if (arePrefixesPresent(argMultimap, PREFIX_FREQ)) {
            frequency = ParserUtil.parseFrequency(argMultimap.getValue(PREFIX_FREQ).get());
        }

        String taskType = argMultimap.getValue(PREFIX_TASK_TYPE).get();
        String taskDes = argMultimap.getValue(PREFIX_TASK_DES).get();
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        LocalDate[] dates = new LocalDate[2];
        if (isEventOrDeadline(argMultimap)) {
            dates = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            if (taskType.equals("event")) {
                if (dates[1] == null) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            AddTaskCommand.MESSAGE_USAGE));
                }
            }
        }

        Task task = ParserUtil.buildTask(taskType, taskDes, dates);
        task.addFrequency(frequency);
        task.addPriority(priority);
        for (Tag tag : tagList) {
            task.addTag(tag);
        }
        return new AddTaskCommand(task);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private static boolean isEventOrDeadline(ArgumentMultimap argMultimap) {
        String type = argMultimap.getValue(PREFIX_TASK_TYPE).get();
        return type.equals("event") || type.equals("deadline");

    }
}
