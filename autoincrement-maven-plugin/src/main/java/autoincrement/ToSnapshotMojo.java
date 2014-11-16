package autoincrement;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * Mojo (Maven plugin) that convert the projects version to a release version (by removing "-SNAPSHOT").
 * The new version is stored as an environment variable.
 *
 * @author Bjorn Ekryd
 */
@Mojo(name = "toSnapshot",
        threadSafe = true,
        defaultPhase = LifecyclePhase.NONE,
        requiresProject = true)
public class ToSnapshotMojo extends AbstractMojo {
    @Component
    private MavenProject mavenProject;

    /** The name of the environment variable where the new version should be stored */
    @Parameter(property = "autoincrement.environmentVariableName", defaultValue = "newVersion", required = true)
    private String environmentVariableName;

    /** The name of the environment variable where the new version should be stored */
    @Parameter(property = "autoincrement.increaseVersionNumber", defaultValue = "true")
    private boolean increaseVersionNumber;

    /** The prefix in the version number that should not be incremented */
    @Parameter(property = "autoincrement.versionNumberPrefix", defaultValue = "1.0.")
    private String versionNumberPrefix;

    private String versionNumber;

    /**
     * Execute plugin.
     *
     * @throws org.apache.maven.plugin.MojoFailureException exception that will be handled by plugin framework
     * @see org.apache.maven.plugin.Mojo#execute()
     */
    @Override
    public void execute() throws MojoFailureException {
        versionNumber = mavenProject.getVersion();

        if (increaseVersionNumber) {
            incrementVersionNumber();
        }

        addSnapshotToVersionNumber();
        storeNewVersionInEnvironment();
    }

    private void incrementVersionNumber() {
        IncrementVersion incrementVersion = IncrementVersion.withPrefix(versionNumberPrefix);
        incrementVersion.process(versionNumber);
        versionNumber = incrementVersion.getIncrementedVersion();

        getLog().debug("Incremented version: " + versionNumber);
    }

    private void addSnapshotToVersionNumber() {
        SnapshotAppender snapshotAppender = new SnapshotAppender();
        snapshotAppender.process(versionNumber);
        versionNumber = snapshotAppender.getVersionWithSnapshot();

        getLog().debug("Version with snapshot: " + versionNumber);
    }

    private void storeNewVersionInEnvironment() {
        mavenProject.getProperties().setProperty(environmentVariableName, versionNumber);
        getLog().info(String.format("Setting environment variable '%s' to '%s'",
                environmentVariableName,
                versionNumber));
    }
}