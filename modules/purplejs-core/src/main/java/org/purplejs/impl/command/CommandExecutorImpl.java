package org.purplejs.impl.command;

import java.util.Map;
import java.util.ServiceLoader;

import org.purplejs.command.Command;
import org.purplejs.command.CommandExecutor;
import org.purplejs.command.CommandHandler;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;

public final class CommandExecutorImpl
    implements CommandExecutor
{
    private final Map<Class, CommandHandler> map;

    public CommandExecutorImpl()
    {
        this.map = buildHandlerMap();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R, C extends Command<R>> R execute( final C command )
    {
        try
        {
            final CommandHandler handler = findHandler( command.getClass() );
            return (R) handler.execute( command );
        }
        catch ( final RuntimeException e )
        {
            throw e;
        }
        catch ( final Exception e )
        {
            throw Throwables.propagate( e );
        }
    }

    private CommandHandler findHandler( final Class type )
    {
        final CommandHandler handler = this.map.get( type );
        if ( handler != null )
        {
            return handler;
        }

        throw new RuntimeException( "CommandHandler for " + type.getName() + " not found" );
    }

    private static Map<Class, CommandHandler> buildHandlerMap()
    {
        final ImmutableMap.Builder<Class, CommandHandler> builder = ImmutableMap.builder();
        for ( final CommandHandler handler : ServiceLoader.load( CommandHandler.class ) )
        {
            builder.put( handler.getType(), handler );
        }

        return builder.build();
    }
}
