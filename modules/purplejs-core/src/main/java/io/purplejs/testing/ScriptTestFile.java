package io.purplejs.testing;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;

import io.purplejs.Engine;
import io.purplejs.resource.ResourcePath;

public final class ScriptTestFile
{
    private final ResourcePath path;

    private final Map<String, Runnable> testMethods;

    private Engine engine;

    ScriptTestFile( final ResourcePath path )
    {
        this.path = path;
        this.testMethods = Maps.newHashMap();
    }

    public Engine getEngine()
    {
        return this.engine;
    }

    public void setEngine( final Engine engine )
    {
        this.engine = engine;
    }

    public String getName()
    {
        return this.path.getPath();
    }

    public Set<String> getTestMethods()
    {
        return this.testMethods.keySet();
    }

    public void runTestMethod( final String name )
    {
        this.testMethods.get( name ).run();
    }

    public void before( final Runnable runnable )
    {

    }

    public void after( final Runnable runnable )
    {

    }

    public void add( final String name, final Runnable runnable )
    {
        System.out.println("HERE!");
        this.testMethods.put( name, runnable );
    }
}
