package io.purplejs.testing.junit;

import java.util.List;
import java.util.function.Consumer;

import com.google.common.collect.Lists;

import io.purplejs.core.Engine;
import io.purplejs.core.resource.ResourcePath;

public final class ScriptTestFile
{
    private final ResourcePath path;

    private Engine engine;

    private Consumer<Object[]> before;

    private Consumer<Object[]> after;

    private final List<ScriptTestMethod> testMethods;

    public ScriptTestFile( final ResourcePath path )
    {
        this.path = path;
        this.testMethods = Lists.newArrayList();
    }

    public String getName()
    {
        return this.path.getPath().replaceAll( "(.+)\\.(.+)", "$2.$1" );
    }

    public List<ScriptTestMethod> getTestMethods()
    {
        return this.testMethods;
    }

    public void setEngine( final Engine engine )
    {
        this.engine = engine;
    }

    public void before( final Consumer<Object[]> callback )
    {
        this.before = callback;
    }

    public void after( final Consumer<Object[]> callback )
    {
        this.after = callback;
    }

    public void test( final String name, final Consumer<Object[]> callback )
    {
        this.testMethods.add( new ScriptTestMethod( name, callback ) );
    }

    public void runBefore( final Object... args )
    {
        if ( this.before != null )
        {
            this.before.accept( args );
        }
    }

    public void runAfter( final Object... args )
    {
        if ( this.after != null )
        {
            this.after.accept( args );
        }
    }
}
