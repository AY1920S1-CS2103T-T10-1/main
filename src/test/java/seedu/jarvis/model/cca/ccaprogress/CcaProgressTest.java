package seedu.jarvis.model.cca.ccaprogress;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.cca.TypicalCcaMilestones.TIGER;

/**
 * Integration tests for {@code CcaProgress}.
 */
public class CcaProgressTest {

    private CcaProgress ccaProgress;

    @BeforeEach
    public void setUp() {
        ccaProgress = new CcaProgress();
    }

    @Test
    public void setMileStones_validInput_success() {
        CcaMilestoneList ccaMilestoneList = new CcaMilestoneList();
        ccaMilestoneList.add(TIGER);
        ccaProgress.setMilestones(ccaMilestoneList);
        assertEquals(ccaProgress.getCcaMilestoneList(), ccaMilestoneList)
    }

}
