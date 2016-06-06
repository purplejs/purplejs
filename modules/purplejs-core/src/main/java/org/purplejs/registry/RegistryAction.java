package org.purplejs.registry;

@FunctionalInterface
public interface RegistryAction
{
    void execute( RegistryBuilder builder );
}
