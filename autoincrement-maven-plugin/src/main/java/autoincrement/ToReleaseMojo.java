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
@Mojo(name = "toRelease",
        threadSafe = true,
        defaultPhase = LifecyclePhase.NONE,
        requiresProject = true)
public class ToReleaseMojo extends AbstractMojo {
    @Component
    private MavenProject mavenProject;

    /** The name of the environment variable where the new version should be stored */
    @Parameter(property = "autoincrement.environmentVariableName", defaultValue = "newVersion", required = true)
    private String environmentVariableName;

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
        removeSnapshotFromVersion();
        storeNewVersionInEnvironment();
    }

    private void removeSnapshotFromVersion() {
        SnapshotRemover snapshotRemover = new SnapshotRemover();
        snapshotRemover.process(versionNumber);
        versionNumber = snapshotRemover.getVersionWithoutSnapshot();
        getLog().debug("Version without snapshot: " + versionNumber);
    }

    private void storeNewVersionInEnvironment() {
        mavenProject.getProperties().setProperty(environmentVariableName, versionNumber);
        getLog().info(String.format("Setting environment variable '%s' to '%s'",
                environmentVariableName,
                versionNumber));
    }
}