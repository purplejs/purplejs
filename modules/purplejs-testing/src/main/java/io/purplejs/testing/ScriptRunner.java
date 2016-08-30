package io.purplejs.testing;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.reflect.ClassPath;

import io.purplejs.core.Engine;
import io.purplejs.core.EngineBuilder;
import io.purplejs.core.resource.ResourcePath;

public final class ScriptRunner
    extends Runner
{
    private static final ClassPath CP = initClassPath();

    private final Class<?> testClass;

    private final List<ScriptTestInstance> testFiles;

    private final ScriptTestSuite testInstance;

    public ScriptRunner( final Class<?> testClass )
        throws Exception
    {
        this.testClass = testClass;
        this.testInstance = (ScriptTestSuite) this.testClass.newInstance();
        this.testFiles = findTestFiles();
    }

    @Override
    public Description getDescription()
    {
        final Description suite = Description.createSuiteDescription( this.testClass );
        for ( final ScriptTestInstance file : this.testFiles )
        {
            final Description desc = Description.createTestDescription( file.getName(), file.getName() );
            suite.addChild( desc );

            for ( final ScriptTestMethod method : file.getTestMethods() )
            {
                final Description methodDesc = Description.createTestDescription( file.getName(), method.getName() );
                desc.addChild( methodDesc );
            }
        }

        return suite;
    }

    @Override
    public void run( final RunNotifier notifier )
    {
        for ( final ScriptTestInstance file : this.testFiles )
        {
            for ( final ScriptTestMethod method : file.getTestMethods() )
            {
                final Description desc = Description.createTestDescription( file.getName(), method.getName() );
                notifier.fireTestStarted( desc );

                try
                {
                    runBefore( file );
                    method.runTest( this.testInstance );
                    runAfter( file );
                }
                catch ( final Throwable e )
                {
                    notifier.fireTestFailure( new Failure( desc, e ) );
                }
                finally
                {
                    notifier.fireTestFinished( desc );
                }
            }

            file.dispose();
        }
    }

    private void runBefore( final ScriptTestInstance file )
        throws Exception
    {
        this.testInstance.setUp();
        file.runBefore();
    }

    private void runAfter( final ScriptTestInstance file )
        throws Exception
    {
        file.runAfter();
        this.testInstance.tearDown();
    }

    private List<ScriptTestInstance> findTestFiles()
        throws Exception
    {
        return findResources().stream().map( this::createScriptTestFile ).collect( Collectors.toList() );
    }

    private ScriptTestInstance createScriptTestFile( final ResourcePath path )
    {
        final ScriptTestInstance result = new ScriptTestInstance( path );
        final Engine engine = createEngine( result );
        result.setEngine( engine );

        engine.require( path );
        return result;
    }

    private Engine createEngine( final ScriptTestInstance testFile )
    {
        final EngineBuilder builder = EngineBuilder.newBuilder();
        this.testInstance.initialize( builder );
        builder.module( this.testInstance );
        builder.module( binder -> binder.globalVariable( "__RUNNER__", testFile ) );
        return builder.build();
    }

    private List<String> getTestFiles()
    {
        final List<String> files = Lists.newArrayList( this.testInstance.getTestFiles() );
        return files.stream().map( String::trim ).filter( ( str ) -> !Strings.isNullOrEmpty( str ) ).collect( Collectors.toList() );
    }

    private List<Pattern> compileFilePatterns()
    {
        return getTestFiles().stream().map( this::compileFilePattern ).collect( Collectors.toList() );
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

        for ( final ClassPath.ResourceInfo info : CP.getResources() )
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

    private static ClassPath initClassPath()
    {
        try
        {
            return ClassPath.from( ScriptRunner.class.getClassLoader() );
        }
        catch ( final Exception e )
        {
            throw new Error( e );
        }
    }
}
