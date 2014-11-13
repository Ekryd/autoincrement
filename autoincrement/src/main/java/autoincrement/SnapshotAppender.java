package autoincrement;

/**
 * @author bjorn
 * @since 2014-11-13
 */
public class SnapshotAppender {
    private static final String SNAPSHOT = "-SNAPSHOT";
    private final String version;
    private String versionWithSnapshot;

    public SnapshotAppender(String version) {
        this.version = version;
    }

    public void process() {
        if (version.endsWith(SNAPSHOT)) {
            throw new IllegalArgumentException(String.format(
                    "Version '%s' is already a snapshot version", version));
        }
        versionWithSnapshot = version + SNAPSHOT;
    }

    public String getVersionWithSnapshot() {
        return versionWithSnapshot;
    }
}
