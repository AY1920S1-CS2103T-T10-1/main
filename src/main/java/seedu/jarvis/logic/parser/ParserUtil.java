package seedu.jarvis.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.commons.util.StringUtil;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.address.person.Address;
import seedu.jarvis.model.address.person.Email;
import seedu.jarvis.model.address.person.Name;
import seedu.jarvis.model.address.person.Phone;
import seedu.jarvis.model.address.tag.Tag;
import seedu.jarvis.model.cca.CcaName;
import seedu.jarvis.model.cca.CcaType;
import seedu.jarvis.model.cca.Equipment;
import seedu.jarvis.model.cca.EquipmentList;
import seedu.jarvis.model.financetracker.installment.InstallmentDescription;
import seedu.jarvis.model.financetracker.installment.InstallmentMoneyPaid;
import seedu.jarvis.model.planner.Frequency;
import seedu.jarvis.model.planner.Priority;
import seedu.jarvis.model.planner.tasks.Deadline;
import seedu.jarvis.model.planner.tasks.Event;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.planner.tasks.Todo;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MONEY_MESSAGE_CONSTRAINTS = "Money spent cannot be equal to or less than 0.";
    public static final String MESSAGE_INVALID_DATE = "Date is invalid. Please follow the format: dd/mm/yyyy.";
    public static final String MESSAGE_INVALID_TASK_TYPE = "Task type is invalid. Valid task types are: 'todo', 'event'"
                                                            + "and 'deadline' only.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code CcaName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static InstallmentDescription parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!InstallmentDescription.isValidDescription(trimmedDescription)) {
            throw new ParseException(InstallmentDescription.MESSAGE_CONSTRAINTS);
        }
        return new InstallmentDescription(trimmedDescription);
    }

    /**
     * Parses a {@code String description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static InstallmentMoneyPaid parseMoneySpent(String money) throws ParseException {
        requireNonNull(money);
        String trimmedMoney = money.trim();
        if (!InstallmentMoneyPaid.isValidAmount(trimmedMoney)) {
            throw new ParseException(MONEY_MESSAGE_CONSTRAINTS);
        }
        return new InstallmentMoneyPaid(trimmedMoney);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String priority} into a {@code Priority}.
     * @return the priority level of the task
     * @throws ParseException if the given {@code Priority} is invalid.
     */
    public static Priority parsePriority(String priority) throws ParseException {
        requireNonNull(priority);
        String trimmedPriority = priority.trim();
        if (!Priority.isValidPriority(trimmedPriority)) {
            throw new ParseException(Priority.MESSAGE_CONSTRAINTS);
        }

        if (priority.equals("high")) {
            return Priority.HIGH;
        } else if (priority.equals("med")) {
            return Priority.MED;
        } else {
            return Priority.LOW;
        }
    }

    /**
     * Parses a {@code String frequency} into a {@code Frequency}.
     * @return the frequency level of the task
     * @throws ParseException if the given {@code Frequency} is invalid.
     */
    public static Frequency parseFrequency(String frequency) throws ParseException {
        requireNonNull(frequency);
        String trimmedFrequency = frequency.trim();
        if (!Frequency.isValidFrequency(trimmedFrequency)) {
            throw new ParseException(Frequency.MESSAGE_CONSTRAINTS);
        }

        switch (frequency) {
        case "daily":
            return Frequency.DAILY;
        case "weekly":
            return Frequency.WEEKLY;
        case "monthly":
            return Frequency.MONTHLY;
        default:
            return Frequency.YEARLY;
        }
    }

    /**
     * Parses a {@code String date} into a {@code Calendar date} for tasks
     * @param date the date to be parsed
     * @return an array of Calendars to be added into the Task objects
     */
    public static LocalDate[] parseDate(String date) throws ParseException {
        requireNonNull(date);
        LocalDate[] res = new LocalDate[2];
        try {
            String trimmedDate = date.trim();
            String[] splitDate = trimmedDate.split("//");
            int count = 0;
            for (String d : splitDate) {
                LocalDate formattedDate = LocalDate.parse(d, Task.getDateFormat());
                res[count] = formattedDate;
                count++;
            }
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_INVALID_DATE);
        }

        return res;
    }

    /**
     * Builds a task for AddTaskCommandParser
     * @param taskType the type of task
     * @param taskDes description of the task
     * @param dates dates of the task, if any
     * @return returns a task
     * @throws ParseException if task type input by the user is wrong
     */
    public static Task buildTask(String taskType, String taskDes, LocalDate[] dates) throws ParseException {
        Task t;
        if (taskType.equals("event")) {
            t = new Event(taskDes, dates[0], dates[1]);
        } else if (taskType.equals("deadline")) {
            t = new Deadline(taskDes, dates[0]);
        } else if (taskType.equals("todo")) {
            t = new Todo(taskDes);
        } else {
            throw new ParseException(MESSAGE_INVALID_TASK_TYPE);
        }
        return t;
    }


    //=========== CcaTracker =============================================================================

    /**
     * Parses a {@code String name} into a {@code CcaName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static CcaName parseCcaName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!CcaName.isValidName(trimmedName)) {
            throw new ParseException(CcaName.MESSAGE_CONSTRAINTS);
        }
        return new CcaName(trimmedName);
    }

    /**
     * Parses a {@code String name} into a {@code CcaName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static CcaType parseCcaType(String ccaType) throws ParseException {
        requireNonNull(ccaType);
        String trimmedCcaType = ccaType.trim();
        if (!CcaType.isValidCcaType(trimmedCcaType)) {
            throw new ParseException(CcaType.MESSAGE_CONSTRAINTS);
        }
        return new CcaType(trimmedCcaType);
    }

    /**
     * Parses a {@code String equipment} into a {@code Equipment}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code equipment} is invalid.
     */
    public static Equipment parseEquipment(String equipment) throws ParseException {
        requireNonNull(equipment);
        String trimmedEquipment = equipment.trim();
        if (!Equipment.isValidEquipmentName(trimmedEquipment)) {
            throw new ParseException(Equipment.MESSAGE_CONSTRAINTS);
        }
        return new Equipment(trimmedEquipment);
    }

    /**
     * Parses {@code Collection<String> equipments} into a {@code EquipmentList}.
     */
    public static EquipmentList parseEquipments(Collection<String> equipments) throws ParseException {
        requireNonNull(equipments);
        final EquipmentList equipmentList = new EquipmentList();
        for (String equipmentName : equipments) {
            equipmentList.addEquipment(parseEquipment(equipmentName));
        }
        return equipmentList;
    }
}