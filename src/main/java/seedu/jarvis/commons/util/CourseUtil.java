package seedu.jarvis.commons.util;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import seedu.jarvis.commons.exceptions.CourseNotFoundException;
import seedu.jarvis.model.course.Course;
import seedu.jarvis.model.course.CourseCode;
import seedu.jarvis.model.course.CourseCredit;
import seedu.jarvis.model.course.Description;
import seedu.jarvis.model.course.Faculty;
import seedu.jarvis.model.course.FulfillRequirements;
import seedu.jarvis.model.course.Preclusion;
import seedu.jarvis.model.course.PrereqTree;
import seedu.jarvis.model.course.Title;


/**
 * Finds and reads Json files located in resources/modinfo.
 */
public class CourseUtil {
    /** The length of "LL5009GRSII" */
    private static final int LONGEST_STRING_LEN = 11;

    /**
     * Regex that removes all whitespace not found within quotes.
     * Adapted from https://stackoverflow.com/a/9584469.
     */
    private static final String REMOVE_WHITESPACE_REGEX = "\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

    private static final String COURSE_FOLDER = "modinfo";

    private static final String removeSpacesNotWithinQuotes(String string) {
        return string.replaceAll(REMOVE_WHITESPACE_REGEX, "").trim();
    }

    /**
     * Returns the file path to the relevant json file.
     * @param paths subdirectories and filename
     * @return a {@code String} containing the file path
     */
    private static String getFilePath(String... paths) {
        StringBuilder newFilePath = new StringBuilder(COURSE_FOLDER);
        for (String s : paths) {
            newFilePath.append("/").append(s);
        }
        return newFilePath.toString();
    }

    /**
     * Returns a {@code File} object pointing to the relevant course file.
     *
     * @param courseCode of the course
     * @return a {@code File} object
     * @throws IOException if the file is not found
     */
    private static File getCourseFile(String courseCode)
            throws CourseNotFoundException {
        String coursePrefix = getCoursePrefix(courseCode);
        String fileName = (courseCode.contains(".json")) ? courseCode : courseCode + ".json";

        // get File object to the desired file
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        String filePath = getFilePath(coursePrefix, fileName);
        try {
            return new File(Objects.requireNonNull(classLoader.getResource(filePath)).getFile());
        } catch (NullPointerException e) {
            throw new CourseNotFoundException();
        }
    }

    /**
     * Obtains course information as a {@code json String}.
     *
     * @param courseCode of the course
     * @return a {@code json String}
     * @throws IOException if the file is not found
     */
    public static String getCourseJsonString(String courseCode)
            throws CourseNotFoundException {
        File file = getCourseFile(courseCode);
        StringBuilder text = new StringBuilder();
        try (Stream<String> fileStream = Files.lines(file.toPath())) {
            fileStream.forEach(text::append);
        } catch (IOException e) {
            throw new CourseNotFoundException();
        }
        return removeSpacesNotWithinQuotes(text.toString());
    }

    /**
     * Returns the course prefix of the given course code. For example:
     * "MA1512" returns "MA".
     * "CS3230" returns "CS".
     *
     * @param courseCode the course code
     * @return a {@code String} containing the course prefix
     */
    private static String getCoursePrefix(String courseCode) {
        StringBuilder prefix = new StringBuilder();
        char[] code = courseCode.toCharArray();
        int i = 0;
        while (i < code.length && Character.isLetter(code[i])) {
            prefix.append(code[i++]);
        }
        return prefix.toString();
    }

    /**
     * Returns a {@code Map<String, String>} containing all fields in the specified
     * course file.
     *
     * @param courseCode the course code
     * @return a {@code Map<String, String>} of all values
     * @throws IOException if the file is not found
     */
    public static Map<String, String> getCourseMap(String courseCode)
            throws CourseNotFoundException {
        String json = getCourseJsonString(courseCode);
        JsonNode root;
        try {
            root = new ObjectMapper().readTree(json);
            return getCourseProps(root);
        } catch (IOException e) {
            throw new CourseNotFoundException();
        }
    }

    /**
     * Returns a {@code Map} containing the course properties of {@code root}.
     *
     * @param root a JsonNode containing course data
     * @return a {@code Map} containing course data
     */
    private static Map<String, String> getCourseProps(JsonNode root) {
        Map<String, String> courseProps = new HashMap<>();

        root.fields().forEachRemaining(entry -> {
            if (entry.getValue().isObject() || entry.getValue().isArray()) {
                courseProps.put(entry.getKey(), entry.getValue().toString());
            } else {
                courseProps.put(entry.getKey(), entry.getValue().asText());
            }
        });

        return courseProps;
    }

    /**
     * Returns a {@code Course} object containing the information in the course file.
     *
     * @param code of the course
     * @return a {@code Course} object containing information of the course.
     */
    public static Course getCourse(String code)
            throws CourseNotFoundException {
        // TODO somehow refactor this function
        AtomicReference<CourseCode> courseCode = new AtomicReference<>();
        AtomicReference<Title> title = new AtomicReference<>();
        AtomicReference<CourseCredit> courseCredit = new AtomicReference<>();
        AtomicReference<Description> description = new AtomicReference<>();
        AtomicReference<Faculty> faculty = new AtomicReference<>();
        AtomicReference<Preclusion> preclusion = new AtomicReference<>();
        AtomicReference<FulfillRequirements> fulfillRequirements = new AtomicReference<>();
        AtomicReference<PrereqTree> prereqTree = new AtomicReference<>();

        Map<String, String> courseInformation = getCourseMap(code);

        courseInformation.forEach((key, value) -> {
            Optional<String> arg = Optional.ofNullable(value);

            arg.ifPresent(s -> {
                switch (key) {
                case "courseCode":
                    courseCode.set(new CourseCode(s));
                    break;
                case "title":
                    title.set(new Title(s));
                    break;
                case "courseCredit":
                    courseCredit.set(new CourseCredit(s));
                    break;
                case "description":
                    description.set(new Description(s));
                    break;
                case "faculty":
                    faculty.set(new Faculty(s));
                    break;
                case "preclusion":
                    preclusion.set(new Preclusion(s));
                    break;
                case "fulfillRequirements":
                    fulfillRequirements.set(new FulfillRequirements(s));
                    break;
                case "prereqTree":
                    prereqTree.set(new PrereqTree(s));
                    break;
                default:
                }
            });
        });
        return new Course(
            title.get(), faculty.get(), description.get(), courseCode.get(), courseCredit.get(),
            prereqTree.get(), preclusion.get(), fulfillRequirements.get()
        );
    }

    /**
     * Add quotes to start and end of {@code String} to avoid json parsing error.
     */
    public static String addQuotes(String s) {
        return (s.length() <= CourseUtil.LONGEST_STRING_LEN) ? "\"" + s + "\"" : s;
    }
}
