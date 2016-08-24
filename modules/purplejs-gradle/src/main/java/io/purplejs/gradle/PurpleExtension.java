package io.purplejs.gradle;

import org.gradle.api.Project;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.dsl.DependencyHandler;

public class PurpleExtension
{
    private static final String GROUP = "io.purplejs";

    private final DependencyHandler dependencies;

    public PurpleExtension( final Project project )
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
}
