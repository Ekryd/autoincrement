package autoincrement;

/**
 * Append a -SNAPSHOT string to a version number.
 * <p/>
 * Example:
 * <p/>
 * Append to version '1.2.12' becomes '1.2.12-SNAPSHOT'
 *
 * @author bjorn
 * @since 2014-11-13
 */
class SnapshotAppender {
    private static final String SNAPSHOT = "-SNAPSHOT";
    private String versionWithSnapshot;

    public SnapshotAppender() {
    }

    /** Process the version to append to */
    public void process(String version) {
        if (version.endsWith(SNAPSHOT)) {
            throw new IllegalArgumentException(String.format(
                    "Version '%s' is already a snapshot version", version));
        }
        versionWithSnapshot = version + SNAPSHOT;
    }

    /** Retrieves the new version number */
    public String getVersionWithSnapshot() {
        return versionWithSnapshot;
    }
}
