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
public class RemoveSnapshotTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    @Test
    public void snapshotInVersionShouldBeRemoved() {
        String str = "1.0.00-SNAPSHOT";
        SnapshotRemover snapshotRemover = new SnapshotRemover(str);
        snapshotRemover.process();
        
        assertThat(snapshotRemover.getVersionWithoutSnapshot(), is("1.0.00"));
    }

    @Test
    public void snapshotCaseInsensitiveInVersionShouldBeRemoved() {
        String str = "1.0.00-SnAPsHoT";
        SnapshotRemover snapshotRemover = new SnapshotRemover(str);
        snapshotRemover.process();
        
        assertThat(snapshotRemover.getVersionWithoutSnapshot(), is("1.0.00"));
    }

    @Test
    public void noSnapshotShouldThrowException() {
                String str = "1.0.00-SNAPPSHOTT";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Version '" + str + "' does not end with -SNAPSHOT");
        SnapshotRemover snapshotRemover = new SnapshotRemover(str);
        snapshotRemover.process();
    }

    @Test
    public void snapshotInWrongPlaceShouldThrowException() {
                String str = "1.0.00-SNAPSHOT.00";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Version '" + str + "' does not end with -SNAPSHOT");
        SnapshotRemover snapshotRemover = new SnapshotRemover(str);
        snapshotRemover.process();
    }
}
