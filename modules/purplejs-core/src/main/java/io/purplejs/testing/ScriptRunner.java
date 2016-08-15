package io.purplejs.testing;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

import com.google.common.collect.Lists;
import com.google.common.reflect.ClassPath;

import io.purplejs.Engine;
import io.purplejs.EngineBuilder;
import io.purplejs.resource.ResourcePath;

public final class ScriptRunner
    extends Runner
{
    private final Class<?> testClass;

    private final ScriptTestSuite testInstance;

    private final List<ScriptTestFile> testFiles;

    public ScriptRunner( final Class<?> testClass )
        throws Exception
    {
        this.testClass = testClass;
        this.testInstance = (ScriptTestSuite) testClass.newInstance();
        this.testFiles = findTestFiles();

    }

    @Override
    public Description getDescription()
    {
        final Description suite = Description.createSuiteDescription( this.testClass );
        for ( final ScriptTestFile file : this.testFiles )
        {
            final Description desc = Description.createTestDescription( file.getName(), file.getName() );
            suite.addChild( desc );

            for ( final String method : file.getTestMethods() )
            {
                final Description methodDesc = Description.createTestDescription( file.getName(), method );
                desc.addChild( methodDesc );
            }
        }

        return suite;
    }

    @Override
    public void run( final RunNotifier notifier )
    {
        for ( final ScriptTestFile file : this.testFiles )
        {
            for ( final String method : file.getTestMethods() )
            {
                final Description desc = Description.createTestDescription( file.getName(), method );
                notifier.fireTestStarted( desc );

                try
                {
                    file.runTestMethod( method );
                    notifier.fireTestFinished( desc );
                }
                catch ( final Throwable e )
                {
                    notifier.fireTestFailure( new Failure( desc, e ) );
                }
            }
        }
    }

    private List<ScriptTestFile> findTestFiles()
        throws Exception
    {
        return findResources().stream().map( this::createScriptTestFile ).collect( Collectors.toList() );
    }

    private ScriptTestFile createScriptTestFile( final ResourcePath path )
    {
        final ScriptTestFile result = new ScriptTestFile( path );
        final Engine engine = createEngine( result );
        result.setEngine( engine );

        engine.require( path );
        return result;
    }

    private Engine createEngine( final Object test )
    {
        final EngineBuilder builder = EngineBuilder.newBuilder();
        builder.module( binder -> binder.globalVariable( "t", test ) );
        this.testInstance.configure( builder );
        return builder.build();
    }

    private List<Pattern> compileFilePatterns()
    {
        return this.testInstance.getTestFiles().stream().map( this::compileFilePattern ).collect( Collectors.toList() );
    }

    private Pattern compileFilePattern( final String path )
    {
        final String pattern = path.
            replace( ".", "\\." ).
            replace( "*", "[^/]*" ).
            replace( "**", ".*" );

        return Pattern.compile( pattern );
    }

    private List<ResourcePath> findResources()
        throws Exception
    {
        final List<Pattern> patterns = compileFilePatterns();
        final List<ResourcePath> list = Lists.newArrayList();

        final ClassPath cp = ClassPath.from( getClass().getClassLoader() );
        for ( final ClassPath.ResourceInfo info : cp.getResources() )
        {
            matchesResource( info, patterns, list );
        }

        return list;
    }

    private void matchesResource( final ClassPath.ResourceInfo info, final List<Pattern> patterns, final List<ResourcePath> list )
    {
        final String path = "/" + info.getResourceName();
        patterns.stream().filter( pattern -> pattern.matcher( path ).find() ).forEach( pattern -> list.add( ResourcePath.from( path ) ) );
    }
}
