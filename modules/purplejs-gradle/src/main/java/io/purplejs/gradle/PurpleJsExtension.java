package io.purplejs.gradle;

import org.gradle.api.Project;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.dsl.DependencyHandler;

public class PurpleJsExtension
{
    private static final String GROUP = "io.purplejs";

    private final DependencyHandler dependencies;

    private boolean devMode;

    public PurpleJsExtension( final Project project )
    {
        this.dependencies = project.getDependencies();
    }

    public String getVersion()
    {
        return getClass().getPackage().getImplementationVersion();
    }

    public Dependency dependency( final String name )
    {
        return this.dependencies.create( GROUP + ":purplejs-" + name + ":" + getVersion() );
    }

    public boolean isDevMode()
    {
        return this.devMode;
    }

    public void setDevMode( final boolean devMode )
    {
        this.devMode = devMode;
    }
}
