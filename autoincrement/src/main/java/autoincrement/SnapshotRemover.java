package autoincrement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author bjorn
 * @since 2014-11-13
 */
public class SnapshotRemover {
    private static final Pattern REG_EX_SNAPSHOT = Pattern.compile("(?i)-SNAPSHOT$");
    private final String version;
    private String versionWithoutSnapshot;

    public SnapshotRemover(String version) {
        this.version = version;
    }
    
    public void process() {
        Matcher matcher = REG_EX_SNAPSHOT.matcher(version);
        
        if (!matcher.find()) {
            throw new IllegalArgumentException(String.format(
                    "Version '%s' does not end with -SNAPSHOT", version));
        }
        versionWithoutSnapshot = matcher.replaceFirst("");
    }

    public String getVersionWithoutSnapshot() {
        return versionWithoutSnapshot;
    }
}
