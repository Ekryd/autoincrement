package autoincrement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Remove a -SNAPSHOT string from a version number.
 * <p/>
 * Example:
 * <p/>
 * Remove from version '1.2.12-SNAPSHOT' becomes '1.2.12'
 *
 * @author bjorn
 * @since 2014-11-13
 */
class SnapshotRemover {
    private static final Pattern REG_EX_SNAPSHOT = Pattern.compile("(?i)-SNAPSHOT$");
    private String versionWithoutSnapshot;

    public SnapshotRemover() {
    }

    /** Process the version that contains -SNAPSHOT */
    public void process(String version) {
        Matcher matcher = REG_EX_SNAPSHOT.matcher(version);

        if (!matcher.find()) {
            throw new IllegalArgumentException(String.format(
                    "Version '%s' does not end with -SNAPSHOT", version));
        }
        versionWithoutSnapshot = matcher.replaceFirst("");
    }

    /** Retrieves the new version number */
    public String getVersionWithoutSnapshot() {
        return versionWithoutSnapshot;
    }
}
