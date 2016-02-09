package org.purplejs.value;

public interface ScriptExports
{
    ScriptValue getValue();

    boolean hasMethod( String name );

    ScriptValue executeMethod( String name, Object... args );
}
