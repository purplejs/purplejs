package org.purplejs.command;

import java.util.Map;
import java.util.function.Function;

import org.purplejs.resource.ResourcePath;
import org.purplejs.value.ScriptExports;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public final class CommandBuilder<R>
{
    private ResourcePath resource;

    private final Map<String, Object> variables;

    private Function<ScriptExports, R> executor;

    private CommandBuilder()
    {
        this.variables = Maps.newHashMap();
    }

    public CommandBuilder<R> resource( final ResourcePath resource )
    {
        this.resource = resource;
        return this;
    }

    public CommandBuilder<R> variable( final String name, final Object value )
    {
        this.variables.put( name, value );
        return this;
    }

    public CommandBuilder<R> executor( final Function<ScriptExports, R> executor )
    {
        this.executor = executor;
        return this;
    }

    public Command<R> build()
    {
        final CommandImpl<R> command = new CommandImpl<>();
        command.resource = this.resource;
        command.variables = ImmutableMap.copyOf( this.variables );
        command.executor = this.executor;
        return command;
    }

    public static <R> CommandBuilder<R> newBuilder()
    {
        return new CommandBuilder<>();
    }
}
