package autoincrement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author bjorn
 * @since 2014-11-13
 */
public class AppendSnapshotTest {
    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void snapshotShouldBeAddedAfterVersion() {
        String str = "1.0.00";
        SnapshotAppender snapshotAppender = new SnapshotAppender();
        snapshotAppender.process(str);

        assertThat(snapshotAppender.getVersionWithSnapshot(), is("1.0.00-SNAPSHOT"));
    }

    @Test
    public void snapshotVersionShouldNotBeAppendedAgain() {
        String str = "1.0.00-SNAPSHOT";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Version '" + str + "' is already a snapshot version");

        SnapshotAppender snapshotAppender = new SnapshotAppender();
        snapshotAppender.process(str);
    }
}
