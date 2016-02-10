package org.purplejs.command;

public interface CommandHandler<R, C extends Command<R>>
{
    Class<C> getType();

    R execute( C command )
        throws Exception;
}
