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
public class ToReleaseMojo  extends AbstractMojo {
    @Component
    private MavenProject mavenProject;
    
    /** The name of the environment variable where the new version should be stored */
    @Parameter(property = "autoincrement.environmentVariableName", defaultValue = "newVersion", required = true)
    private String environmentVariableName;
    
    private String versionWithoutSnapshot;

    /**
     * Execute plugin.
     *
     * @throws org.apache.maven.plugin.MojoFailureException exception that will be handled by plugin framework
     * @see org.apache.maven.plugin.Mojo#execute()
     */
    @Override
    public void execute() throws MojoFailureException {
        removeSnapshotFromVersion(mavenProject.getVersion());
        storeNewVersionInEnvironment();
    }

    private void removeSnapshotFromVersion(String version) {
        SnapshotRemover snapshotRemover = new SnapshotRemover(version);
        snapshotRemover.process();
        versionWithoutSnapshot = snapshotRemover.getVersionWithoutSnapshot();
        getLog().info("versionWithoutSnapshot: " + versionWithoutSnapshot);
    }

    private void storeNewVersionInEnvironment() {
        getLog().info(String.format("Value of '%s' is '%s'", 
                environmentVariableName, 
                mavenProject.getProperties().getProperty(environmentVariableName)));
        mavenProject.getProperties().setProperty(environmentVariableName, versionWithoutSnapshot);
    }
}