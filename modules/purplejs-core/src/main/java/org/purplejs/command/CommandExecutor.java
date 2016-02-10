package org.purplejs.command;

public interface CommandExecutor
{
    <R, C extends Command<R>> R execute( C command );
}
