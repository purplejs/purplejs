package io.purplejs.testing.junit;

import java.util.function.Consumer;

public final class ScriptTestMethod
{
    private final String name;

    private final Consumer<Object[]> executor;

    public ScriptTestMethod( final String name, final Consumer<Object[]> executor )
    {
        this.name = name;
        this.executor = executor;
    }

    public String getName()
    {
        return this.name;
    }

    public void runTest( final Object... args )
    {
        this.executor.accept( args );
    }
}
