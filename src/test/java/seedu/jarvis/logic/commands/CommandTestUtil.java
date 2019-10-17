package seedu.jarvis.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.logic.parser.CliSyntax.AddressSyntax.PREFIX_ADDRESS;
import static seedu.jarvis.logic.parser.CliSyntax.AddressSyntax.PREFIX_EMAIL;
import static seedu.jarvis.logic.parser.CliSyntax.AddressSyntax.PREFIX_NAME;
import static seedu.jarvis.logic.parser.CliSyntax.AddressSyntax.PREFIX_PHONE;
import static seedu.jarvis.logic.parser.CliSyntax.AddressSyntax.PREFIX_TAG;
import static seedu.jarvis.logic.parser.CliSyntax.FinanceSyntax.PREFIX_DESCRIPTION;
import static seedu.jarvis.logic.parser.CliSyntax.FinanceSyntax.PREFIX_MONEY;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.address.EditAddressCommand;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.logic.commands.finance.EditInstallmentCommand;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.address.AddressBook;
import seedu.jarvis.model.address.person.NameContainsKeywordsPredicate;
import seedu.jarvis.model.address.person.Person;
import seedu.jarvis.testutil.address.EditPersonDescriptorBuilder;
import seedu.jarvis.testutil.finance.EditInstallmentDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_DESC_NETFLIX = "Netflix";
    public static final String VALID_MONEY_NETFLIX = "13.50";
    public static final String VALID_DESC_SPOTIFY = "Spotify";
    public static final String VALID_MONEY_SPOTIFY = "9.50";
    public static final String VALID_DESC_LUNCH = "Lunch at Reedz";
    public static final String VALID_MONEY_LUNCH = "5.50";
    public static final String VALID_DESC_EARPHONES = "Earphones";
    public static final String VALID_MONEY_EARPHONES = "30.50";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INSTAL_DESC_NETFLIX = " " + PREFIX_DESCRIPTION + VALID_DESC_NETFLIX;
    public static final String INSTAL_MONEY_NETFLIX = " " + PREFIX_MONEY + VALID_MONEY_NETFLIX;

    public static final String INSTAL_DESC_SPOTIFY = " " + PREFIX_DESCRIPTION + VALID_DESC_SPOTIFY;
    public static final String INSTAL_MONEY_SPOTIFY = " " + PREFIX_MONEY + VALID_MONEY_SPOTIFY;

    public static final String PURCHASE_DESC_LUNCH = " " + PREFIX_DESCRIPTION + VALID_DESC_LUNCH;
    public static final String PURCHASE_MONEY_LUNCH = " " + PREFIX_MONEY + VALID_MONEY_LUNCH;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String INVALID_INSTAL_MONEY = " " + PREFIX_MONEY + "-10.0";
    public static final String INVALID_PURCHASE_MONEY = " " + PREFIX_MONEY + "-10.0";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditAddressCommand.EditPersonDescriptor DESC_AMY;
    public static final EditAddressCommand.EditPersonDescriptor DESC_BOB;

    public static final EditInstallmentCommand.EditInstallmentDescriptor INSTALL_NETFLIX;
    public static final EditInstallmentCommand.EditInstallmentDescriptor INSTALL_SPOTIFY;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        INSTALL_NETFLIX = new EditInstallmentDescriptorBuilder()
                .withDescription(VALID_DESC_NETFLIX)
                .withSubscriptionFee(VALID_MONEY_NETFLIX)
                .build();
        INSTALL_SPOTIFY = new EditInstallmentDescriptorBuilder()
                .withDescription(VALID_DESC_SPOTIFY)
                .withSubscriptionFee(VALID_MONEY_SPOTIFY)
                .build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel,
                                            CommandResult expectedCommandResult, Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }

    /**
     * Inversely executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandInverseSuccess(Command command, Model actualModel,
                                                   CommandResult expectedCommandResult,
                                                   Model expectedModel) {
        try {
            CommandResult result = command.executeInverse(actualModel);
            assertEquals(expectedCommandResult, result);
            //assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Inverse Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandInverseSuccess(Command, Model, String, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandInverseSuccess(Command command, Model actualModel,
                                                   String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandInverseSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Inversely executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandInverseFailure(Command command, Model actualModel,
                                                   String expectedMessage) {

        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.executeInverse(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}
