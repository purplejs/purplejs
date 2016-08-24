package io.purplejs.gradle;

import java.io.File;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.plugins.ApplicationPlugin;
import org.gradle.api.plugins.ApplicationPluginConvention;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.JavaExec;

public class BootPlugin
    implements Plugin<Project>
{
    private PurpleExtension ext;

    private Project project;

    @Override
    public void apply( final Project project )
    {
        this.project = project;
        this.ext = this.project.getExtensions().create( "purplejs", PurpleExtension.class, this.project );

        addPlugins();
        addRepositories();
        addDependencies();
        configureAppPlugin();
    }

    private void addPlugins()
    {
        this.project.getPlugins().apply( JavaPlugin.class );
        this.project.getPlugins().apply( ApplicationPlugin.class );
    }

    private void addRepositories()
    {
        this.project.getRepositories().jcenter();
    }

    private void addDependencies()
    {
        final DependencyHandler handler = this.project.getDependencies();
        handler.add( "compile", this.ext.dependency( "boot" ) );
        handler.add( "testCompile", this.ext.dependency( "testing" ) );
    }

    private void configureAppPlugin()
    {
        final ApplicationPluginConvention convention = this.project.getConvention().findPlugin( ApplicationPluginConvention.class );
        convention.setMainClassName( "io.purplejs.boot.MainApp" );

        final JavaExec runTask = (JavaExec) this.project.getTasks().getByName( ApplicationPlugin.TASK_RUN_NAME );
        runTask.systemProperty( "io.purplejs.runMode", "dev" );

        final String devDirs = new File( this.project.getProjectDir(), "src/main/resources" ).getAbsolutePath();
        runTask.systemProperty( "io.purplejs.devSourceDirs", devDirs );
    }
}
