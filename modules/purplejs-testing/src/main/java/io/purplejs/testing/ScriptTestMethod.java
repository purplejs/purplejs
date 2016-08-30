package io.purplejs.testing;

import java.util.function.Consumer;

final class ScriptTestMethod
{
    private final String name;

    private final Consumer<Object[]> executor;

    ScriptTestMethod( final String name, final Consumer<Object[]> executor )
    {
        this.name = name;
        this.executor = executor;
    }

    String getName()
    {
        return this.name;
    }

    void runTest( final Object... args )
    {
        this.executor.accept( args );
    }
}
