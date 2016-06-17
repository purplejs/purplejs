package org.purplejs.command;

import java.util.Map;
import java.util.function.Function;

import org.purplejs.resource.ResourcePath;
import org.purplejs.value.ScriptExports;

final class CommandImpl<R>
    implements Command<R>
{
    ResourcePath resource;

    Map<String, Object> variables;

    Function<ScriptExports, R> executor;

    @Override
    public ResourcePath getResource()
    {
        return this.resource;
    }

    @Override
    public Map<String, Object> getVariables()
    {
        return this.variables;
    }

    @Override
    public R execute( final ScriptExports exports )
    {
        return this.executor.apply( exports );
    }
}
