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
public class IncrementVersionTest {
    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void lastNumberInVersionShouldBeIncremented() {
        String str = "1.0.0";

        IncrementVersion incrementVersion = IncrementVersion.withPrefix("1.0.");
        incrementVersion.process(str);

        assertThat(incrementVersion.getIncrementedVersion(), is("1.0.1"));
    }

    @Test
    public void lastNumberInVersionShouldBePadded() {
        String str = "1.0.000";

        IncrementVersion incrementVersion = IncrementVersion.withPrefix("1.0.");
        incrementVersion.process(str);

        assertThat(incrementVersion.getIncrementedVersion(), is("1.0.001"));
    }

    @Test
    public void incrementInVersionShouldBecomeLargerSize() {
        String str = "1.0.9";

        IncrementVersion incrementVersion = IncrementVersion.withPrefix("1.0.");
        incrementVersion.process(str);

        assertThat(incrementVersion.getIncrementedVersion(), is("1.0.10"));
    }

    @Test
    public void lettersInPrefixShouldBeOk() {
        String str = "1.Test.00";

        IncrementVersion incrementVersion = IncrementVersion.withPrefix("1.Test.");
        incrementVersion.process(str);

        assertThat(incrementVersion.getIncrementedVersion(), is("1.Test.01"));
    }

    @Test
    public void wrongPrefixShouldThrowException() {
        String str = "1.Test.00";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Version '" + str + "' does not start with prefix '1.00.'");

        IncrementVersion incrementVersion = IncrementVersion.withPrefix("1.00.");
        incrementVersion.process(str);
    }

    @Test
    public void tooLongPrefixShouldThrowException() {
        String str = "1.Test.00";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Prefix '1.Test.00' must be shorter than version '1.Test.00'");

        IncrementVersion incrementVersion = IncrementVersion.withPrefix("1.Test.00");
        incrementVersion.process(str);
    }

    @Test
    public void tooLongPrefixShouldThrowException2() {
        String str = "1.Test.00";

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Prefix '1.Test.00.' must be shorter than version '1.Test.00'");

        IncrementVersion incrementVersion = IncrementVersion.withPrefix("1.Test.00.");
        incrementVersion.process(str);
    }

    @Test
    public void lastPartOfVersionMustBeANumber() {
        String str = "1.Test.0xff";

        expectedException.expect(NumberFormatException.class);
        expectedException.expectMessage("For input string: \"0xff\"");

        IncrementVersion incrementVersion = IncrementVersion.withPrefix("1.Test.");
        incrementVersion.process(str);
    }

    @Test
    public void RemainingDotsCannotBeConvertedToNumber() {
        String str = "1.Test.2";

        expectedException.expect(NumberFormatException.class);
        expectedException.expectMessage("For input string: \".2\"");

        IncrementVersion incrementVersion = IncrementVersion.withPrefix("1.Test");
        incrementVersion.process(str);
    }

}
