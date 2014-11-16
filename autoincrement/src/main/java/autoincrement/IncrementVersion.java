package autoincrement;

import org.apache.commons.lang3.StringUtils;

/**
 * Increase patch number of a semantic version, ie MAJOR.MINOR.PATCH. The prefix tells the values of MAJOR.MINOR.
 * <p/>
 * Example:
 * <p/>
 * Increment version '1.2.12' (with prefix '1.2.') becomes '1.2.13'
 *
 * @author bjorn
 * @since 2014-11-13
 */
public class IncrementVersion {
    private final String prefix;

    private String incrementedVersion;
    private String versionNumber;

    /** Create new instance with set prefix */
    private IncrementVersion(String prefix) {
        this.prefix = prefix;
    }

    /** Factory method to create a new instance with a set prefix */
    public static IncrementVersion withPrefix(String prefix) {
        return new IncrementVersion(prefix);
    }

    /** Process the version to increment */
    public void process(String version) {
        checkPrefixConstraints(version);

        int number = extractNumber(version);
        number++;
        String incrementedNumber = leftPadNumber(number);

        incrementedVersion = prefix + incrementedNumber;
    }

    private void checkPrefixConstraints(String version) {
        if (version.length() <= prefix.length()) {
            throw new IllegalArgumentException(String.format(
                    "Prefix '%s' must be shorter than version '%s'", prefix, version));
        }
        if (!version.startsWith(prefix)) {
            throw new IllegalArgumentException(String.format(
                    "Version '%s' does not start with prefix '%s'", version, prefix));
        }
    }

    private int extractNumber(String version) {
        versionNumber = version.substring(prefix.length());
        return Integer.parseInt(versionNumber, 10);
    }

    private String leftPadNumber(int number) {
        return StringUtils.leftPad("" + number, versionNumber.length(), '0');
    }

    /** Retrieve the incremented version number */
    public String getIncrementedVersion() {
        return incrementedVersion;
    }
}
