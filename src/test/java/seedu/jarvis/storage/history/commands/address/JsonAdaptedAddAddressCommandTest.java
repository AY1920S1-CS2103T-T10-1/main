package seedu.jarvis.storage.history.commands.address;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.history.TypicalCommands.ADD_ALICE;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.address.AddAddressCommand;

/**
 * Tests the behaviour of {@code JsonAdaptedAddAddressCommand}.
 */
public class JsonAdaptedAddAddressCommandTest {

    @Test
    public void toModelType_addValidPerson_returnsAddCommand() throws Exception {
        JsonAdaptedAddAddressCommand jsonAdaptedAddAddressCommand = new JsonAdaptedAddAddressCommand(
                (AddAddressCommand) ADD_ALICE);
        assertEquals(ADD_ALICE, jsonAdaptedAddAddressCommand.toModelType());
    }

}
