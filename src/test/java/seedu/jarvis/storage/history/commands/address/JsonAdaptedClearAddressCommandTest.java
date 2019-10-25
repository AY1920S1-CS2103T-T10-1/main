package seedu.jarvis.storage.history.commands.address;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.history.TypicalCommands.CLEAR_ALL;

import org.junit.jupiter.api.Test;

import seedu.jarvis.logic.commands.address.ClearAddressCommand;

/**
 * Tests the behaviour of {@code JsonAdaptedClearAddressCommand}.
 */
public class JsonAdaptedClearAddressCommandTest {

    @Test
    public void toModelType_validClearAddressCommand_returnClearAddressCommand() throws Exception {
        JsonAdaptedClearAddressCommand jsonAdaptedClearAddressCommand = new JsonAdaptedClearAddressCommand(
                (ClearAddressCommand) CLEAR_ALL);
        assertEquals(CLEAR_ALL, jsonAdaptedClearAddressCommand.toModelType());
    }
}
