package seedu.jarvis.model.cca.ccaprogress;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CcaCurrentProgressTest {

    private CcaCurrentProgress ccaCurrentProgress;

    @BeforeEach
    public void setUp() {
        ccaCurrentProgress = new CcaCurrentProgress();
    }


    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> CcaMilestone.isValidCcaMilestone(null));

        // invalid name
        assertFalse(CcaMilestone.isValidCcaMilestone("")); // empty string
        assertFalse(CcaMilestone.isValidCcaMilestone(" ")); // spaces only

        // valid name
        assertTrue(CcaMilestone.isValidCcaMilestone("Level 2. *canoeing")); // contains non-alphanumeric characters
        assertTrue(CcaMilestone.isValidCcaMilestone("modern dance")); // alphabets only
        assertTrue(CcaMilestone.isValidCcaMilestone("12345")); // numbers only
        assertTrue(CcaMilestone.isValidCcaMilestone("2nd cca")); // alphanumeric characters
        assertTrue(CcaMilestone.isValidCcaMilestone("Modern Dance")); // with capital letters
        assertTrue(CcaMilestone.isValidCcaMilestone("Tamil language society production")); // long names
    }
}
